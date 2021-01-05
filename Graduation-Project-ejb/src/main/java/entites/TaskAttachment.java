package entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TaskAttachment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@ManyToOne
	Task task;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TaskAttachment() {
		super();
	}

	public TaskAttachment(int id, String name, Task task) {
		super();
		this.id = id;
		this.name = name;
		this.task = task;
	}

	public TaskAttachment(String name, Task task) {
		super();
		this.name = name;
		this.task = task;
	}
	
	
}
