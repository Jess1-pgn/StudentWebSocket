package dao;

import model.Student;
import java.util.List;

/**
 * Data Access Object interface for Student entity.
 * Defines all database operations for the Student table.
 * 
 * TIER 3: Data Access Layer
 * - Handles database operations only
 * - No business logic or validation
 * - No HTTP handling
 */
public interface StudentDAO {
    
    /**
     * Retrieves all students from the database.
     * @return List of all students, ordered by ID
     */
    List<Student> findAll();
    
    /**
     * Retrieves a single student by ID.
     * @param id the student ID
     * @return Student object or null if not found
     */
    Student findById(int id);
    
    /**
     * Inserts a new student into the database.
     * @param student the student to add (without ID)
     * @return true if successful, false otherwise
     */
    boolean insert(Student student);
    
    /**
     * Updates an existing student in the database.
     * @param student the student with updated information (including ID)
     * @return true if successful, false otherwise
     */
    boolean update(Student student);
    
    /**
     * Deletes a student from the database.
     * @param id the ID of the student to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int id);
}
