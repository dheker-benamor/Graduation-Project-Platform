package entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class ReportPost {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	int id_report;
	@ManyToOne
	@JoinColumn(name="id_Student")
	private Student users;
	
	@ManyToOne
	@JoinColumn(name="id_Post")
	private PostForum Post;

	public int getId_report() {
		return id_report;
	}

	public void setId_report(int id_vote) {
		this.id_report = id_vote;
	}

	public Student getUsers() {
		return users;
	}

	public void setUsers(Student users) {
		this.users = users;
	}

	public PostForum getPost() {
		return Post;
	}

	public void setPost(PostForum post) {
		Post = post;
	}
}
