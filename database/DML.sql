-- Insert Roles
INSERT INTO role (name)
VALUES
    ('superadmin'),
    ('admin'),
    ('student'),
    ('teacher'),
    ('NT staff');

-- Insert Users
INSERT INTO users (username, password, salt, last_login, isblocked, role_id)
VALUES
    ('emma_stone', 'hashed_pw', 'salt123', NOW(), TRUE, (SELECT id FROM role WHERE name = 'student')),
    ('john_wick', 'hashed_pw', 'salt123', NOW(), TRUE, (SELECT id FROM role WHERE name = 'admin')),
    ('alice_johnson', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'superadmin')),
    ('bob_smith', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'superadmin')),
    ('carol_lee', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'admin')),
    ('david_kim', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'admin')),
    ('mary_johnson', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'NT staff')),
    ('james_johnson', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'NT staff')),
    ('sophia_brown', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'teacher')),
    ('james_anderson', 'hashed_pw', 'salt123', NOW(), FALSE, (SELECT id FROM role WHERE name = 'teacher'));

-- Insert Superadmins
INSERT INTO superadmin (name, phone_number, email_address, user_id)
VALUES
    ('Alice Johnson', '123-456-7890', 'alice@school.edu', (SELECT id FROM users WHERE username = 'alice_johnson')),
    ('Bob Smith', '987-654-3210', 'bob@school.edu', (SELECT id FROM users WHERE username = 'bob_smith'));

-- Insert Admins
INSERT INTO admin (name, phone_number, email_address, user_id)
VALUES
    ('Carol Lee', '111-222-3333', 'carol@school.edu', (SELECT id FROM users WHERE username = 'carol_lee')),
    ('David Kim', '444-555-6666', 'david@school.edu', (SELECT id FROM users WHERE username = 'david_kim'));

-- Insert Guardians
INSERT INTO guardian (
    name, surname, gender, email_address, phone_number, address, occupation,
    guardian_type, preference, user_id
)
VALUES
    ('Mary', 'Johnson', 'Female', 'mary.johnson@example.com', '+11234567890', 
     '123 Maple Street, Springfield, IL', 'Nurse', 'Mother', 'Primary',
     (SELECT id FROM users WHERE username = 'mary_johnson')),
     
    ('James', 'Johnson', 'Male', 'james.johnson@example.com', '+19876543210', 
     '123 Maple Street, Springfield, IL', 'Engineer', 'Father', 'Secondary',
     (SELECT id FROM users WHERE username = 'james_johnson'));

-- Insert Students
INSERT INTO student (
    first_name, middle_name, last_name, date_of_birth, roll_number, gender,
    enrollment_number, enrollment_date, guardian_id
)
VALUES
    ('Emma', 'Grace', 'Wilson', '2008-04-15', NULL, 'Female', '0001', '2024-09-01',
     (SELECT id FROM guardian WHERE name = 'Mary' AND surname = 'Johnson')),
     
    ('Liam', NULL, 'Taylor', '2007-11-03', NULL, 'Male', '0002', '2024-09-01',
     (SELECT id FROM guardian WHERE name = 'James' AND surname = 'Johnson'));

-- Insert Teachers
INSERT INTO teacher (name, surname, gender, email_address, phone_number, user_id)
VALUES
    ('Sophia', 'Brown', 'Female', 'sophia@school.edu', '777-888-9999', (SELECT id FROM users WHERE username = 'sophia_brown')),
    ('James', 'Anderson', 'Male', 'james@school.edu', '222-333-4444', (SELECT id FROM users WHERE username = 'james_anderson'));
