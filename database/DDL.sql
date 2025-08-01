-- 1. Create role table first (parent of users)
CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

-- 2. Create users table (depends on role)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255),
    last_login TIMESTAMP,
    isblocked INTEGER DEFAULT 0,
    role_id INTEGER NOT NULL REFERENCES role(id)
);

-- 3. Create tables that depend on users
CREATE TABLE IF NOT EXISTS superadmin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS admin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) UNIQUE,
    user_id INTEGER REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS student (
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

CREATE TABLE IF NOT EXISTS teacher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
    email_address VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    user_id INTEGER REFERENCES users(id)
);
