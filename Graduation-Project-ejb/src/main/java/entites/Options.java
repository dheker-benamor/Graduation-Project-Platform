package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Options implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departement == null) ? 0 : departement.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Options other = (Options) obj;
		if (departement == null) {
			if (other.departement != null)
				return false;
		} else if (!departement.equals(other.departement))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@JsonIgnore
    @ManyToOne
	private Departement departement;
	@JsonIgnore
    @ManyToOne
	private Staff departementchief;
    
    public Staff getDepartementchief() {
		return departementchief;
	}

	public void setDepartementchief(Staff departementchief) {
		this.departementchief = departementchief;
	}

	

	@OneToMany(mappedBy="Option",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Classes> classess;
    
	

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

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	@Override
	public String toString() {
		return "Options [id=" + id + ", name=" + name + ", departement=" + ", classes="  + "]";
	}

	public Options(int id, String name, Departement departement, Set<Classes> classes) {
		super();
		this.id = id;
		this.name = name;
		this.departement = departement;
		this.classess = classes;
	}

	public Set<Classes> getClassess() {
		return classess;
	}

	public void setClassess(Set<Classes> classess) {
		this.classess = classess;
	}

	public Options() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
