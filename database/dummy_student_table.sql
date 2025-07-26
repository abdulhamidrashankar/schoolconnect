-- create dummy table student
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT,
    enrolled_on DATE DEFAULT CURRENT_DATE
);

-- insert dummy student data
INSERT INTO student (name, email, age) VALUES
('Kabir Ali', 'kabir@example.com', 15),
('Sana Khan', 'sana@example.com', 14);
