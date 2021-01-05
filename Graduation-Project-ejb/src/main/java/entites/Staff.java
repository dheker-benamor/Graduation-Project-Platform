package entites;


import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DiscriminatorValue(value="Staff")
public class Staff extends User {
	private static final long serialVersionUID = 1L;

	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role;
    @Column(name="main_skill")
    private String main_skill;
    @Column(name="secondary_skill")
    private String secondary_skill;
    @JsonIgnore
    @OneToMany(mappedBy="staff", fetch=FetchType.EAGER)
    private Set<Convention> convention;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="departementchief", fetch=FetchType.EAGER)
    private Set<Classes> classes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="departementchief", fetch=FetchType.EAGER)
    private Set<Options> option;
    @OneToOne
    private Departement departement;
    public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Set<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}

	public Set<Options> getOption() {
		return option;
	}

	public void setOption(Set<Options> option) {
		this.option = option;
	}

	@ManyToMany(mappedBy="StaffSheets", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonBackReference
    @JsonIgnore  
    private List<Sheet> Sheet ;
    
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Set<Convention> getConvention() {
		return convention;
	}

	public void setConvention(Set<Convention> convention) {
		this.convention = convention;
	}
	@JsonIgnore 
	public List<Sheet> getSheet() {
		return Sheet;
	}
	 
	public void setSheet(List<Sheet> sheet) {
		Sheet = sheet;
	}

	public String getMain_skill() {
		return main_skill;
	}

	public void setMain_skill(String main_skill) {
		this.main_skill = main_skill;
	}

	public String getSecondary_skill() {
		return secondary_skill;
	}

	public void setSecondary_skill(String secondary_skill) {
		this.secondary_skill = secondary_skill;
	}

	public Staff(String nom, String prenom, String email, String password, Role role, String main_skill,
			String secondary_skill, Set<Convention> convention, List<entites.Sheet> sheet) {
		super(nom, prenom, email, password);
		this.role = role;
		this.main_skill = main_skill;
		this.secondary_skill = secondary_skill;
		this.convention = convention;
		Sheet = sheet;
	}

	public Staff() {
		
	}
	
}
