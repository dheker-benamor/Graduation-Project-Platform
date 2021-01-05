package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.PostForum;
import entites.Student;


@Remote
public interface IPostForumServices {
	public int ajouterPost(PostForum post);

	public List<PostForum> findAllPosts();

	public PostForum findPostById(int id);

	public Student findUserbyid(int id);

	public void affectrUserToPost(int id,PostForum p);

	public void deletePostId(int id);

	public List<PostForum> RechercherPost(String s);

	

	public void setEtat(PostForum p);

	public PostForum getbyid(int id);

	public int verif(Student s, int id);

	public void updatePost(PostForum PostNewValues, int id);

	public List<PostForum> getByCategorie(String categorie);
}
