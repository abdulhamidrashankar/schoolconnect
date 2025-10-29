#!/bin/bash

# SchoolConnect Chat Test Setup Script
echo "🎓 SchoolConnect Chat Test Setup"
echo "================================="

# Check if Java is running
echo "📋 Checking if Spring Boot application is running..."
if curl -s http://localhost:9001/actuator/health > /dev/null 2>&1; then
    echo "✅ Spring Boot application is running on port 9001"
else
    echo "❌ Spring Boot application is not running on port 9001"
    echo "Please start the application first with: mvn spring-boot:run"
    exit 1
fi

# Test WebSocket endpoint
echo "🔌 Testing WebSocket endpoint..."
if curl -s -I http://localhost:9001/ws > /dev/null 2>&1; then
    echo "✅ WebSocket endpoint is accessible"
else
    echo "❌ WebSocket endpoint is not accessible"
    exit 1
fi

# Test REST API endpoints
echo "🌐 Testing REST API endpoints..."

# Test users endpoint
echo "Testing /api/chat/users..."
if curl -s http://localhost:9001/api/chat/users > /dev/null 2>&1; then
    echo "✅ Users endpoint is accessible"
else
    echo "❌ Users endpoint is not accessible"
fi

# Test rooms endpoint
echo "Testing /api/chat/rooms..."
if curl -s http://localhost:9001/api/chat/rooms > /dev/null 2>&1; then
    echo "✅ Rooms endpoint is accessible"
else
    echo "❌ Rooms endpoint is not accessible"
fi

echo ""
echo "🚀 Test Setup Complete!"
echo "======================="
echo ""
echo "📱 Open your browser and navigate to:"
echo "   http://localhost:9001/chat-test-comprehensive.html"
echo ""
echo "🧪 Test Scenarios:"
echo "   1. Connect with different user IDs (1, 2, 3, etc.)"
echo "   2. Test 1:1 private messaging between users"
echo "   3. Create teacher announcements"
echo "   4. Test real-time message delivery"
echo "   5. Test typing indicators"
echo "   6. Test user online/offline status"
echo ""
echo "💡 Tips:"
echo "   - Open multiple browser tabs/windows to simulate different users"
echo "   - Use different user IDs in each tab"
echo "   - Check the activity log for debugging information"
echo ""

