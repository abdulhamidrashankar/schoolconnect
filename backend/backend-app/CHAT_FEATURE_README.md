# Chat Feature Implementation

This document describes the complete chat feature implementation for the SchoolConnect backend application.

## Overview

The chat feature provides real-time messaging capabilities with the following key features:

1. **Private 1:1 Messaging**: Parent-Teacher and Student-Teacher private conversations
2. **Group Announcements**: Teacher-only announcement channels for class communications
3. **Real-time WebSocket Communication**: Live messaging with typing indicators and user status
4. **Message Management**: Edit, delete, react to messages
5. **Message Status Tracking**: Read receipts and delivery status

## Architecture

### Controllers

#### ChatController (`/api/chat`)
REST API endpoints for chat operations:
- `GET /rooms` - Get user's chat rooms
- `POST /private` - Create/get private chat
- `POST /room` - Create general chat room
- `POST /announcement` - Create announcement room (teachers only)
- `POST /message` - Send message
- `GET /rooms/{roomId}/messages` - Get chat room messages
- `GET /private/{otherUserId}/messages` - Get private chat messages
- `PUT /messages/{messageId}/status` - Update message status
- `POST /messages/{messageId}/reactions` - Add reaction
- `DELETE /messages/{messageId}/reactions` - Remove reaction
- `PUT /messages/{messageId}` - Edit message
- `DELETE /messages/{messageId}` - Delete message
- `GET /rooms/{roomId}` - Get chat room details

#### WebSocketController
Real-time messaging endpoints:
- `/app/chat.sendMessage` - Send message via WebSocket
- `/app/chat.addReaction` - Add reaction to message
- `/app/chat.removeReaction` - Remove reaction from message
- `/app/chat.editMessage` - Edit message
- `/app/chat.deleteMessage` - Delete message
- `/app/chat.updateMessageStatus` - Update message status
- `/app/chat.typing` - Send typing indicator
- `/app/chat.userStatus` - Update user online/offline status

### Services

#### ChatService Interface
Defines the contract for chat operations:
- Private chat management
- Announcement room creation
- Message sending and retrieval
- Message status updates
- Reaction management
- Message editing and deletion

#### ChatServiceImpl
Implements the chat business logic:
- User permission validation (parent-teacher, student-teacher only)
- Message persistence and retrieval
- Real-time notifications
- Chat room management

#### NotificationService
Handles real-time notifications:
- User status broadcasting
- Chat room notifications
- System messages
- User-specific notifications

### Data Transfer Objects (DTOs)

#### Core DTOs
- `ChatMessageDTO` - Complete message structure with metadata
- `SendMessageRequest` - Message creation request
- `PrivateChatRequest` - Private chat creation request
- `CreateAnnouncementRequest` - Announcement room creation request

#### WebSocket DTOs
- `ReactionRequest` - Reaction management
- `EditMessageWebSocketRequest` - Message editing
- `DeleteMessageRequest` - Message deletion
- `UpdateMessageStatusWebSocketRequest` - Status updates
- `TypingIndicatorRequest` - Typing indicators
- `UserStatusRequest` - User status updates

### Entities

#### Message Entity
- `id` - UUID primary key
- `chatRoom` - Associated chat room
- `sender` - Message sender
- `receiver` - Direct message receiver
- `type` - Message type (CHAT, SYSTEM, etc.)
- `subtype` - Message subtype (PRIVATE, PUBLIC)
- `contentType` - Content type (TEXT, IMAGE, etc.)
- `body` - Message content
- `metadata` - Additional metadata (JSON)
- `threadRoot` - Thread root message
- `replyTo` - Reply target message
- `createdAt` - Creation timestamp
- `status` - Message status (SENT, DELIVERED, READ)
- `edited` - Edit flag
- `deleted` - Deletion flag

#### ChatRoom Entity
- `id` - UUID primary key
- `name` - Room name
- `group` - Group flag
- `createdAt` - Creation timestamp

#### ChatRoomUser Entity
- `id` - UUID primary key
- `chatRoom` - Associated chat room
- `user` - Room participant

#### MessageReaction Entity
- `id` - UUID primary key
- `message` - Associated message
- `user` - Reaction user
- `reaction` - Reaction emoji/text
- `createdAt` - Creation timestamp

## WebSocket Configuration

### Endpoints
- `/ws` - WebSocket endpoint with SockJS fallback
- `/ws` - Native WebSocket endpoint

### Message Broker
- `/topic` - Public topics (chat rooms, user status)
- `/queue` - Private user queues
- `/user` - User-specific destinations

### Security
- Authentication via user ID in connection headers
- Authorization checks for chat room access
- User permission validation for message operations

## Usage Examples

### Creating a Private Chat
```javascript
// REST API
POST /api/chat/private
{
  "otherUserId": 123
}

// WebSocket
stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
  "receiverId": 123,
  "body": "Hello!",
  "contentType": "TEXT"
}));
```

### Sending a Message
```javascript
// REST API
POST /api/chat/message
{
  "chatRoomId": "uuid",
  "body": "Hello everyone!",
  "contentType": "TEXT"
}

// WebSocket
stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
  "chatRoomId": "uuid",
  "body": "Hello everyone!",
  "contentType": "TEXT"
}));
```

### Creating a General Chat Room
```javascript
// REST API
POST /api/chat/room
{
  "name": "Study Group Chat",
  "participantIds": [1, 2, 3, 4, 5]
}
```

### Creating an Announcement Room
```javascript
// REST API (Teachers only)
POST /api/chat/announcement
{
  "name": "Class 5A Announcements",
  "participantIds": [1, 2, 3, 4, 5]
}
```

### Adding Reactions
```javascript
// REST API
POST /api/chat/messages/{messageId}/reactions
{
  "reaction": "👍"
}

// WebSocket
stompClient.send("/app/chat.addReaction", {}, JSON.stringify({
  "messageId": "uuid",
  "chatRoomId": "uuid",
  "reaction": "👍"
}));
```

### Typing Indicators
```javascript
// WebSocket
stompClient.send("/app/chat.typing", {}, JSON.stringify({
  "chatRoomId": "uuid",
  "typing": true
}));
```

## Security Considerations

1. **User Authentication**: All endpoints require valid authentication
2. **Permission Validation**: Users can only chat with authorized partners
3. **Message Authorization**: Users can only access messages in their chat rooms
4. **Role-based Access**: Only teachers can create announcements
5. **Message Ownership**: Users can only edit/delete their own messages

## Database Schema

The chat feature uses the following database tables:
- `messages` - Message storage
- `chat_rooms` - Chat room information
- `chat_room_users` - Room membership
- `message_reactions` - Message reactions

## Real-time Features

1. **Live Messaging**: Messages appear instantly via WebSocket
2. **Typing Indicators**: Real-time typing status
3. **User Status**: Online/offline status broadcasting
4. **Message Status**: Read receipts and delivery confirmations
5. **Reactions**: Real-time reaction updates
6. **Notifications**: User-specific and room-specific notifications

## Error Handling

The system includes comprehensive error handling:
- Authentication errors
- Authorization errors
- Validation errors
- Database errors
- WebSocket connection errors

All errors are returned with appropriate HTTP status codes and error messages.

## Performance Considerations

1. **Message Pagination**: Large chat histories are paginated
2. **Database Indexing**: Optimized queries for message retrieval
3. **WebSocket Scaling**: Efficient message broadcasting
4. **Caching**: User status and room information caching
5. **Connection Management**: Automatic cleanup of disconnected users

## Testing

The implementation includes:
- Unit tests for service layer
- Integration tests for REST endpoints
- WebSocket connection tests
- Message flow tests
- Permission validation tests

## Future Enhancements

1. **File Sharing**: Support for file attachments
2. **Message Search**: Full-text search capabilities
3. **Message Encryption**: End-to-end encryption
4. **Push Notifications**: Mobile push notifications
5. **Message Scheduling**: Scheduled message delivery
6. **Message Templates**: Predefined message templates
7. **Chat Analytics**: Usage statistics and analytics
