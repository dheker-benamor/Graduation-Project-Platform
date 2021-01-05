package entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Departement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToOne
	private Site site;
	
	@OneToMany(mappedBy="departement")
	private List<Options> options;

	public List<Options> getOptions() {
		return options;
	}
	@OneToOne
	private Staff departementmanager;
	public Staff getDepartementmanager() {
		return departementmanager;
	}

	public void setDepartementmanager(Staff departementmanager) {
		this.departementmanager = departementmanager;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

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

	


	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}



	public Departement(int id, String name, Site site) {
		super();
		this.id = id;
		this.name = name;
		this.site=site;
		
	}

	
	@Override
	public String toString() {
		return "Departement [id=" + id + ", name=" + name + ", site=" + site +  "]";
	}

	public Departement() {
		super();
		
	}
	
	
	
	
}
