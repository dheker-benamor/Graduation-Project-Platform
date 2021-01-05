package entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VoteComment implements Serializable {
	public VoteComment() { }
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	int id_vote;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="id_Student")
	private Student users;
	
	public int getId_vote() {
		return id_vote;
	}

	public void setId_vote(int id_vote) {
		this.id_vote = id_vote;
	}
     
	@JsonIgnore
	public Student getUsers() {
		return users;
	}

	public void setUsers(Student users) {
		this.users = users;
	}
    
	@JsonIgnore
	public CommentPost getVotecomment() {
		return votecomment;
	}

	public void setVotecomment(CommentPost votecomment) {
		this.votecomment = votecomment;
	}

	@ManyToOne
	@JoinColumn(name="id_comment")
	private CommentPost votecomment;

}
