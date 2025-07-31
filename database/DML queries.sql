
--create role table
CREATE TABLE role (
	id SERIAL PRIMARY KEY, 
	name VARCHAR(100)
);
INSERT INTO role (name)
VALUES 
('superadmin'),('admin'),('student'),('teacher'),('NT staff');


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255),
    last_login TIMESTAMP,
    isblocked INTEGER DEFAULT 0,
    role_id INTEGER NOT NULL REFERENCES role(id)
);

INSERT INTO users (username, password, salt, last_login, isblocked, role_id)
VALUES ('emma_stone', 'hashed_pw', 'salt123', NOW(), 1, 3 ),
('john_wick', 'hashed_pw', 'salt123', NOW(), 1, 2 );


CREATE TABLE superadmin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
	user_id INTEGER REFERENCES users(id)
);
INSERT INTO superadmin (name, phone_number, email_address)
VALUES
('Alice Johnson', '123-456-7890', 'alice@school.edu'),
('Bob Smith', '987-654-3210', 'bob@school.edu');


CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
	user_id INTEGER REFERENCES users(id)
);
INSERT INTO admin (name, phone_number, email_address)
VALUES
('Carol Lee', '111-222-3333', 'carol@school.edu'),
('David Kim', '444-555-6666', 'david@school.edu');


CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
	roll_number INTEGER,
    gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
	enrollment_number VARCHAR(50),
    enrollment_date DATE DEFAULT CURRENT_DATE,
	user_id INTEGER REFERENCES users(id)
);
INSERT INTO student (first_name, middle_name, last_name, date_of_birth, gender, enrollment_number, enrollment_date)
VALUES
('Emma', 'Grace', 'Wilson', '2008-04-15', 'Female', '0001' , '2024-09-01'),
('Liam', NULL, 'Taylor', '2007-11-03', 'Male', '0002', '2024-09-01');


CREATE TABLE teacher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
    email_address VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
	user_id INTEGER REFERENCES users(id)
);
INSERT INTO teacher (name, surname, gender, email_address, phone_number)
VALUES
('Sophia', 'Brown', 'Female', 'sophia@school.edu', '777-888-9999'),
('James', 'Anderson', 'Male', 'james@school.edu', '222-333-4444');


CREATE TABLE guardian_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
INSERT INTO guardian_type (name)
VALUES
('Father'), ('Mother'), ('Grand Father'), ('Grand Mother'), ('Uncle'),('Aunt');

CREATE TABLE guardian (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    email_address VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(500),
    occupation VARCHAR(100),
    student_id INTEGER REFERENCES student(id),
	guardian_type_id INTEGER REFERENCES guardian_type(id)
);

INSERT INTO guardian (name, surname, gender, email_address,phone_number, guardian_type_id, address, occupation, student_id
)
VALUES
('Mary', 'Johnson', 'Female', 'mary.johnson@example.com', '+11234567890', 2, '123 Maple Street, Springfield, IL', 'Nurse', 1),
('James', 'Johnson', 'Male', 'james.johnson@example.com', '+19876543210', 1, '123 Maple Street, Springfield, IL', 'Engineer', 1);

