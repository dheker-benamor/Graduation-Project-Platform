package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.PostForum;
import entites.ReportPost;

import iservices.IReportService;


@Stateless
@LocalBean
public class ReportService implements IReportService {
	@PersistenceContext(unitName= "Graduation-Project-ejb")
	EntityManager em;
	@Override
	public void ajouterReport(ReportPost v,int id) {
		PostForum cp= em.find(PostForum.class, id);
		v.setPost(cp);
		em.persist(v);
	
		
		
		
	}
	
	@Override
	public int getNbrReportPerPost(int id) {
	 Query q = em.createNativeQuery("select COUNT(*) from ReportPost where id_post ="+id);
	 int count = ((Number) q.getSingleResult()).intValue();
	 return count;
	
	}
	@Override
	public void deletePostIdfromReport(int id) {
	
Query querry = em.createNativeQuery("DELETE FROM ReportPost WHERE id_post = " +id );
querry.executeUpdate();

		 
	}
	
}
