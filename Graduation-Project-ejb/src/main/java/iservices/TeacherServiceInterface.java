package iservices;

import entites.Teacher;

public interface TeacherServiceInterface {

	public int getCodeTeacherById(int idTeacher);

	public void updateNumberDefense(int codeenseignant);

	public int getTheNumberOfeacher(int codeenseignant);
	
	public String getTheEmailOfEncadreurById(int idTeacher);
	
	public Teacher getTheTeacherById(int id);
}
