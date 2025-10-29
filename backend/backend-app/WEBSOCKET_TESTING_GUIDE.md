# WebSocket Testing Guide

This guide provides step-by-step instructions for testing the SchoolConnect WebSocket chat functionality using the provided HTML test interface.

## Prerequisites

1. **Backend Application Running**
   - Ensure your Spring Boot application is running on `http://localhost:8080`
   - Database should be configured and running
   - WebSocket endpoints should be accessible

2. **Browser Requirements**
   - Modern browser with WebSocket support
   - JavaScript enabled
   - Access to `http://localhost:8080`

## Step-by-Step Testing Instructions

### 1. Start the Backend Application

```bash
# Navigate to your project directory
cd backend-app

# Run the Spring Boot application
mvn spring-boot:run
# OR
java -jar target/backend-app-*.jar
```

**Expected Output:**
```
Started App in X.XXX seconds (JVM running for X.XXX)
```

### 2. Access the Test Interface

1. Open your web browser
2. Navigate to: `http://localhost:8080/websocket-test.html`
3. You should see the SchoolConnect Chat Test interface

### 3. Basic Connection Test

#### Step 3.1: Configure Connection
1. In the **Connection** panel (left sidebar):
   - Enter a **User ID** (e.g., `1`)
   - Verify **WebSocket URL** is `ws://localhost:8080/ws`
   - Click **Connect**

#### Step 3.2: Verify Connection
- Status should change to "Connected" (green indicator)
- Activity log should show "Connected as user 1"
- Connection button should be disabled
- Disconnect button should be enabled

**Expected Result:** ✅ Connection successful

### 4. Private Chat Testing

#### Step 4.1: Create Private Chat
1. In the **Chat Rooms** panel:
   - Enter another **User ID** (e.g., `2`) in "Other User ID" field
   - Click **Create Private Chat**

#### Step 4.2: Send Messages
1. Type a message in the input field
2. Click **Send** or press **Enter**
3. Message should appear in the chat area

**Expected Result:** ✅ Private chat created and messages sent

### 5. Room-Based Chat Testing

#### Step 5.1: Join a Chat Room
1. In the **Chat Rooms** panel:
   - Enter a **Room ID** (UUID format, e.g., `123e4567-e89b-12d3-a456-426614174000`)
   - Click **Join Room**

#### Step 5.2: Send Room Messages
1. Type a message in the input field
2. Click **Send**
3. Message should appear in the chat area

**Expected Result:** ✅ Room chat working

### 6. WebSocket Features Testing

#### Step 6.1: Typing Indicators
1. Start typing in the message input field
2. Other connected clients should see typing indicator
3. Stop typing for 1 second
4. Typing indicator should disappear

#### Step 6.2: Message Reactions
1. Send a message
2. Click on reaction buttons (👍, ❤️, 😂, etc.)
3. Reactions should appear on the message

#### Step 6.3: User Status
1. Open another browser tab/window
2. Connect with a different User ID
3. Original tab should show user status updates

### 7. Error Handling Testing

#### Step 7.1: Invalid Connection
1. Change WebSocket URL to invalid address
2. Try to connect
3. Should show error in activity log

#### Step 7.2: Send Without Connection
1. Disconnect from WebSocket
2. Try to send a message
3. Should show "Not connected" error

#### Step 7.3: Invalid Room ID
1. Enter invalid room ID format
2. Try to join room
3. Should handle gracefully

### 8. Advanced Testing Scenarios

#### Scenario 1: Multiple Users
1. Open 3 browser tabs
2. Connect as User ID 1, 2, and 3
3. Create private chats between users
4. Send messages between different user pairs
5. Verify all users receive appropriate messages

#### Scenario 2: Room Broadcasting
1. Connect 2 users to the same room
2. Send messages from one user
3. Verify other user receives messages
4. Test typing indicators between users

#### Scenario 3: Message Types
1. Test different message types:
   - TEXT messages
   - IMAGE messages (metadata)
   - FILE messages (metadata)
2. Verify proper handling of each type

### 9. REST API Testing (Optional)

You can also test the REST API endpoints using curl or Postman:

#### Create Private Chat
```bash
curl -X POST http://localhost:8080/api/chat/private \
  -H "Content-Type: application/json" \
  -d '{"otherUserId": 2}'
```

#### Send Message
```bash
curl -X POST http://localhost:8080/api/chat/message \
  -H "Content-Type: application/json" \
  -d '{
    "receiverId": 2,
    "body": "Hello from REST API!",
    "contentType": "TEXT"
  }'
```

#### Get User Chat Rooms
```bash
curl -X GET http://localhost:8080/api/chat/rooms
```

### 10. Troubleshooting

#### Common Issues and Solutions

**Issue: Connection Failed**
- Check if backend is running on port 8080
- Verify WebSocket URL is correct
- Check browser console for errors

**Issue: Messages Not Received**
- Verify both users are connected
- Check if room ID is correct
- Verify user permissions

**Issue: Typing Indicators Not Working**
- Check WebSocket connection status
- Verify room is joined
- Check browser console for errors

**Issue: Reactions Not Working**
- Ensure message has an ID
- Check if room is properly joined
- Verify WebSocket message format

### 11. Expected WebSocket Message Flow

#### Connection Flow
1. Client connects to `/ws`
2. Server authenticates user
3. Client subscribes to topics
4. Server confirms connection

#### Message Flow
1. Client sends message via `/app/chat.sendMessage`
2. Server processes and saves message
3. Server broadcasts to appropriate subscribers
4. Clients receive message via `/topic/chatroom/{roomId}`

#### Status Updates
1. Client sends status via `/app/chat.userStatus`
2. Server broadcasts to all user's chat rooms
3. Other users receive status via `/topic/user-status`

### 12. Performance Testing

#### Load Testing
1. Connect multiple users (10+)
2. Send messages rapidly
3. Monitor response times
4. Check for message ordering

#### Memory Testing
1. Send large messages
2. Send many messages quickly
3. Monitor browser memory usage
4. Check for memory leaks

### 13. Security Testing

#### Authentication Testing
1. Try connecting without valid user ID
2. Verify proper error handling
3. Check for unauthorized access

#### Authorization Testing
1. Try accessing other users' private chats
2. Verify proper permission checks
3. Test role-based access (teacher announcements)

### 14. Browser Compatibility

Test the interface on different browsers:
- Chrome (recommended)
- Firefox
- Safari
- Edge

### 15. Mobile Testing

Test on mobile devices:
- Responsive design should work
- Touch interactions should be smooth
- WebSocket connections should be stable

## Test Checklist

- [ ] Backend application starts successfully
- [ ] Test interface loads correctly
- [ ] WebSocket connection establishes
- [ ] Private chat creation works
- [ ] Room-based chat works
- [ ] Messages send and receive
- [ ] Typing indicators work
- [ ] Reactions work
- [ ] User status updates work
- [ ] Error handling works
- [ ] Multiple users can chat
- [ ] Message types work correctly
- [ ] REST API endpoints work
- [ ] Performance is acceptable
- [ ] Security measures work
- [ ] Cross-browser compatibility
- [ ] Mobile responsiveness

## Success Criteria

The WebSocket chat feature is working correctly if:
1. ✅ Users can connect and disconnect
2. ✅ Private chats work between users
3. ✅ Room-based chats work
4. ✅ Messages are delivered in real-time
5. ✅ Typing indicators work
6. ✅ Reactions work
7. ✅ User status updates work
8. ✅ Error handling is graceful
9. ✅ Multiple users can chat simultaneously
10. ✅ All WebSocket features function as expected

## Next Steps

After successful testing:
1. Integrate with your frontend application
2. Add authentication integration
3. Implement user management
4. Add file upload functionality
5. Implement push notifications
6. Add message search
7. Implement message encryption
8. Add analytics and monitoring

## Support

If you encounter issues:
1. Check the browser console for errors
2. Check the backend logs
3. Verify database connectivity
4. Check WebSocket configuration
5. Review the implementation code

The test interface provides detailed logging to help diagnose issues.
