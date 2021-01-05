package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@Table(name ="entreprise")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Entreprise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nomEntreprise;
	private String identifiant;
	private String numtelentre;
	private String mail, adresse, siteweb, lienlinkedin, description;

	private String password;
	private String logoentreprise;
	private String presentation;
	
	
	private int autorise;
   
	@JsonBackReference
	@OneToMany(mappedBy="entreprise",fetch=FetchType.EAGER)
	private List<OffreEntreprise> offreentreprise = new ArrayList<>() ;

	public int getAutorise() {
		return autorise;
	}

	public void setAutorise(int autorise) {
		this.autorise = autorise;
	}

	

	public List<OffreEntreprise> getOffreentreprise() {
		return offreentreprise;
	}

	public void setOffreentreprise(List<OffreEntreprise> offreentreprise) {
		this.offreentreprise = offreentreprise;
	}

	

public Entreprise() {

	}

	public Entreprise(String nomEntreprise, String identifiant, String numtelentre, String mail, String adresse,
			String siteweb, String lienlinkedin, String description, String mdp, String logoentreprise,
			String presentation, String token) {
		super();
		this.nomEntreprise = nomEntreprise;
		this.identifiant = identifiant;
		this.numtelentre = numtelentre;
		this.mail = mail;
		this.adresse = adresse;
		this.siteweb = siteweb;
		this.lienlinkedin = lienlinkedin;
		this.description = description;
		this.password = mdp;
		this.logoentreprise = logoentreprise;
		this.presentation = presentation;
		
	}
	
	public Entreprise(String nomEntreprise, String identifiant, String mdp) {
		super();

		this.nomEntreprise = nomEntreprise;
		this.identifiant = identifiant;
		this.password = mdp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSiteweb() {
		return siteweb;
	}

	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}

	public String getLienlinkedin() {
		return lienlinkedin;
	}

	public void setLienlinkedin(String lienlinkedin) {
		this.lienlinkedin = lienlinkedin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogoentreprise() {
		return logoentreprise;
	}

	public void setLogoentreprise(String logoentreprise) {
		this.logoentreprise = logoentreprise;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getNumtelentre() {
		return numtelentre;
	}

	public void setNumtelentre(String numtelentre) {
		this.numtelentre = numtelentre;
	}

	@Override
	public String toString() {
		return "Entreprise [id=" + id + ", nomEntreprise=" + nomEntreprise + ", identifiant=" + identifiant
				+ ", numtelentre=" + numtelentre + ", mail=" + mail + ", adresse=" + adresse + ", siteweb=" + siteweb
				+ ", lienlinkedin=" + lienlinkedin + ", description=" + description + ", password=" + password
				+ ", logoentreprise=" + logoentreprise + ", presentation=" + presentation + ", autorise=" + autorise
				+ ", offreentreprise=" + offreentreprise + "]";
	}

	
	

}
