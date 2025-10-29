@echo off
echo 🔧 SchoolConnect Chat Fix Test
echo =============================

echo 📋 Testing API endpoints...

echo Testing /api/test/health...
curl -s http://localhost:9001/api/test/health
echo.

echo Testing /api/test/users...
curl -s http://localhost:9001/api/test/users?userId=1
echo.

echo Testing /api/chat/users...
curl -s http://localhost:9001/api/chat/users
echo.

echo Testing /api/chat/rooms...
curl -s http://localhost:9001/api/chat/rooms
echo.

echo 🚀 Test Complete!
echo =================
echo.
echo 📱 Now try opening the chat test interface:
echo    http://localhost:9001/chat-test-comprehensive.html
echo.
echo 💡 If you still get errors, check the application logs for more details.
echo.
pause

