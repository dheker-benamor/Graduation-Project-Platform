package services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Activity;
import entites.Task;
import entites.TaskComment;
import iservices.TaskCommentServiceRemote;


@Stateful
@LocalBean
public class TaskCommentService implements TaskCommentServiceRemote {
	@PersistenceContext(unitName="Graduation-Project-ejb")
	EntityManager em;
	
	@Override
	public void AjouterTaskComment(TaskComment taskcomment) {
		taskcomment.setDate(new Date());
		
		em.persist(taskcomment);			
		
	}
	

	@Override
	public void UpdateTaskComment(TaskComment taskcomment) {
		TaskComment taskcomment2 = em.find(TaskComment.class, taskcomment.getId());	
		taskcomment2.setBody(taskcomment.getBody());	
	}


	@Override
	public void DeleteTaskComment(int id) {
		//Task task = em.find(Task.class,id);
		//em.remove(task);
		Query query = em.createNativeQuery("DELETE FROM TaskComment WHERE id = " + id);
		query.executeUpdate();
		
		
	}
	@Override
	public TaskComment findTaskCommentById(int id) {
		TaskComment comment = em.find(TaskComment.class,id);
		return comment;
		
		
	}


	@Override
	public Set<TaskComment> findAllTaskComments(int id) {
		Task task = em.find(Task.class,id);
		return task.getTaskComments();
	}
	@Override
	public List<TaskComment> findCommentsByTask(int id) {
		TypedQuery<TaskComment> query= em.createQuery("select c from TaskComment c WHERE c.task = "+ id +" ORDER BY c.date DESC",TaskComment.class);
		return  query.getResultList();
	}
}
