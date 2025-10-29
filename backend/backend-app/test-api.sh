#!/bin/bash

# SchoolConnect Chat API Test Script
# Make sure the application is running on localhost:9001

echo "Testing SchoolConnect Chat API..."

# Test 1: Get available users
echo "1. Testing GET /api/chat/users"
curl -X GET "http://localhost:9001/api/chat/users" \
  -H "Content-Type: application/json" \
  -w "\nStatus: %{http_code}\n\n"

# Test 2: Create private chat
echo "2. Testing POST /api/chat/private"
curl -X POST "http://localhost:9001/api/chat/private" \
  -H "Content-Type: application/json" \
  -d '{"otherUserId": 2}' \
  -w "\nStatus: %{http_code}\n\n"

# Test 3: Send message
echo "3. Testing POST /api/chat/message"
curl -X POST "http://localhost:9001/api/chat/message" \
  -H "Content-Type: application/json" \
  -d '{"receiverId": 2, "body": "Hello from API test!", "contentType": "TEXT"}' \
  -w "\nStatus: %{http_code}\n\n"

# Test 4: Get private chat messages
echo "4. Testing GET /api/chat/private/2/messages"
curl -X GET "http://localhost:9001/api/chat/private/2/messages" \
  -H "Content-Type: application/json" \
  -w "\nStatus: %{http_code}\n\n"

echo "API tests completed!"


