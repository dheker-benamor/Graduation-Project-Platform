package entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_skills")
public class user_skills implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int student_id;
	private int skills_id;
	private int rate ;
	
	public user_skills(int rate) {
		super();
		this.rate = rate;
	}
	public user_skills() {
		super();
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getSkills_id() {
		return skills_id;
	}
	public void setSkills_id(int skills_id) {
		this.skills_id = skills_id;
	}
	@Override
	public String toString() {
		return "user_skills [id=" + id + ", student_id=" + student_id + ", skills_id=" + skills_id + ", rate=" + rate
				+ "]";
	}
	

}
