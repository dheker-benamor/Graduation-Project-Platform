package entites;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_offreentreprise")

public class User_offreentreprise implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int student_id;
	private int offreentreprise_id;

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

	public int getOffreentreprise_id() {
		return offreentreprise_id;
	}

	public void setOffreentreprise_id(int offreentreprise_id) {
		this.offreentreprise_id = offreentreprise_id;
	}

	@Override
	public String toString() {
		return "User_offreentreprise [student_id=" + student_id + ", offreentreprise_id=" + offreentreprise_id + "]";
	}

}
