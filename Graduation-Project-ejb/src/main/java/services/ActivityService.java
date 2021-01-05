package services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Activity;
import entites.Task;
import entites.TaskComment;
import iservices.ActivityServiceRemote;
import iservices.TaskCommentServiceRemote;


@Stateful
@LocalBean
public class ActivityService implements ActivityServiceRemote {
	@PersistenceContext(unitName="Graduation-Project-ejb")
	EntityManager em;
	
	@Override
	public void AjouterActivity(Activity activity) {
		activity.setDate(new Date());
		em.persist(activity);			
		
	}
	


	@Override
	public Activity findActivityById(int id) {
		Activity activity = em.find(Activity.class,id);
		return activity;
		
		
	}


	@Override
	public List<Activity> findAllActivities(int id) {
		TypedQuery<Activity> query= em.createQuery("select c from Activity c WHERE c.project = "+ id +" ORDER BY c.date DESC",Activity.class);
		return  query.getResultList();
	}
}
