package entites;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TaskComment {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 1000)
	private String body;
	
	
	@ManyToOne
	private Task task ; 
	@OneToOne
	private User user ; 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") 
	private Date date;

	public TaskComment() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public TaskComment(String body, Task task, Date date) {
		super();
		this.body = body;
		this.task = task;
		this.date = date;
	}

	public TaskComment(String body, Task task) {
		super();
		this.body = body;
		this.task = task;
	}




	public TaskComment(String body) {
		super();
		this.body = body;
	}

	@Override
	public String toString() {
		return "TaskComments [id=" + id + ", body=" + body + ", task=" + task + ", date=" + date + "]";
	}
	
	
	

}
