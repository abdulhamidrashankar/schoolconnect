# SchoolConnect Chat Test Guide

## Quick Start

1. **Start the application:**
   ```bash
   cd backend-app
   mvn spring-boot:run
   ```

2. **Open the test interface:**
   - Navigate to `http://localhost:9001/chat-test.html`

3. **Test the chat functionality:**
   - Enter a user ID (e.g., 1, 2, 3)
   - Click "Connect"
   - Select a user from the list to start chatting
   - Type messages and send them

## Test Scenarios

### Scenario 1: Basic Connection Test
1. Open `http://localhost:9001/chat-test.html`
2. Enter user ID: `1`
3. Click "Connect"
4. Verify status shows "Connected"
5. Check that users are loaded in the sidebar

### Scenario 2: Private Chat Test
1. Connect as user ID `1`
2. Click on another user (e.g., user ID `2`)
3. Type a message: "Hello, how are you?"
4. Press Enter or click Send
5. Verify message appears in the chat

### Scenario 3: Two-User Test
1. Open two browser tabs/windows
2. In tab 1: Connect as user ID `1`
3. In tab 2: Connect as user ID `2`
4. In tab 1: Select user `2` and send a message
5. In tab 2: Select user `1` and verify you can see the message
6. In tab 2: Reply to the message
7. In tab 1: Verify you can see the reply

## API Endpoints

### REST API
- `GET /api/chat/users` - Get available users
- `GET /api/chat/private/{userId}/messages` - Get chat history
- `POST /api/chat/private` - Create private chat
- `POST /api/chat/message` - Send message

### WebSocket Endpoints
- `/ws` - WebSocket connection
- `/app/chat.sendMessage` - Send message
- `/user/queue/private` - Receive private messages
- `/user/queue/errors` - Receive errors

## Troubleshooting

### Connection Issues
- Check if the application is running on port 9001
- Verify WebSocket URL is correct: `http://localhost:9001/ws`
- Check browser console for errors

### User List Issues
- Ensure database has users with proper roles
- Check that users have roles (teacher, student, parent)
- Verify the `canUsersChat` method allows the user combinations

### Message Issues
- Check that both users are connected
- Verify the message is being sent to the correct user
- Check the WebSocket subscription is working

## Database Setup

Make sure you have the following tables with data:
- `users` - User accounts
- `role` - User roles (teacher, student, parent)
- `chat_rooms` - Chat rooms
- `chat_room_users` - Room memberships
- `messages` - Messages
- `message_reactions` - Message reactions

## Expected User Roles
- **Teachers** can chat with students and parents
- **Students** can chat with teachers
- **Parents** can chat with teachers

## Test Data
You can create test users with different roles to test the chat functionality:

```sql
-- Example test data (adjust as needed)
INSERT INTO role (name) VALUES ('teacher'), ('student'), ('parent');

INSERT INTO users (username, password, role_id) VALUES 
('teacher1', 'password', 1),
('student1', 'password', 2),
('parent1', 'password', 3);
```

## Debugging

1. **Check browser console** for JavaScript errors
2. **Check application logs** for backend errors
3. **Verify WebSocket connection** in browser dev tools
4. **Test API endpoints** directly with Postman or curl

## Common Issues

1. **CORS errors**: Make sure CORS is configured for your test origin
2. **Authentication errors**: Security is disabled, but check WebSocket auth
3. **Database errors**: Ensure database is running and accessible
4. **User not found**: Check that users exist in the database with proper roles
