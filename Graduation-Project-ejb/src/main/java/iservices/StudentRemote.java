package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.Category;
import entites.Sheet;
import entites.Student;
import entites.User;

@Remote
public interface StudentRemote {
	public int addStudent(Student s);

	int RequestStudetRegistration(Student s);
	public List<Student> DisplayStudentRequest();

	Student AcceptRegestration(int idStudent);

	Student findStudentbyId(int idStudent);
    Boolean ModifySheetWhenAccepted (Sheet sheet,int idSheet);
    Boolean RequestModify (Sheet sheet , int idSheet);
	int addSheet(Sheet sheet);
	

	Boolean ModifySheet(Sheet sheet, int idSheet);

	Boolean RequestCancelProject(Sheet sheet, int idSheet);

	Sheet DisplayMySheet(int idStudent);

	int addCategory(Category category, int idSheet);

	Boolean ModifyCategory(Category category, int idCategory);

	void DeleteCategory( int idCategory);

	boolean GetSheetpdf(int id);
//
	









	public String getEmailStudentById(int id);



	
}
