package service;

import dao.StudentDAO;
import dao.StudentDAOImpl;
import model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Service implementation for Student business logic.
 * Handles validation, business rules, and coordinates with DAO layer.
 * 
 * TIER 2: Business Logic Layer
 * - Validates input data
 * - Enforces business rules
 * - Coordinates transactions
 * - Handles errors with meaningful messages
 * - No direct database access (uses DAO)
 * - No HTTP handling
 */
public class StudentServiceImpl implements StudentService {
    
    private StudentDAO studentDAO;
    
    /**
     * Constructor - initializes DAO dependency
     */
    public StudentServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
    }
    
    /**
     * Retrieves all students.
     * @return List of all students
     */
    @Override
    public List<Student> getAllStudents() {
        System.out.println("Service: Fetching all students");
        return studentDAO.findAll();
    }
    
    /**
     * Retrieves a student by ID.
     * @param id the student ID
     * @return Student object or null if not found
     */
    @Override
    public Student getStudentById(int id) {
        System.out.println("Service: Fetching student with ID: " + id);
        return studentDAO.findById(id);
    }
    
    /**
     * Creates a new student with validation.
     * @param student the student to create
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if validation fails
     */
    @Override
    public boolean createStudent(Student student) {
        System.out.println("Service: Creating new student");
        
        // Validate student data
        validateStudent(student);
        
        // Create student
        boolean success = studentDAO.insert(student);
        
        if (success) {
            System.out.println("Service: Student created successfully");
        } else {
            System.err.println("Service: Failed to create student");
        }
        
        return success;
    }
    
    /**
     * Updates an existing student with validation.
     * @param student the student to update
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if validation fails or student not found
     */
    @Override
    public boolean updateStudent(Student student) {
        System.out.println("Service: Updating student with ID: " + student.getIdStudent());
        
        // Validate ID
        if (student.getIdStudent() <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        
        // Check if student exists
        Student existingStudent = studentDAO.findById(student.getIdStudent());
        if (existingStudent == null) {
            throw new IllegalArgumentException("Student not found with ID: " + student.getIdStudent());
        }
        
        // Validate student data
        validateStudent(student);
        
        // Update student
        boolean success = studentDAO.update(student);
        
        if (success) {
            System.out.println("Service: Student updated successfully");
        } else {
            System.err.println("Service: Failed to update student");
        }
        
        return success;
    }
    
    /**
     * Deletes a student by ID.
     * @param id the student ID
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if student not found
     */
    @Override
    public boolean deleteStudent(int id) {
        System.out.println("Service: Deleting student with ID: " + id);
        
        // Check if student exists
        Student existingStudent = studentDAO.findById(id);
        if (existingStudent == null) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
        
        // Delete student
        boolean success = studentDAO.delete(id);
        
        if (success) {
            System.out.println("Service: Student deleted successfully");
        } else {
            System.err.println("Service: Failed to delete student");
        }
        
        return success;
    }
    
    /**
     * Validates student data according to business rules.
     * @param student the student to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student object cannot be null");
        }
        
        // Validate first name
        if (student.getFirstNameStudent() == null || student.getFirstNameStudent().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (student.getFirstNameStudent().length() > 100) {
            throw new IllegalArgumentException("First name cannot exceed 100 characters");
        }
        
        // Validate last name
        if (student.getLastNameStudent() == null || student.getLastNameStudent().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (student.getLastNameStudent().length() > 100) {
            throw new IllegalArgumentException("Last name cannot exceed 100 characters");
        }
        
        // Validate date of birth
        if (student.getDateBirthStudent() == null || student.getDateBirthStudent().trim().isEmpty()) {
            throw new IllegalArgumentException("Date of birth is required");
        }
        
        // Validate date format and ensure it's not in the future
        validateDateOfBirth(student.getDateBirthStudent());
    }
    
    /**
     * Validates the date of birth.
     * @param dateStr the date string to validate
     * @throws IllegalArgumentException if date is invalid or in the future
     */
    private void validateDateOfBirth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        
        try {
            Date birthDate = sdf.parse(dateStr);
            Date currentDate = new Date();
            
            if (birthDate.after(currentDate)) {
                throw new IllegalArgumentException("Date of birth cannot be in the future");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }
}
