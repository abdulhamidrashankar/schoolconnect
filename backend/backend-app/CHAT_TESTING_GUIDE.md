# Chat Feature Testing Guide

This guide explains how to test the chat functionality using the provided test interface.

## Quick Start

1. **Start the Backend Server**
   ```bash
   cd backend-app
   mvn spring-boot:run
   ```
   The server will start on `http://localhost:9001`

2. **Open the Test Interface**
   Navigate to `http://localhost:9001/static/websocket-test.html`

## Testing Private Chat

### Step 1: Connect as First User
1. Enter a User ID (e.g., `4`)
2. Click "Connect"
3. You should see "Connected as user 4" in the log

### Step 2: Create Private Chat
1. Enter another User ID (e.g., `13`) in "Other User ID" field
2. Click "Create Private Chat"
3. You should see "Created private chat with user 13" in the log

### Step 3: Send Messages
1. Type a message in the message input field
2. Click "Send" or press Enter
3. The message should appear in the chat area immediately
4. The message will be sent to the other user via WebSocket

### Step 4: Test with Second User
1. Open another browser tab/window
2. Navigate to the same URL
3. Connect as user `13`
4. You should receive the message from user `4`

## Testing Room Chat

### Step 1: Create a Room
1. Connect as a user
2. Click "Create Room"
3. Enter a room name (e.g., "Test Room")
4. The room will be created and you'll see the room ID in the log

### Step 2: Join the Room
1. Copy the room ID from the log
2. Paste it in the "Room ID" field
3. Click "Join Room"
4. You should see "Joined room: [room-id]" in the log

### Step 3: Send Messages to Room
1. Type a message and click "Send"
2. The message will be sent to all users in the room

## Testing Features

### Message Types
- **Text Messages**: Default message type
- **Image Messages**: Select "Image" from dropdown (metadata will be added)
- **File Messages**: Select "File" from dropdown (metadata will be added)

### Reactions
1. Click on any reaction button (👍, ❤️, 😂, etc.)
2. The reaction will be added to the last message
3. Reactions appear below messages

### Typing Indicators
- Start typing in the message input
- Other users will see a typing indicator
- The indicator stops after 1 second of inactivity

### User Status
- When you connect, your status is broadcast to other users
- You'll see status updates in the log

## Troubleshooting

### Connection Issues
- **"Connection failed"**: Check if the server is running on port 9001
- **"User not authenticated"**: Make sure you're using valid user IDs
- **CORS errors**: The server is configured to allow all origins

### Message Not Sending
- Check the browser console for JavaScript errors
- Verify the WebSocket connection is established
- Check the server logs for backend errors

### Messages Not Receiving
- Ensure both users are connected
- Check if the correct room ID is being used
- Verify the WebSocket subscriptions are working

## API Testing

You can also test the REST API endpoints directly:

### Get User's Chat Rooms
```bash
curl -X GET http://localhost:9001/api/chat/rooms
```

### Create a Room
```bash
curl -X POST http://localhost:9001/api/chat/room \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Room", "participantIds": [1, 2, 3]}'
```

### Send a Message
```bash
curl -X POST http://localhost:9001/api/chat/message \
  -H "Content-Type: application/json" \
  -d '{"chatRoomId": "room-uuid", "body": "Hello!", "contentType": "TEXT"}'
```

## Expected Behavior

### Private Chat
- Messages are sent directly between two users
- Each user sees their own messages on the right (sent)
- Each user sees the other's messages on the left (received)
- Messages are delivered in real-time

### Room Chat
- Messages are broadcast to all users in the room
- All users see the same messages
- Messages include sender information
- Real-time delivery to all participants

### Notifications
- "NEW_MESSAGE" notifications appear in the log
- User status updates are logged
- Error messages are displayed in red

## Performance Testing

### Multiple Users
1. Open multiple browser tabs/windows
2. Connect different users in each tab
3. Send messages between users
4. Verify all users receive messages correctly

### Large Messages
1. Send very long text messages
2. Test with special characters and emojis
3. Verify message formatting is preserved

### Connection Stability
1. Disconnect and reconnect users
2. Send messages during reconnection
3. Verify messages are not lost

## Security Testing

### User Permissions
- Test with different user roles (student, teacher, parent)
- Verify users can only chat with authorized partners
- Test unauthorized access attempts

### Message Validation
- Send empty messages
- Send messages with invalid content types
- Test message length limits

## Debugging

### Browser Console
- Open Developer Tools (F12)
- Check the Console tab for JavaScript errors
- Check the Network tab for failed requests

### Server Logs
- Check the Spring Boot console output
- Look for WebSocket connection logs
- Monitor database query logs

### WebSocket Inspector
- Use browser developer tools to inspect WebSocket messages
- Check message payloads and headers
- Verify subscription destinations

## Common Issues and Solutions

### Issue: Messages not appearing
**Solution**: Check if the WebSocket connection is established and subscriptions are active

### Issue: "User not authenticated" error
**Solution**: Ensure the user ID is valid and the user exists in the database

### Issue: Messages not delivered to other users
**Solution**: Verify both users are connected and subscribed to the correct topics

### Issue: Room creation fails
**Solution**: Check if the user has permission to create rooms and the request payload is valid

### Issue: WebSocket connection drops
**Solution**: Check network connectivity and server status, try reconnecting

This testing guide should help you verify that the chat functionality is working correctly. If you encounter any issues not covered here, check the server logs and browser console for more detailed error information.
