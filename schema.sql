-- Database Schema for Student Management System

-- Create database
CREATE DATABASE IF NOT EXISTS studentdb;

-- Use the database
USE studentdb;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    idStudent INT AUTO_INCREMENT PRIMARY KEY,
    firstNameStudent VARCHAR(100) NOT NULL,
    lastNameStudent VARCHAR(100) NOT NULL,
    dateBirthStudent DATE NOT NULL,
    INDEX idx_lastname (lastNameStudent),
    INDEX idx_firstname (firstNameStudent)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample data
INSERT INTO students (firstNameStudent, lastNameStudent, dateBirthStudent) VALUES
('John', 'Doe', '2000-01-15'),
('Jane', 'Smith', '1999-05-20'),
('Bob', 'Johnson', '2001-03-10');
