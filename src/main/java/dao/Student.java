package dao;

import java.sql.Date;

public class Student {
	
	private int idStudent;
	private String firstNameStudent, lastNameStudent;
	private Date dateBirthStudent;
	
	
	
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String firstNameStudent, String lastNameStudent, Date dateBirthStudent) {
		super();
		this.firstNameStudent = firstNameStudent;
		this.lastNameStudent = lastNameStudent;
		this.dateBirthStudent = dateBirthStudent;
	}
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
	public Date getDateBirthStudent() {
		return dateBirthStudent;
	}
	public void setDateBirthStudent(Date dateBirthStudent) {
		this.dateBirthStudent = dateBirthStudent;
	}
	@Override
	public String toString() {
		return "Student [idStudent=" + idStudent + ", firstNameStudent=" + firstNameStudent + ", lastNameStudent="
				+ lastNameStudent + ", dateBirthStudent=" + dateBirthStudent + "]";
	}
	
	
	

}
