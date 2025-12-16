package dao;

import model.Student;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object implementation for Student entity.
 * Handles all database operations for students.
 * 
 * TIER 3: Data Access Layer
 * - Executes SQL queries with PreparedStatements
 * - Maps ResultSet to Student objects
 * - Proper resource management
 * - No business logic or validation
 * - No HTTP handling
 */
public class StudentDAOImpl implements StudentDAO {

    /**
     * Retrieves all students from the database.
     * @return List of all students
     */
    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY idStudent";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setIdStudent(rs.getInt("idStudent"));
                student.setFirstNameStudent(rs.getString("firstNameStudent"));
                student.setLastNameStudent(rs.getString("lastNameStudent"));
                student.setDateBirthStudent(rs.getString("dateBirthStudent"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all students: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return students;
    }

    /**
     * Retrieves a single student by ID.
     * @param id the student ID
     * @return Student object or null if not found
     */
    @Override
    public Student findById(int id) {
        String sql = "SELECT * FROM students WHERE idStudent = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Student student = new Student();
                student.setIdStudent(rs.getInt("idStudent"));
                student.setFirstNameStudent(rs.getString("firstNameStudent"));
                student.setLastNameStudent(rs.getString("lastNameStudent"));
                student.setDateBirthStudent(rs.getString("dateBirthStudent"));
                return student;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return null;
    }

    /**
     * Adds a new student to the database.
     * @param student the student to add
     * @return true if successful, false otherwise
     */
    @Override
    public boolean insert(Student student) {
        String sql = "INSERT INTO students (firstNameStudent, lastNameStudent, dateBirthStudent) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getFirstNameStudent());
            stmt.setString(2, student.getLastNameStudent());
            stmt.setString(3, student.getDateBirthStudent());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting student: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Updates an existing student in the database.
     * @param student the student with updated information
     * @return true if successful, false otherwise
     */
    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET firstNameStudent=?, lastNameStudent=?, dateBirthStudent=? WHERE idStudent=?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getFirstNameStudent());
            stmt.setString(2, student.getLastNameStudent());
            stmt.setString(3, student.getDateBirthStudent());
            stmt.setInt(4, student.getIdStudent());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Deletes a student from the database.
     * @param id the ID of the student to delete
     * @return true if successful, false otherwise
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE idStudent = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Safely closes database resources.
     * @param conn the connection to close
     * @param stmt the statement to close
     * @param rs the result set to close
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
                e.printStackTrace();
            }
        }
        if (conn != null) {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
