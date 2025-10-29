@echo off
REM SchoolConnect Chat Test Setup Script
echo 🎓 SchoolConnect Chat Test Setup
echo =================================

REM Check if Java is running
echo 📋 Checking if Spring Boot application is running...
curl -s http://localhost:9001/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Spring Boot application is running on port 9001
) else (
    echo ❌ Spring Boot application is not running on port 9001
    echo Please start the application first with: mvn spring-boot:run
    pause
    exit /b 1
)

REM Test WebSocket endpoint
echo 🔌 Testing WebSocket endpoint...
curl -s -I http://localhost:9001/ws >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ WebSocket endpoint is accessible
) else (
    echo ❌ WebSocket endpoint is not accessible
    pause
    exit /b 1
)

REM Test REST API endpoints
echo 🌐 Testing REST API endpoints...

REM Test users endpoint
echo Testing /api/chat/users...
curl -s http://localhost:9001/api/chat/users >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Users endpoint is accessible
) else (
    echo ❌ Users endpoint is not accessible
)

REM Test rooms endpoint
echo Testing /api/chat/rooms...
curl -s http://localhost:9001/api/chat/rooms >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Rooms endpoint is accessible
) else (
    echo ❌ Rooms endpoint is not accessible
)

echo.
echo 🚀 Test Setup Complete!
echo =======================
echo.
echo 📱 Open your browser and navigate to:
echo    http://localhost:9001/chat-test-comprehensive.html
echo.
echo 🧪 Test Scenarios:
echo    1. Connect with different user IDs (1, 2, 3, etc.)
echo    2. Test 1:1 private messaging between users
echo    3. Create teacher announcements
echo    4. Test real-time message delivery
echo    5. Test typing indicators
echo    6. Test user online/offline status
echo.
echo 💡 Tips:
echo    - Open multiple browser tabs/windows to simulate different users
echo    - Use different user IDs in each tab
echo    - Check the activity log for debugging information
echo.
pause

