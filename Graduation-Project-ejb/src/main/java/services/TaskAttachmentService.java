package services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Activity;
import entites.Task;
import entites.TaskAttachment;
import iservices.TaskAttachmentServiceRemote;



@Stateful
@LocalBean
public class TaskAttachmentService implements TaskAttachmentServiceRemote {
	@PersistenceContext(unitName="Graduation-Project-ejb")
	EntityManager em;
	@EJB
	ActivityService activityservice;
	@Override
	public void AddAttachment(String name, int id) {
		Task task = em.find(Task.class,id);	
		TaskAttachment attachment =new TaskAttachment(name,task);
		String description = "New attachment add to "+task.getTitle() ;
		Activity activity = new Activity(description, new Date(), task.getProject());
		em.persist(attachment);	
		activityservice.AjouterActivity(activity);
	}



	@Override
	public TaskAttachment findAttachmentById(int id) {
		TaskAttachment attachment = em.find(TaskAttachment.class,id);
		return attachment;
	}
	@Override
	public void DeleteAttachment(int id) {
		TaskAttachment attachment = em.find(TaskAttachment.class,id);
		
		Query query = em.createNativeQuery("DELETE FROM TaskAttachment WHERE id = " + id);
		query.executeUpdate();
		String description = "Attachment deleted from "+ attachment.getTask().getTitle() ;
		Activity activity = new Activity(description, new Date(), attachment.getTask().getProject());
		activityservice.AjouterActivity(activity);

	}



	@Override
	public List<TaskAttachment> findAllAttachments(int id) {
		TypedQuery<TaskAttachment> query= em.createQuery("select c from TaskAttachment c WHERE c.task = "+ id ,TaskAttachment.class);
		return  query.getResultList();
	}
}
