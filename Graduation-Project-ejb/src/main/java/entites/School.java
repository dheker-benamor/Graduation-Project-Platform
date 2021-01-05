package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class School implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@OneToMany(mappedBy="School", cascade=CascadeType.REMOVE)
	private List<Site> Sites= new ArrayList<Site>();
	
	public List<Site> getSites() {
		return Sites;
	}

	public void setSites(List<Site> sites) {
		Sites = sites;
	}

	private String schoolname;
	
	public School(int id, String schoolname, List<Site> sites) {
		super();
		this.id = id;
		this.schoolname = schoolname;
		Sites = sites;
	}

	public School() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", schoolname=" + schoolname + ", Sites=" + Sites + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	
	
	
	
}
