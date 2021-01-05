package iservices;

import javax.ejb.Local;

import entites.Student;
@Local
public interface StudentLocal {
	public int addStudent(Student s);

	public String getEmailStudentById(int id);
}
