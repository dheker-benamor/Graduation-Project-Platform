package entites;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jms.JMSSessionMode;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@DiscriminatorValue(value="Student")
public class Student extends User {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<OffreEntreprise> offreentreprise ;
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Skills> skills;
	@ManyToOne
	private Classes classe;
	public Classes getClasse() {
		return classe;
	}

	public void setClasse(Classes classe) {
		this.classe = classe;
	}

	public Set<OffreEntreprise> getOffreentreprise() {
		return offreentreprise;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	public void setOffreentreprise(Set<OffreEntreprise> offreentreprise) {
		this.offreentreprise = offreentreprise;
	}

	@Column(name="isActive")
	private Boolean isActive;
    @Column(name="isCredit")
    private Boolean isCredit;
    @Column(name="Money_owned")
    private int Money_owned;

    
    @OneToMany(mappedBy="users", cascade = {CascadeType.ALL}, 
			fetch=FetchType.EAGER)
			private Set<PostForum> lp;



    @JsonIgnore
    @OneToOne(mappedBy="student")
    private Sheet sheet;
    
    @OneToOne(mappedBy="student")
    private Convention convention;
	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(Boolean isCredit) {
		this.isCredit = isCredit;
	}

	public int getMoney_owned() {
		return Money_owned;
	}

	public void setMoney_owned(int money_owned) {
		Money_owned = money_owned;
	}

	@Override
	public String toString() {
		return "Student [isActive=" + isActive + ", isCredit=" + isCredit + ", Money_owned=" + Money_owned + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Student() {
		
	}
	
}
