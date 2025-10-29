@echo off
echo 🧪 Testing Announcement Creation Fix
echo ====================================

echo 📋 Testing with Teacher User (ID: 1)...
curl -X POST http://localhost:9001/api/chat/announcement ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Test Announcement\",\"participantIds\":[2,3,5,6]}" ^
  -w "\nHTTP Status: %%{http_code}\n"

echo.
echo 📋 Testing with Student User (ID: 2) - Should fail...
curl -X POST http://localhost:9001/api/chat/announcement ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Test Announcement 2\",\"participantIds\":[1,3,5,6]}" ^
  -w "\nHTTP Status: %%{http_code}\n"

echo.
echo 🚀 Test Complete!
echo =================
echo.
echo 💡 If the first test returns 200 and second returns 500, the fix is working!
echo.
pause
