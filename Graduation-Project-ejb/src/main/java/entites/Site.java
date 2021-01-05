package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Site")
public class Site implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String Sitename;
	private String adresse;
	private int codePostal;
	public int getId() {
		return id;
	}
	public Site() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((School == null) ? 0 : School.hashCode());
		result = prime * result + ((Sitename == null) ? 0 : Sitename.hashCode());
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + codePostal;
		result = prime * result + ((departements == null) ? 0 : departements.hashCode());
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site other = (Site) obj;
		if (School == null) {
			if (other.School != null)
				return false;
		} else if (!School.equals(other.School))
			return false;
		if (Sitename == null) {
			if (other.Sitename != null)
				return false;
		} else if (!Sitename.equals(other.Sitename))
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (codePostal != other.codePostal)
			return false;
		if (departements == null) {
			if (other.departements != null)
				return false;
		} else if (!departements.equals(other.departements))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	public Site(int id, String sitename, String adresse, int codePostal, entites.School school,
			List<Departement> departements) {
		super();
		this.id = id;
		Sitename = sitename;
		this.adresse = adresse;
		this.codePostal = codePostal;
		School = school;
		this.departements = departements;
	}
	@Override
	public String toString() {
	
		
		return "Site [id=" + id + ", Sitename=" + Sitename + ", adresse=" + adresse + ", codePostal=" + codePostal
				+ ", School="  + ", departements="  + "]";
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSitename() {
		return Sitename;
	}
	public void setSitename(String sitename) {
		Sitename = sitename;
	}
	public School getSchool() {
		return School;
	}
	public void setSchool(School school) {
		School = school;
	}
	public List<Departement> getDepartements() {
		return departements;
	}
	public void setDepartements(List<Departement> departements) {
		this.departements = departements;
	}
	@ManyToOne
	private School School;
	@JsonIgnore
	@OneToMany(mappedBy="site", cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	private List<Departement> departements= new ArrayList<Departement>();
	
	
	
	
}
