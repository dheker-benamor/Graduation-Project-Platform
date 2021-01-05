package services;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.CommentPost;
import entites.PostForum;
import entites.VoteComment;
import iservices.ICommentPostServices;




@Stateless
@LocalBean
public class CommentPostService implements ICommentPostServices{
	@PersistenceContext(unitName= "Graduation-Project-ejb")
	EntityManager em;
	@EJB(beanName = "VoteService")
	VoteService vs;
	
	@Override
	public int ajouterComment(CommentPost cp,int id) {
		PostForum p= em.find(PostForum.class, id);
		cp.setPostforum(p);
		em.persist(cp);
		return cp.getId_comment();
		
		
	}
	
	
	@Override
	public List<CommentPost> getAllCommentsByPost(int postId) {
		PostForum p = em.find(PostForum.class,postId);
	
		List<CommentPost> c = p.getC();
	
		
		return c;
	}
	
	@Override
	public List<String> getAll(int postId) {
		
		PostForum p = em.find(PostForum.class,postId);// nlawej  fel post
	
		List<CommentPost> c = p.getC();//n'affecti fel les commmentaire mtaa el post fi List
	     List<String> L = new ArrayList<String>();// lista eli bch n'affichi feha kol chay
	     for(int i=0;i<c.size();i++) { 
	    	String r1= c.get(i).getDescription();//nekhou fel description mtaa el comment 
	    	String r4 = c.get(i).getS().getNom();
	    	int r2 = 0;//vs.getNbrVotePerComment(c.get(i).getId_comment());// nekhou fel nbr de vote mtaa kol comment a traver un service 
	    	String r3 = "id: "+c.get(i).getId_comment()+" / "+"autheur:"+" "+r4+" "+" / "+"description: "+"  "+ r1 +" / " + "Nbr de votes: " + "  "+ r2;//nbr de like per comment fi string
	    	L.add(r3);//addi fel string 
	     }
		
		return L;
	}
	
	@Override
	public void signalerPost(int postId) { 
		PostForum p = em.find(PostForum.class,postId);
		
	}

	@Override
	public List<CommentPost> getAllComments() {
	List<CommentPost> emp = em.createQuery("Select e from CommentPost e",CommentPost.class).getResultList();
	return emp;
	}
	
   @Override
   public List<String> Notifs(int id){ 
	   List<CommentPost> L = this.getAllComments();
	   List<String> f = new ArrayList<>();
	   for(int i =0;i<L.size();i++) { 
		   if (L.get(i).getPostforum().getUsers().getId()==id)
		   {
		   String r1 = L.get(i).getS().getNom();
		   String r11 = L.get(i).getS().getPrenom();
		   String r2 = L.get(i).getPostforum().getName_post();
		   String r3 = L.get(i).getPostforum().getUsers().getNom();
		   String r4 = r1 + " " + r11 +" " + " a commenté Votre publication  " + r2 ;
		   f.add(r4);
		   }
		   else 
			   i++;
	   }
	   return f;
	   
	   
   }


@Override
public List<VoteComment> getAllVotePerComment(int postId) {
	CommentPost p = em.find(CommentPost.class,postId);
	return p.getV();
	
}
}
