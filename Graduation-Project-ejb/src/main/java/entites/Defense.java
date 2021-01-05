package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Defense")
public class Defense implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_defense")
	private int id_defense;
	@Temporal(TemporalType.DATE)
	@Column(name ="date_defense")
	private Date dateDefense;
	@Temporal(TemporalType.TIME)
	@Column(name ="time_defense")
	private Date timeDefense;
	@Column(name ="name_classroom")
	private String nameClassroom;
	@Column(name ="location")
	private String location;
	@Column(name ="state_defense")
	private String stateDefense;
	@Column(name="confirm_president")
	private boolean confirmPresident;
	@Column(name="confirm_encadreur")
	private boolean confirmEncadreur;
	@Column(name="confirmRapporteur")
	private boolean confirmRapporteur;
    @JsonIgnore
	@OneToMany(mappedBy="defense")
	private  List<Jury> jury=new ArrayList<Jury>();
    @JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private Sheet sheet;
	
	//Constructeurs
	public Defense() {
		super();
	}
	//Getters & Setters
	public int getId_defense() {
		return id_defense;
	}
	public void setId_defense(int id_defense) {
		this.id_defense = id_defense;
	}
	public Date getDateDefense() {
		return dateDefense;
	}
	public void setDateDefense(Date dateDefense) {
		this.dateDefense = dateDefense;
	}
	public Date getTimeDefense() {
		return timeDefense;
	}
	public void setTimeDefense(Date timeDefense) {
		this.timeDefense = timeDefense;
	}
	public String getNameClassroom() {
		return nameClassroom;
	}
	public void setNameClassroom(String nameClassroom) {
		this.nameClassroom = nameClassroom;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStateDefense() {
		return stateDefense;
	}
	public void setStateDefense(String stateDefense) {
		this.stateDefense = stateDefense;
	}
	public List<Jury> getJury() {
		return jury;
	}
	public void setJury(List<Jury> jury) {
		this.jury = jury;
	}
	public Sheet getSheet() {
		return sheet;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	public boolean isConfirmPresident() {
		return confirmPresident;
	}
	public void setConfirmPresident(boolean confirmPresident) {
		this.confirmPresident = confirmPresident;
	}
	public boolean isConfirmEncadreur() {
		return confirmEncadreur;
	}
	public void setConfirmEncadreur(boolean confirmEncadreur) {
		this.confirmEncadreur = confirmEncadreur;
	}
	public boolean isConfirmRapporteur() {
		return confirmRapporteur;
	}
	public void setConfirmRapporteur(boolean confirmRapporteur) {

		this.confirmRapporteur = confirmRapporteur;
	}
	@Override
	public String toString() {
		return "Defense [id_defense=" + id_defense + ", dateDefense=" + dateDefense + ", timeDefense=" + timeDefense
				+ ", nameClassroom=" + nameClassroom + ", location=" + location + ", stateDefense=" + stateDefense
				+ ", confirmPresident=" + confirmPresident + ", confirmEncadreur=" + confirmEncadreur
				+ ", confirmRapporteur=" + confirmRapporteur;
	}
	

	
	
	
	
}
