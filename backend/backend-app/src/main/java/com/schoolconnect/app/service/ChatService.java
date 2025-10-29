package com.schoolconnect.app.service;

import com.schoolconnect.app.dto.chat.ChatMessageDTO;
import com.schoolconnect.app.dto.chat.SendMessageRequest;
import com.schoolconnect.app.dto.chat.UserDTO;
import com.schoolconnect.app.entity.ChatRoom;
import com.schoolconnect.app.entity.Message;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatRoom getOrCreatePrivateChat(Long userId1, Long userId2);

    ChatRoom createAnnouncementRoom(Long creatorId, String name, List<Long> participantIds);

    ChatRoom createGeneralChatRoom(String name, List<Long> participantIds);

    ChatMessageDTO sendMessage(Long senderId, SendMessageRequest request);

    List<ChatMessageDTO> getChatRoomMessages(UUID chatRoomId, Long userId);

    List<ChatMessageDTO> getPrivateChatMessages(Long userId1, Long userId2);

    List<ChatRoom> getUserChatRooms(Long userId);

    void updateMessageStatus(UUID messageId, String status, Long userId);

    void addReaction(UUID messageId, Long userId, String reaction);

    void removeReaction(UUID messageId, Long userId, String reaction);

    void deleteMessage(UUID messageId, Long userId);

    ChatMessageDTO editMessage(UUID messageId, Long userId, String newBody);

    ChatRoom getChatRoom(UUID roomId, Long userId);

    List<UserDTO> getAvailableUsers(Long userId);

    ChatMessageDTO convertToMessageDTO(Message message);
}