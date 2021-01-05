package entites;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//import com.fasterxml.jackson.annotation.JsonGetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PostForum implements Serializable {
	

	public List<CommentPost> getC() {
		return c;
	}

	public void setC(List<CommentPost> c) {
		this.c = c;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getId_post() {
		return id_post;
	}

	public void setId_post(int id_post) {
		this.id_post = id_post;
	}

	public String getName_post() {
		return name_post;
	}

	public void setName_post(String name_post) {
		this.name_post = name_post;
	}

	public String getDescription_post() {
		return description_post;
	}

	public void setDescription_post(String description_post) {
		this.description_post = description_post;
	}

	public String getImage_post() {
		return image_post;
	}

	public void setImage_post(String image_post) {
		this.image_post = image_post;
	}

	//@JsonIgnore
	//public User getUser() {
	////	return user;
	//}

	

	public PostForum( String name_post, String description_post,String Categorie, String image_post) {			  
		this.name_post = name_post;
		this.description_post = description_post;
		this.image_post = image_post;
		this.categorie= Categorie;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_post")
	private int id_post;
	@Column(name="etat_post")
	private String etat="non résolu";
	@Column(name="categorie")
	private String categorie;

	@Override
	public String toString() {
		return "PostForum [id_post=" + id_post + ", etat=" + etat + ", categorie=" + categorie + ", name_post="
				+ name_post + ", description_post=" + description_post + ", image_post=" + image_post + ", c=" + c
				+ ", users=" + users + "]";
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	@Column(name="name_post")
	private String name_post;

	@Column(name="description_post")
	private String description_post;

	@Column(name="image_post")
	private String image_post;

	@OneToMany(mappedBy="postforum", cascade = {CascadeType.ALL}, 
			fetch=FetchType.EAGER)
			private List<CommentPost> c= new ArrayList<>();
	
	
	@ManyToOne
	private Student users;

	public Student getUsers() {
		return users;
	}

	public void setUsers(Student users) {
		this.users = users;
	}

	public PostForum() {
		super();

}

}