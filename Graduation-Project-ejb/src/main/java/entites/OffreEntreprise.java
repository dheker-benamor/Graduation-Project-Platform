package entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="offreentreprise")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OffreEntreprise implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String annonce;
	@Temporal(TemporalType.DATE)
	private Date datedebut;
	@Temporal(TemporalType.DATE)
	private Date datefin;
	private String ville;
	private String localisation;
	private String mission;
	
	private String detailsstage;
	private boolean embauche;
	private String profilrech;
	
	@Enumerated(EnumType.STRING)
	private TypeO type;
	 
	
	@ManyToOne(fetch=FetchType.EAGER)
	Entreprise entreprise;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	Category categories;
	@JsonIgnore
	@ManyToMany(mappedBy="offreentreprise",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Student> student ;
	@JsonIgnore
	@ManyToMany
	private List<Skills> skills;
	
	
	public List<Skills> getSkills() {
		return skills;
	}
	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}
	public List<Student> getStudent() {
		return student;
	}
	public void setStudent(List<Student> student) {
		this.student = student;
	}
	public TypeO getType() {
		return type;
	}
	public void setType(TypeO type) {
		this.type = type;
	}
	public Category getCategories() {
		return categories;
	}
	public void setCategories(Category categories) {
		this.categories = categories;
	}
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnnonce() {
		return annonce;
	}
	public void setAnnonce(String annonce) {
		this.annonce = annonce;
	}
	
	public Date getDatedebut() {
		return datedebut;
	}
	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getLocalisation() {
		return localisation;
	}
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getDetailsstage() {
		return detailsstage;
	}
	public void setDetailsstage(String detailsstage) {
		this.detailsstage = detailsstage;
	}
	public boolean isEmbauche() {
		return embauche;
	}
	public void setEmbauche(boolean embauche) {
		this.embauche = embauche;
	}
	public String getProfilrech() {
		return profilrech;
	}
	public void setProfilrech(String profilrech) {
		this.profilrech = profilrech;
	}
	@Override
	public String toString() {
		return "OffreEntreprise [id=" + id + ", annonce=" + annonce + ", datedebut=" + datedebut + ", datefin="
				+ datefin + ", ville=" + ville + ", localisation=" + localisation + ", mission=" + mission
				+ ", detailsstage=" + detailsstage + ", embauche=" + embauche + ", profilrech=" + profilrech + ", type="
				+ type + ", entreprise=" + entreprise.getNomEntreprise()  ;
	}
	
	
	
	
	
	
	
//	@Override
//	public String toString() {
//		return "OffreEntreprise [id=" + id + ", annonce=" + annonce + ", datedebut=" + datedebut + ", datefin="
//				+ datefin + ", ville=" + ville + ", localisation=" + localisation + ", mission=" + mission
//				+ ", detailsstage=" + detailsstage + ", embauche=" + embauche + ", profilrech=" + profilrech + "]";
//	}
	
	
	
	
	
	
	
	

}
