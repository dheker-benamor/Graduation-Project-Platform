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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CommentPost implements Serializable  {
	

	public CommentPost(String description) {
		super();
		this.description = description;
	}

	public CommentPost() { }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_comment")
	int id_comment;
	
	@Column(name="description_comment")
	String description ;
	
	
	public int getId_comment() {
		return id_comment;
	}


	public void setId_comment(int id_comment) {
		this.id_comment = id_comment;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

    @JsonIgnore
	public PostForum getPostforum() {
		return postforum;
	}


	public void setPostforum(PostForum postforum) {
		this.postforum = postforum;
	}

	@ManyToOne
	private PostForum postforum;

	
	@ManyToOne
	@JoinColumn(name="id_Student")
	private Student s;

    
	public Student getS() {
		return s;
	}


	public void setS(Student s) {
		this.s = s;
	}
	
	
	@OneToMany(mappedBy="votecomment", cascade = {CascadeType.ALL}, 
	fetch=FetchType.EAGER)
	private List<VoteComment> v= new ArrayList<>();

	
	public List<VoteComment> getV() {
		return v;
	}

	public void setV(List<VoteComment> v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "CommentPost [id_comment=" + id_comment + ", description=" + description + ", postforum=" + postforum
				+ ", s=" + s + ", v=" + v + "]";
	}


	//@Override
	//public String toString() {
		//return "CommentPost [id_comment=" + id_comment + ", description=" + description + "]";
	//}

}
