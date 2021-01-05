package entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Convention implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="Id_convention")
	private int id_convention;
	@Column(name="starting_date")
	private String starting_date;
	@Column(name="ending_date")
	private String ending_date;
	@Column(name="isAcceptedByStaff")
	private Boolean isAcceptedByStaff;
	@Column(name="isAcceptedByCompany")
	private Boolean isAcceptedByCompany;
	
	
	@OneToOne
	@JsonIgnore
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	private Staff staff;
	
	
	
	

	public int getId_convention() {
		return id_convention;
	}

	public void setId_convention(int id_convention) {
		this.id_convention = id_convention;
	}

	public String getStarting_date() {
		return starting_date;
	}

	public void setStarting_date(String starting_date) {
		this.starting_date = starting_date;
	}

	public String getEnding_date() {
		return ending_date;
	}

	public void setEnding_date(String ending_date) {
		this.ending_date = ending_date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	

	public Convention() {
		super();
	}
	
}
