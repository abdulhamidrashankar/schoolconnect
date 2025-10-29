package com.schoolconnect.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolconnect.app.dto.chat.ChatMessageDTO;
import com.schoolconnect.app.dto.chat.SendMessageRequest;
import com.schoolconnect.app.dto.chat.UserDTO;
import com.schoolconnect.app.entity.*;
import com.schoolconnect.app.repository.*;
import com.schoolconnect.app.service.ChatService;
import java.util.UUID;
import com.schoolconnect.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageReactionRepository messageReactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public ChatRoom getOrCreatePrivateChat(Long userId1, Long userId2) {
        // Validate users exist and can chat
        User user1 = userRepository.findById(userId1)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId1));
        User user2 = userRepository.findById(userId2)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId2));

        // Check if users can chat (parent-teacher, student-teacher, etc.)
        if (!canUsersChat(user1, user2)) {
            throw new RuntimeException("Users are not allowed to chat with each other");
        }

        Optional<ChatRoom> existingRoom = chatRoomRepository.findPrivateChatRoom(userId1, userId2);
        
        if (existingRoom.isPresent()) {
            return existingRoom.get();
        }
        
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName("Private: " + user1.getUsername() + " - " + user2.getUsername());
        chatRoom.setGroup(false);
        chatRoom.setCreatedAt(Instant.now());
        chatRoom = chatRoomRepository.save(chatRoom);
        
        ChatRoomUser cru1 = new ChatRoomUser();
        cru1.setChatRoom(chatRoom);
        cru1.setUser(user1);
        chatRoomUserRepository.save(cru1);
        
        ChatRoomUser cru2 = new ChatRoomUser();
        cru2.setChatRoom(chatRoom);
        cru2.setUser(user2);
        chatRoomUserRepository.save(cru2);
        
        return chatRoom;
    }

    @Override
    @Transactional
    public ChatRoom createAnnouncementRoom(Long creatorId, String name, List<Long> participantIds) {
        // Validate that the creator is a teacher
        User creator = userRepository.findById(creatorId)
            .orElseThrow(() -> new RuntimeException("Creator not found: " + creatorId));
        
        if (creator.getRole() == null || !creator.getRole().getName().equalsIgnoreCase("teacher")) {
            throw new RuntimeException("Only teachers can create announcement rooms");
        }
        
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setGroup(true);
        chatRoom.setCreatedAt(Instant.now());
        chatRoom = chatRoomRepository.save(chatRoom);
        
        for (Long userId : participantIds) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
            ChatRoomUser cru = new ChatRoomUser();
            cru.setChatRoom(chatRoom);
            cru.setUser(user);
            chatRoomUserRepository.save(cru);
        }
        
        return chatRoom;
    }

    @Override
    @Transactional
    public ChatRoom createGeneralChatRoom(String name, List<Long> participantIds) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setGroup(true);
        chatRoom.setCreatedAt(Instant.now());
        chatRoom = chatRoomRepository.save(chatRoom);
        
        for (Long userId : participantIds) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
            ChatRoomUser cru = new ChatRoomUser();
            cru.setChatRoom(chatRoom);
            cru.setUser(user);
            chatRoomUserRepository.save(cru);
        }
        
        return chatRoom;
    }

    @Override
    @Transactional
    public ChatMessageDTO sendMessage(Long senderId, SendMessageRequest request) {
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found: " + senderId));
        
        Message message = new Message();
        message.setSender(sender);
        message.setType("CHAT");
        message.setContentType(request.getContentType() != null ? request.getContentType() : "TEXT");
        message.setBody(request.getBody());
        message.setCreatedAt(Instant.now());
        message.setStatus("SENT");
        
        if (request.getChatRoomId() != null) {
            ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
            
            message.setChatRoom(chatRoom);
            message.setSubtype(chatRoom.isGroup() ? "PUBLIC" : "PRIVATE");
            
            if (!chatRoomUserRepository.existsByChatRoomIdAndUserId(chatRoom.getId(), senderId)) {
                throw new RuntimeException("User not authorized to send message in this room");
            }
            
            // Check if it's an announcement room and only teachers can send messages
            if (chatRoom.isGroup() && chatRoom.getName().toLowerCase().contains("announcement")) {
                if (sender.getRole() == null || !sender.getRole().getName().equalsIgnoreCase("teacher")) {
                    throw new RuntimeException("Only teachers can send announcements");
                }
            }
        } else if (request.getReceiverId() != null) {
            User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
            
            // Validate that sender and receiver can chat
            if (!canUsersChat(sender, receiver)) {
                throw new RuntimeException("Users are not allowed to chat with each other");
            }
            
            message.setReceiver(receiver);
            message.setSubtype("PRIVATE");
            
            // Get or create private chat room
            ChatRoom chatRoom = getOrCreatePrivateChat(senderId, request.getReceiverId());
            message.setChatRoom(chatRoom);
        } else {
            throw new RuntimeException("Either chatRoomId or receiverId must be provided");
        }
        
        if (request.getReplyTo() != null) {
            Message parentMessage = messageRepository.findById(request.getReplyTo())
                .orElseThrow(() -> new RuntimeException("Parent message not found"));
            
            Message rootMessage = parentMessage.getThreadRoot() != null ? 
                parentMessage.getThreadRoot() : parentMessage;
            
            message.setThreadRoot(rootMessage);
            message.setReplyTo(rootMessage);
        }
        
        if (request.getMetadata() != null && !request.getMetadata().isEmpty()) {
            try {
                message.setMetadata(objectMapper.writeValueAsString(request.getMetadata()));
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize metadata", e);
            }
        }
        
        message = messageRepository.save(message);
        
        // Send notification
        if (request.getChatRoomId() != null) {
            notificationService.sendNotificationToChatRoom(request.getChatRoomId(), "NEW_MESSAGE", message);
        } else if (request.getReceiverId() != null) {
            notificationService.sendNotificationToUser(request.getReceiverId(), "NEW_MESSAGE", message);
            // Also send to sender for private messages
            notificationService.sendNotificationToUser(senderId, "NEW_MESSAGE", message);
        }
        
        return convertToMessageDTO(message);
    }

    @Override
    public List<ChatMessageDTO> getChatRoomMessages(UUID chatRoomId, Long userId) {
        if (!chatRoomUserRepository.existsByChatRoomIdAndUserId(chatRoomId, userId)) {
            throw new RuntimeException("User not authorized to view this chat room");
        }
        
        List<Message> messages = messageRepository.findAllRoomMessagesAsc(chatRoomId);
        
        return messages.stream()
            .map(this::convertToMessageDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDTO> getPrivateChatMessages(Long userId1, Long userId2) {
        List<Message> messages = messageRepository.findPrivateChat(userId1, userId2);
        return messages.stream()
            .map(this::convertToMessageDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoom> getUserChatRooms(Long userId) {
        return chatRoomRepository.findUserChatRooms(userId);
    }

    @Override
    @Transactional
    public void updateMessageStatus(UUID messageId, String status, Long userId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        
        if (message.getReceiver() != null && !message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to update message status");
        }
        
        message.setStatus(status);
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void addReaction(UUID messageId, Long userId, String reaction) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Optional<MessageReaction> existing = messageReactionRepository
            .findByMessageIdAndUserIdAndReaction(messageId, userId, reaction);
        
        if (existing.isPresent()) {
            return;
        }
        
        MessageReaction messageReaction = new MessageReaction();
        messageReaction.setMessage(message);
        messageReaction.setUser(user);
        messageReaction.setReaction(reaction);
        messageReaction.setCreatedAt(Instant.now());
        
        messageReactionRepository.save(messageReaction);
    }

    @Override
    @Transactional
    public void removeReaction(UUID messageId, Long userId, String reaction) {
        messageReactionRepository.deleteByMessageIdAndUserIdAndReaction(messageId, userId, reaction);
    }

    @Override
    @Transactional
    public void deleteMessage(UUID messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        
        if (!message.getSender().getId().equals(userId)) {
            throw new RuntimeException("Only sender can delete message");
        }
        
        message.setDeleted(true);
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public ChatMessageDTO editMessage(UUID messageId, Long userId, String newBody) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        
        if (!message.getSender().getId().equals(userId)) {
            throw new RuntimeException("Only sender can edit message");
        }
        
        message.setBody(newBody);
        message.setEdited(true);
        message = messageRepository.save(message);
        
        return convertToMessageDTO(message);
    }

    @Override
    public ChatRoom getChatRoom(UUID roomId, Long userId) {
        if (!chatRoomUserRepository.existsByChatRoomIdAndUserId(roomId, userId)) {
            throw new RuntimeException("User not authorized to view this chat room");
        }
        
        return chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Chat room not found"));
    }

    @Override
    public List<UserDTO> getAvailableUsers(Long userId) {
        User currentUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        
        List<User> allUsers = userRepository.findAll();
        
        return allUsers.stream()
            .filter(user -> !user.getId().equals(userId)) // Exclude current user
            .filter(user -> canUsersChat(currentUser, user)) // Only users who can chat with current user
            .map(user -> {
                String role = user.getRole() != null ? user.getRole().getName() : "student";
                String displayName = user.getUsername();
                return new UserDTO(user.getId(), user.getUsername(), role, displayName);
            })
            .collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO convertToMessageDTO(Message message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setType(message.getType());
        dto.setSubType(message.getSubtype());
        
        ChatMessageDTO.ContextDTO context = new ChatMessageDTO.ContextDTO();
        if (message.getChatRoom() != null) {
            context.setChatRoomId(message.getChatRoom().getId());
        }
        if (message.getThreadRoot() != null) {
            context.setThreadRoot(message.getThreadRoot().getId());
        }
        if (message.getReplyTo() != null) {
            context.setReplyTo(message.getReplyTo().getId());
        }
        dto.setContext(context);
        
        ChatMessageDTO.SenderDTO sender = new ChatMessageDTO.SenderDTO();
        if (message.getSender() != null) {
            sender.setId(message.getSender().getId());
            sender.setDisplayName(message.getSender().getUsername());
        }
        dto.setSender(sender);
        
        if (message.getReceiver() != null) {
            ChatMessageDTO.ReceiverDTO receiver = new ChatMessageDTO.ReceiverDTO();
            receiver.setId(message.getReceiver().getId());
            receiver.setDisplayName(message.getReceiver().getUsername());
            dto.setReceiver(receiver);
        }
        
        ChatMessageDTO.ContentDTO content = new ChatMessageDTO.ContentDTO();
        content.setType(message.getContentType());
        content.setBody(message.getBody());
        
        if (message.getMetadata() != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> metadataMap = objectMapper.readValue(
                    message.getMetadata(), 
                    Map.class
                );
                ChatMessageDTO.MetadataDTO metadata = objectMapper.convertValue(
                    metadataMap, 
                    ChatMessageDTO.MetadataDTO.class
                );
                content.setMetadata(metadata);
            } catch (Exception e) {
                // Ignore metadata parsing errors
            }
        }
        dto.setContent(content);
        
        ChatMessageDTO.AuditDTO audit = new ChatMessageDTO.AuditDTO();
        audit.setTimestamp(message.getCreatedAt());
        audit.setStatus(message.getStatus());
        audit.setEdited(message.isEdited());
        audit.setDeleted(message.isDeleted());
        dto.setAudit(audit);
        
        ChatMessageDTO.ExtensionsDTO extensions = new ChatMessageDTO.ExtensionsDTO();
        List<MessageReaction> reactions = messageReactionRepository.findByMessageId(message.getId());
        List<String> reactionList = reactions.stream()
            .map(MessageReaction::getReaction)
            .distinct()
            .collect(Collectors.toList());
        extensions.setReactions(reactionList);
        extensions.setCustom(new HashMap<>());
        dto.setExtensions(extensions);
        
        return dto;
    }

    private boolean canUsersChat(User user1, User user2) {
        // Get user roles
        String role1 = user1.getRole() != null ? user1.getRole().getName().toLowerCase() : "student";
        String role2 = user2.getRole() != null ? user2.getRole().getName().toLowerCase() : "student";
        
        // Allow all types of communication for testing purposes
        // In production, you might want to restrict this based on your business rules
        return true;
        
        // Original restrictive rules (commented out for testing):
        // return (role1.equals("parent") && role2.equals("teacher")) ||
        //        (role1.equals("teacher") && role2.equals("parent")) ||
        //        (role1.equals("student") && role2.equals("teacher")) ||
        //        (role1.equals("teacher") && role2.equals("student"));
    }
}
