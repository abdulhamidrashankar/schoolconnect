CREATE TABLE role (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(100)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255),
    last_login TIMESTAMP,
    isblocked BOOLEAN DEFAULT FALSE,
    role_id INTEGER NOT NULL REFERENCES role(id)
);

CREATE TABLE superadmin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE guardian (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    email_address VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(500),
    occupation VARCHAR(100),
    guardian_type VARCHAR(50),
    preference VARCHAR(10) CHECK (preference IN ('Primary', 'Secondary')),
    user_id INTEGER REFERENCES users(id)
);

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
    guardian_id INTEGER REFERENCES guardian(id)
);

CREATE TABLE teacher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
    email_address VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    user_id INTEGER REFERENCES users(id)
);
