-- Create database
CREATE DATABASE IF NOT EXISTS studentdb;

-- Use the database
USE studentdb;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    dateBirth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO students (firstName, lastName, dateBirth) VALUES
('John', 'Doe', '2000-01-15'),
('Jane', 'Smith', '1999-05-20'),
('Michael', 'Johnson', '2001-03-10'),
('Emily', 'Williams', '2000-07-25'),
('David', 'Brown', '1998-12-05');
