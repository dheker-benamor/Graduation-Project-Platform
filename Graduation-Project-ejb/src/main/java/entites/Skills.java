package entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="skills")
public class Skills implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Enumerated(EnumType.STRING)
	private SkillName skill;
	@JsonIgnore
	@ManyToMany(mappedBy="skills")
	private List<Student> student;
	@JsonIgnore
	@ManyToMany(mappedBy="skills")
	private List<OffreEntreprise> offreentreprise;
	
	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public List<OffreEntreprise> getOffreentreprise() {
		return offreentreprise;
	}

	public void setOffreentreprise(List<OffreEntreprise> offreentreprise) {
		this.offreentreprise = offreentreprise;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	


	public SkillName getSkill() {
		return skill;
	}

	public void setSkill(SkillName skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "Skills [id=" + id + ", skill=" + skill + ", student=" + student + ", offreentreprise=" + offreentreprise
				+ "]";
	}
	
	

}
