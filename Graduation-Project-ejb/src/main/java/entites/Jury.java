package entites;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Jury")
public class Jury implements Serializable {
//	@EmbeddedId
//	private JuryPK juryPk;
    @Id
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_defensee", referencedColumnName = "id_defense", 
    insertable=false, updatable=false)
	private Defense defense;
	@Id
	@ManyToOne
    @JoinColumn(name = "id_teacher", referencedColumnName = "code_teacher", insertable=false, updatable=false)
	private Teacher teacher;
	@Id
	@Column(name="title_jyry")
	private String titleJyry;
	//Getters &Setters
	public Defense getDefense() {
		return defense;
	}
	public void setDefense(Defense defense) {
		this.defense = defense;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getTitleJyry() {
		return titleJyry;
	}
	public void setTitleJyry(String titleJyry) {
		this.titleJyry = titleJyry;
	}
	@Override
	public String toString() {
		return "Jury [defense=" + defense + ", teacher=" + teacher + ", titleJyry=" + titleJyry + "]";
	}
	
	
	
	
	
	
}
