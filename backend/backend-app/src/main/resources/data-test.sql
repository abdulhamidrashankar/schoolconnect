-- Test data for SchoolConnect Chat Testing
-- This file will be executed when the application starts

-- Insert test roles
INSERT INTO roles (id, name, description) VALUES 
(1, 'STUDENT', 'Student role'),
(2, 'TEACHER', 'Teacher role'),
(3, 'PARENT', 'Parent role'),
(4, 'ADMIN', 'Administrator role')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Insert test users
INSERT INTO users (id, username, email, password, first_name, last_name, phone, is_active, created_at, updated_at) VALUES 
(1, 'teacher1', 'teacher1@school.com', 'password123', 'Alice', 'Johnson', '1234567892', true, NOW(), NOW()),
(2, 'student1', 'student1@school.com', 'password123', 'John', 'Doe', '1234567890', true, NOW(), NOW()),
(3, 'student2', 'student2@school.com', 'password123', 'Jane', 'Smith', '1234567891', true, NOW(), NOW()),
(4, 'teacher2', 'teacher2@school.com', 'password123', 'Bob', 'Wilson', '1234567893', true, NOW(), NOW()),
(5, 'parent1', 'parent1@school.com', 'password123', 'Charlie', 'Brown', '1234567894', true, NOW(), NOW()),
(6, 'parent2', 'parent2@school.com', 'password123', 'Diana', 'Davis', '1234567895', true, NOW(), NOW())
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 2), -- teacher1 is a teacher
(2, 1), -- student1 is a student
(3, 1), -- student2 is a student
(4, 2), -- teacher2 is a teacher
(5, 3), -- parent1 is a parent
(6, 3)  -- parent2 is a parent
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

-- Insert some test chat rooms
INSERT INTO chat_rooms (id, name, is_group, created_at, updated_at) VALUES 
(UUID(), 'General Discussion', true, NOW(), NOW()),
(UUID(), 'Math Class', true, NOW(), NOW()),
(UUID(), 'Science Class', true, NOW(), NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name);
