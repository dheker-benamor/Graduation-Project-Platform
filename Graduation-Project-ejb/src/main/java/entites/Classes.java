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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Classes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String classname;
	private int numberofstudents;
	@JsonIgnore
	@ManyToOne
	private Options Option;
	@JsonIgnore
	@ManyToOne
	private Staff departementchief;
	public Staff getDepartementchief() {
		return departementchief;
	}
	public void setDepartementchief(Staff departementchief) {
		this.departementchief = departementchief;
	}
	@JsonIgnore
	@OneToMany(mappedBy="classe", cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	private Set<Student> student;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public int getNumberofstudents() {
		return numberofstudents;
	}
	public void setNumberofstudents(int numberofstudents) {
		this.numberofstudents = numberofstudents;
	}
	public Options getOption() {
		return Option;
	}
	public void setOption(Options option) {
		Option = option;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Option == null) ? 0 : Option.hashCode());
		result = prime * result + ((classname == null) ? 0 : classname.hashCode());
		result = prime * result + id;
		result = prime * result + numberofstudents;
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		Classes other = (Classes) obj;
		if (Option == null) {
			if (other.Option != null)
				return false;
		} else if (!Option.equals(other.Option))
			return false;
		if (classname == null) {
			if (other.classname != null)
				return false;
		} else if (!classname.equals(other.classname))
			return false;
		if (id != other.id)
			return false;
		if (numberofstudents != other.numberofstudents)
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
	@Override
	public String toString() {
		
		return "Classes [id=" + id + ", classname=" + classname + ", numberofstudents=" + numberofstudents + ", Option="
				+ Option + ", students=" + student + "]";
	}
	public Classes(int id, String classname, int numberofstudents, Options option, Set<Student> students) {
		super();
		this.id = id;
		this.classname = classname;
		this.numberofstudents = numberofstudents;
		Option = option;
		this.student = students;
	}
	public Set<Student> getStudent() {
		return student;
	}
	public void setStudent(Set<Student> student) {
		this.student = student;
	}
	public Classes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Classes(String string, Options option2) {
		this.classname=string;
		this.Option=option2;
	}
	
}
