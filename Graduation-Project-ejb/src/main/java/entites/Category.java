package entites;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="Category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Category")
	private int id;
	@Column(name="name_Category")
	private String name_Category;
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.DETACH)
	Sheet sheet;
	@ManyToMany(cascade = CascadeType.ALL ,fetch=FetchType.EAGER)
	private Set<Teacher> teachers = new TreeSet<Teacher>();
	@ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL , fetch=FetchType.EAGER)
	private Set<Sheet> sheets = new TreeSet<Sheet>();
	
	//Getters & Setters
	
	public Sheet getSheet() {
		return sheet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	public Category(int id, String name_Category, Sheet sheet) {
		super();
		this.id = id;
		this.name_Category = name_Category;
		this.sheet = sheet;
	}
	public Category() {
		super();
	}
	public Category(String name_Category) {
		super();
		this.name_Category = name_Category;
	}
	public String getName_Category() {
		return name_Category;
	}
	public void setName_Category(String name_Category) {
		this.name_Category = name_Category;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	public Set<Sheet> getSheets() {
		return sheets;
	}
	public void setSheets(Set<Sheet> sheets) {
		this.sheets = sheets;
	}
	
	
}
