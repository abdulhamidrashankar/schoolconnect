-- Test data setup for SchoolConnect Chat Testing
-- Run this SQL script in your PostgreSQL database

-- Insert roles
INSERT INTO role (name) VALUES 
('teacher'),
('student'),
('parent')
ON CONFLICT (name) DO NOTHING;

-- Insert test users
INSERT INTO users (username, password, role_id, isblocked) VALUES 
('teacher1', 'password', (SELECT id FROM role WHERE name = 'teacher'), false),
('teacher2', 'password', (SELECT id FROM role WHERE name = 'teacher'), false),
('student1', 'password', (SELECT id FROM role WHERE name = 'student'), false),
('student2', 'password', (SELECT id FROM role WHERE name = 'student'), false),
('parent1', 'password', (SELECT id FROM role WHERE name = 'parent'), false),
('parent2', 'password', (SELECT id FROM role WHERE name = 'parent'), false)
ON CONFLICT (username) DO NOTHING;

-- Verify the data
SELECT u.id, u.username, r.name as role 
FROM users u 
JOIN role r ON u.role_id = r.id 
ORDER BY u.id;
