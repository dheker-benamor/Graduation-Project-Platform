package services;

import java.util.ArrayList;
import java.util.List;






import javax.ejb.LocalBean;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.PostForum;
import entites.Student;
import entites.User;
import iservices.IPostForumServices;





@Stateless
@LocalBean
public class PostForumService implements IPostForumServices{
	@PersistenceContext(unitName= "Graduation-Project-ejb")
	EntityManager em;
	public  static User LoggedPerson;
	@Override
	public int ajouterPost(PostForum post) {
		System.out.println("In addPost: ");
		em.persist(post);
		System.out.println("Out of addPost"+ post.getId_post());
		return post.getId_post();
		
	}
	@Override
	public List<PostForum> findAllPosts() { 
		System.out.println("In findAllPosts: ");
		List<PostForum> pf= em.createQuery("from PostForum", PostForum.class).getResultList();
		System.out.println("Out of findAllUsers: ");
		return pf;
	}
	@Override
	public PostForum findPostById(int id) { 
		PostForum post= em.find(PostForum.class, id);
				return post;
	}
	
	@Override
	public Student findUserbyid(int id) { 
		System.out.println("In findUserById: ");
		Student s= em.find(Student.class, id);
				System.out.println("Out of findUserById: ");
				return s;
		
	}
	@Override
	public void affectrUserToPost(int id,PostForum p) {
		id = LoggedPerson.getId();
		Student s = this.findUserbyid(id);
	    p.setUsers(s);
		}
	
	@Override
	public void deletePostId(int id) {
	
Query querry = em.createNativeQuery("DELETE FROM PostForum WHERE id_post = " +id );
querry.executeUpdate();

		 
	}
	
	@Override
	public List<PostForum> RechercherPost(String s){ 
		List<PostForum> P = new ArrayList<PostForum>();
		P = this.findAllPosts();
		List<PostForum> resultat = new ArrayList<PostForum>();
		for(int i=0;i<P.size();i++) { 
			if(P.get(i).getDescription_post().contains(s)==true)
				resultat.add(P.get(i));
			else 
				i++;
		
			
		}
		return resultat;
		
		
		
	}

	@Override
	public void setEtat(PostForum p) {
		PostForum p1= em.find(PostForum.class, p.getId_post());
		String s ="Résolu";
		p1.setEtat(s);
	}
	  @Override
	   public List<PostForum> getByCategorie(String categorie){ 
		 // Query querry = em.createNativeQuery("select p.PostForum FROM PostForum p WHERE p.categorie ='"+categorie+"'" );
		  //List<PostForum> p=querry.getResultList();
		 // TypedQuery<PostForum> p = em.createQuery("SELECT c from PostForum c where c.categorie = :categorie",PostForum.class);
		 // p.setParameter("categorie", categorie);
		 //return p.getSingleResult();
		  //return p;
		   List<PostForum> l1 = this.findAllPosts();
		   List<PostForum> l2 = new ArrayList<>();
		   for(int i=0;i<l1.size();i++)
			   if(l1.get(i).getCategorie().equals(categorie)==true)
				   l2.add(l1.get(i));
			   
		   return l2;
	   }
	  
	   @Override
		public void updatePost(PostForum PostNewValues,int id) { 
			System.out.println("In updateUser: ");
			PostForum user= em.find(PostForum.class, id);
			user.setDescription_post(PostNewValues.getDescription_post());
			user.setImage_post(PostNewValues.getImage_post());
			user.setName_post(PostNewValues.getName_post());
			System.out.println("Out of updateUser: ");
			
		}
	   @Override
		public int verif (Student s,int id){ 
			
			 Query q = em.createNativeQuery("select  COUNT(*) from PostForum where id_post ="+id+" and id_student ="+s.getId());
			 int count = ((Number) q.getSingleResult()).intValue();
			 return count;
		}
	   
	   @Override
		public PostForum getbyid(int id) {
			PostForum p1= em.find(PostForum.class, id);
			return p1;
		}
}
