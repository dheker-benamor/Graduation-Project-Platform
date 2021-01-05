package services;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Activity;
import entites.Project;
import entites.TaskStatus;
import entites.Task;
import entites.TaskAttachment;
import entites.TaskComment;
import iservices.TaskServiceRemote;
import utilities.Mail;

@Stateful
@LocalBean
public class TaskService implements TaskServiceRemote {
	@PersistenceContext(unitName = "Graduation-Project-ejb")
	EntityManager em;
	@EJB
	ActivityService activityservice;
    @EJB
    TaskCommentService commentservice;
	@EJB
	TaskAttachmentService ts;
	@EJB
	ProjectService ps;
	@Override
	public void AjouterTask(Task task) {
		task.setStatus(TaskStatus.TO_DO);
		task.setApproved(false);
		String description = "New task:"+task.getTitle() ;
		
		Activity activity = new Activity(description, new Date(), task.getProject());
	Project p =	ps.findProjectById(task.getProject().getId());
	em.persist(task);
	//	Mail mail = new Mail(p.getStudent().getEmail(),"new task",description );
	//	Mail mail2 = new Mail(p.getStaff().getEmail(),"new task",description );
	//	Mail mail3 = new Mail(p.getEn().getEmail(),"new task",description );
		
		activityservice.AjouterActivity(activity);
	}

	@Override
	public boolean UpdateTask(Task task) {
		String description = "Task:"+task.getTitle()+"is updated" ;
		Activity activity = new Activity(description, new Date(), task.getProject());
		activityservice.AjouterActivity(activity);
		return em.merge(task) != null;
		
	}

	@Override
	public void UpdateStatus(Task task) {
		Task task2 = em.find(Task.class, task.getId());
		String description;
		if (task.getStatus().equals(TaskStatus.DONE)) {
			task2.setStatus(task.getStatus());
			task2.setDate(new Date());
			description = "Task:"+task2.getTitle()+"is done" ;
		} else {
			task2.setStatus(task.getStatus());
			description = "Task:"+task2.getTitle()+" is "+ task.getStatus() ;
		}
		Activity activity = new Activity(description, new Date(), task2.getProject());
		activityservice.AjouterActivity(activity);
	}

	@Override
	public String UpdateApproved(Task task) {
		Task task2 = em.find(Task.class, task.getId());
		String description;
		if (task.isApproved() && task2.getStatus().equals(TaskStatus.DONE)) {
			task2.setApproved(task.isApproved());
			 description = "Task:"+task2.getTitle()+"is approved" ;
			
			
		} else if(!task.isApproved() && task2.getStatus().equals(TaskStatus.DONE)) {
			task2.setApproved(task.isApproved());
			task2.setStatus(TaskStatus.DOING);
			 description = "Task:"+task2.getTitle()+"is not approved" ;
			 
		}
		else {
			
			task2.setStatus(task2.getStatus());
			 description = "Task:"+task2.getTitle()+"is not done" ;
			 
		}
		Activity activity = new Activity(description, new Date(), task2.getProject());
		activityservice.AjouterActivity(activity);
		return description;
	}

	@Override
	public void DeleteTask(int id) {
		Task task = em.find(Task.class,id);
		
		Query query = em.createNativeQuery("DELETE FROM Task WHERE id = " + id);
		
		
		for(TaskComment tc: task.getTaskComments())
		{
			commentservice.DeleteTaskComment(tc.getId());
		}
		for(TaskAttachment tc: ts.findAllAttachments(id))
		{
			
			File fileDownload = new File("C:\\Users\\dheker\\Desktop\\" + File.separator + tc.getName());
			fileDownload.delete();
			ts.DeleteAttachment(tc.getId());
		}
		query.executeUpdate();
		String description = "Task:"+task.getTitle()+"is deleted" ;
		Activity activity = new Activity(description, new Date(), task.getProject());
		activityservice.AjouterActivity(activity);

	}

	@Override
	public Task findTaskById(int id) {
		Task task = em.find(Task.class, id);

		return task;

	}

	@Override
	public Set<Task> findTaskByProject(int id) {
		Project project = em.find(Project.class, id);
		return project.getTasks();

	}

	@Override
	public List<Task> findAllTask() {

		TypedQuery<Task> query = em.createQuery("select c from Task c", Task.class);
		return query.getResultList();
	}

	@Override
	public long getstats(TaskStatus st, int project) {
		long nombre = ((Number) em
				.createNativeQuery(
						"SELECT COUNT(*) FROM Task  WHERE status = \"" + st + "\" AND project_id = " + project)
				.getSingleResult()).longValue();
		return nombre;
	}

	@Override
	public long getapprovedstats(int ap, int project) {
		long nombre = ((Number) em.createNativeQuery(
				"SELECT COUNT(*) FROM Task WHERE status = 'DONE' AND approved = " + ap + " AND project_id = " + project)
				.getSingleResult()).longValue();
		return nombre;
	}

	@Override
	public long dateStats(String op, int project) {
		long nombre = ((Number) em
				.createNativeQuery("SELECT COUNT(*) FROM Task WHERE status = 'DONE' AND approved = 1 AND deadline " + op
						+ " date AND project_id = " + project)
				.getSingleResult()).longValue();
		return nombre;
	}

}
