package entites;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	@Column(length = 1000)
	private String description;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean approved;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") 
    @Temporal (TemporalType.DATE)
	private Date deadline;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  
    @Temporal (TemporalType.DATE)
	private Date date;
	
	
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	

	@ManyToOne
	private Project project;
	
	@OneToMany(mappedBy="task", cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<TaskComment> taskComments ;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public Set<TaskComment> getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(Set<TaskComment> taskComments) {
		this.taskComments = taskComments;
	}

	public Task() {
		super();
	}

	public Task(String title) {
		super();
		this.title = title;
	}

	public Task( String title, String description, Date deadline, TaskStatus status) {
		super();
	
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.status = status;
	}
	

	public Task(String title, String description, Date deadline, TaskStatus status, Project project) {
		super();
		this.title = title;
		this.description = description;
		this.deadline = deadline;

		this.status = status;
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Boolean isApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
}
