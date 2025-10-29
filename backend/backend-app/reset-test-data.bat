@echo off
echo 🔄 Resetting Test Data for SchoolConnect Chat
echo ============================================

echo 📋 Stopping application if running...
taskkill /F /IM java.exe 2>nul

echo 🗑️ Clearing database...
cd backend-app
if exist "data" rmdir /s /q "data"
if exist "logs" rmdir /s /q "logs"

echo 🚀 Starting application with fresh data...
mvn spring-boot:run

echo ✅ Test data reset complete!
echo ============================
echo.
echo 📱 Open your browser and navigate to:
echo    http://localhost:9001/chat-test-comprehensive.html
echo.
echo 💡 User IDs for testing:
echo    1 = Teacher (Alice Johnson) - Can create announcements
echo    2 = Student (John Doe)
echo    3 = Student (Jane Smith)  
echo    4 = Teacher (Bob Wilson) - Can create announcements
echo    5 = Parent (Charlie Brown)
echo    6 = Parent (Diana Davis)
echo.
pause
