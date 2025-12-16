package dao;

import java.io.Serializable;

/**
 * Student entity class representing a student in the system.
 * Maps to the 'students' table in the database.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idStudent;
    private String firstNameStudent;
    private String lastNameStudent;
    private String dateBirthStudent;

    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * Constructor with all fields
     */
    public Student(int idStudent, String firstNameStudent, String lastNameStudent, String dateBirthStudent) {
        this.idStudent = idStudent;
        this.firstNameStudent = firstNameStudent;
        this.lastNameStudent = lastNameStudent;
        this.dateBirthStudent = dateBirthStudent;
    }

    /**
     * Constructor without ID (for new students)
     */
    public Student(String firstNameStudent, String lastNameStudent, String dateBirthStudent) {
        this.firstNameStudent = firstNameStudent;
        this.lastNameStudent = lastNameStudent;
        this.dateBirthStudent = dateBirthStudent;
    }

    // Getters and Setters
    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstNameStudent() {
        return firstNameStudent;
    }

    public void setFirstNameStudent(String firstNameStudent) {
        this.firstNameStudent = firstNameStudent;
    }

    public String getLastNameStudent() {
        return lastNameStudent;
    }

    public void setLastNameStudent(String lastNameStudent) {
        this.lastNameStudent = lastNameStudent;
    }

    public String getDateBirthStudent() {
        return dateBirthStudent;
    }

    public void setDateBirthStudent(String dateBirthStudent) {
        this.dateBirthStudent = dateBirthStudent;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", firstNameStudent='" + firstNameStudent + '\'' +
                ", lastNameStudent='" + lastNameStudent + '\'' +
                ", dateBirthStudent='" + dateBirthStudent + '\'' +
                '}';
    }
}
