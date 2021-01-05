package iservices;

import java.util.List;

import org.json.JSONObject;

import entites.Encadreurentreprise;
import entites.Project;
import entites.Staff;
import entites.Student;
import entites.User;

public interface ProjectServiceRemote {

	void AddProject(Student student, Encadreurentreprise en);

	Project findProjectById(int id);

	List<Project> findAllProjects();
	void updateProject(Student student, String title, Staff staff, String keywords);

	void AddProject(Project project);

	void DeleteProject(int id);

	Project findProjectByStudent(int student);

	List<JSONObject> links(String qry) throws Exception;

	List<Project> findProjectsByStaff(int id);

	List<Project> findProjectsBySupervisor(int id);

	User findUserById(int id);


}
