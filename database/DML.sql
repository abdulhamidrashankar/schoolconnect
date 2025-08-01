

INSERT INTO role (name)
VALUES 
('superadmin'),('admin'),('student'),('teacher'),('NT staff');


INSERT INTO users (username, password, salt, last_login, isblocked, role_id)
VALUES ('emma_stone', 'hashed_pw', 'salt123', NOW(), 1, 3 ),
('john_wick', 'hashed_pw', 'salt123', NOW(), 1, 2 );


INSERT INTO superadmin (name, phone_number, email_address)
VALUES
('Alice Johnson', '123-456-7890', 'alice@school.edu'),
('Bob Smith', '987-654-3210', 'bob@school.edu');


INSERT INTO admin (name, phone_number, email_address)
VALUES
('Carol Lee', '111-222-3333', 'carol@school.edu'),
('David Kim', '444-555-6666', 'david@school.edu');


INSERT INTO student (first_name, middle_name, last_name, date_of_birth, gender, enrollment_number, enrollment_date)
VALUES
('Emma', 'Grace', 'Wilson', '2008-04-15', 'Female', '0001' , '2024-09-01'),
('Liam', NULL, 'Taylor', '2007-11-03', 'Male', '0002', '2024-09-01');


INSERT INTO teacher (name, surname, gender, email_address, phone_number)
VALUES
('Sophia', 'Brown', 'Female', 'sophia@school.edu', '777-888-9999'),
('James', 'Anderson', 'Male', 'james@school.edu', '222-333-4444');

