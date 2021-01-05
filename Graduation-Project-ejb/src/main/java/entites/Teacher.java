package entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@DiscriminatorValue(value="Teacher")

public class Teacher extends User implements Serializable,Comparable<Teacher>{
	private static final long serialVersionUID = 1L;
	@Column(name="code_teacher")
	private int codeTeacher;
	@OneToMany(mappedBy="teacher")
	private  List<Jury> jury=new ArrayList<Jury>();
	@ManyToMany(mappedBy="teachers", cascade = CascadeType.ALL)
	private Set<Category> categories=new TreeSet<Category>();
	@Column(name="nbr_soutenance")
	private int nbrSoutenance;
	@Temporal(TemporalType.DATE)
	@Column(name ="date_last_defense")
	private Date dateLastDefense;

 
	//Getters & setters
	public int getCodeTeacher() {
		return codeTeacher;
	}
	public void setCodeTeacher(int codeTeacher) {
		this.codeTeacher = codeTeacher;
	}
	@Override
	public String toString() {
		return "Teacher [codeTeacher=" + codeTeacher + "]";
	}
	public int getNbrSoutenance() {
		return nbrSoutenance;
	}
	public void setNbrSoutenance(int nbrSoutenance) {
		this.nbrSoutenance = nbrSoutenance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + codeTeacher;
		result = prime * result + ((jury == null) ? 0 : jury.hashCode());
		result = prime * result + nbrSoutenance;
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
		Teacher other = (Teacher) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (codeTeacher != other.codeTeacher)
			return false;
		if (jury == null) {
			if (other.jury != null)
				return false;
		} else if (!jury.equals(other.jury))
			return false;
		if (nbrSoutenance != other.nbrSoutenance)
			return false;
		return true;
	}
	@Override
	public int compareTo(Teacher o) {
	     if(o.getClass().equals(Teacher.class)){
	         //Nous allons trier sur le nom d'artiste
	    	 Teacher cd = (Teacher)o;
	         return this.codeTeacher-cd.getCodeTeacher();
	      }
	      return -1;
	}
	public Date getDateLastDefense() {
		return dateLastDefense;
	}
	public void setDateLastDefense(Date dateLastDefense) {
		this.dateLastDefense = dateLastDefense;
	}

	
	

}
