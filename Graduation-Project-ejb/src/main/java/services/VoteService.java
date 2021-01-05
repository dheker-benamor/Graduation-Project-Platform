package services;

import java.util.List;

import javax.ejb.LocalBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.CommentPost;
import entites.PostForum;
import entites.Student;
import entites.VoteComment;
import iservices.IVoteService;

@Stateless
@LocalBean
public class VoteService implements IVoteService{
	@PersistenceContext(unitName= "Graduation-Project-ejb")
	EntityManager em;
	
	@Override
	public int ajouterVote(VoteComment v,int id) {
		CommentPost cp= em.find(CommentPost.class, id);
		v.setVotecomment(cp);
		em.persist(v);
		return v.getId_vote();
		
		
	}

	@Override
	public List<VoteComment> getNbrVotePerComment2(int id) {
	 //Query q = em.createNativeQuery("select COUNT(*) from VoteComment where id_comment ="+id);
	 //int count = ((Number) q.getSingleResult()).intValue();
	// return count;
		CommentPost cp= em.find(CommentPost.class, id);
		return cp.getV() ;
	}
	
	@Override
	public int verif (Student s,int id){ 
		
		 Query q = em.createNativeQuery("select  COUNT(*) from VoteComment where id_comment ="+id+" and id_student ="+s.getId());
		 int count = ((Number) q.getSingleResult()).intValue();
		 return count;
	}

	@Override
	public int getNbrVotePerComment(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
  
}
