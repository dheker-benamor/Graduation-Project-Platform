package services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.json.JSONObject;

import entites.Encadreurentreprise;
import entites.Project;
import entites.Staff;
import entites.Student;
import entites.User;
import iservices.ProjectServiceRemote;
import utilities.Search;

@Stateful
@LocalBean
public class ProjectService implements ProjectServiceRemote {
	@PersistenceContext(unitName = "Graduation-Project-ejb")
	EntityManager em;
	
	@Override
	public void AddProject(Project project) {
		project.setDatedebut(new Date());
		em.persist(project);

	}
	@Override
	public void AddProject(Student student, Encadreurentreprise en) {

		Project project = new Project(student, en);
		em.persist(project);

	}
	@Override
	public void updateProject(Student student,String title ,Staff staff ,String keywords) {

		Project project =  findProjectByStudent(student.getId());
		Project project2 = em.find(Project.class, project.getId());	
		project2.setTitle(title);
		project2.setStaff(staff);
		project2.setKeywords(keywords);
		

	}

	@Override
	public Project findProjectById(int id) {
		Project project = em.find(Project.class, id);
		return project;
	}
	@Override
	public User findUserById(int id) {
		User user = em.find(User.class, id);
		return user;
	}

	@Override
	public List<Project> findAllProjects() {
		TypedQuery<Project> query = em.createQuery("select c from Project c ORDER BY c.datedebut DESC", Project.class);
		return query.getResultList();
	}
	@Override
	public List<Project> findProjectsByStaff(int id) {
		Staff staff = em.find(Staff.class, id);
		TypedQuery<Project> query = em.createQuery("select c from Project c where c.staff = "+ id+" ORDER BY c.datedebut DESC", Project.class);
		return query.getResultList();
	}
	@Override
	public List<Project> findProjectsBySupervisor(int id) {
		Encadreurentreprise en = em.find(Encadreurentreprise.class, id);
		TypedQuery<Project> query = em.createQuery("select c from Project c where c.en = "+ id +" ORDER BY c.datedebut DESC", Project.class);
		return query.getResultList();
	}
	@Override
	public List<JSONObject> links(String qry) throws Exception {
		//System.out.println(Search.googleSearch(qry));
		List<JSONObject> links = Search.googleSearch(qry);
		return links;
	}
	@Override
	public Project findProjectByStudent(int student) {
		Student student2 = em.find(Student.class, student);
		Project project = em.createQuery("select e from Project e where e.student = "+student, Project.class).getSingleResult();
		return project;
	}
	@Override
	public void DeleteProject(int id) {
		Query query = em.createNativeQuery("DELETE FROM Project WHERE id = " + id);
		query.executeUpdate();
		
	}
}
