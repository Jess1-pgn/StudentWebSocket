package service;

import model.Student;
import java.util.List;

/**
 * Service interface for Student business logic.
 * Defines business operations for student management.
 * 
 * TIER 2: Business Logic Layer
 * - Business rules and validation
 * - Transaction coordination
 * - No direct database access
 * - No HTTP handling
 */
public interface StudentService {
    
    /**
     * Retrieves all students.
     * @return List of all students
     */
    List<Student> getAllStudents();
    
    /**
     * Retrieves a student by ID.
     * @param id the student ID
     * @return Student object or null if not found
     */
    Student getStudentById(int id);
    
    /**
     * Creates a new student with validation.
     * @param student the student to create
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if validation fails
     */
    boolean createStudent(Student student);
    
    /**
     * Updates an existing student with validation.
     * @param student the student to update
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if validation fails or student not found
     */
    boolean updateStudent(Student student);
    
    /**
     * Deletes a student by ID.
     * @param id the student ID
     * @return true if successful, false otherwise
     * @throws IllegalArgumentException if student not found
     */
    boolean deleteStudent(int id);
}
