package entites;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
public class Sheet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_sheet")
	private int id_sheet;
	@Column(name="title")
	private String title;
	@Column(name="key_words")
	private String key_words;
	@Column(name="description")
	private String description;
	@Column(name="issue")
	private String issue;
	@Column(name="features")
	private String features;
	@Column(name="Accepted")
	private Boolean Accepted;
	@Column(name="refuse_Reason")
	private String refuse_Reason;
	@Column(name="Date")
	private Date date;
	@Column(name="Request_Modification")
	private Boolean Request_Modification;
	@Column(name="featuresRequest")
	private String featuresRequest;
	@Column(name="issueRequest")
	private String issueRequest;
	@Column(name="refuseModificationReason")
	private String refuseModificationReason;
	@Column(name="cancelProject")
	private Boolean canelProject;
	@Column(name="acceptAnnulation")
	private String acceptAnnulation;
	@Column(name="refusAnnulation")
	private String refusAnnulation;
	@Enumerated(EnumType.STRING)
	private SheetStatus sheetstatus;
	
	@OneToOne
	private Student student;
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="sheet", fetch=FetchType.EAGER)
	
	private Set<Category>  categorys;
	
	@ManyToMany(fetch=FetchType.EAGER )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
			  property = "Sheet_id_sheet")
	
	private List<Staff> StaffSheets = new ArrayList<>(); 
	//
	@OneToOne
	private Teacher encadreur;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<Category> categories= new TreeSet<Category>();
	@JsonIgnore
	@OneToOne(mappedBy="sheet")
	private Defense defense;
	@Column(name="programmed_defense")
	private boolean programmed_defense;
	@Column(name="markReporter")
	private int markReporter;
	@OneToOne
	private Teacher reporter;
	@OneToOne
	private Teacher president;
	
	
	
	
	
	public Set<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}

	public List<Staff> getStaffSheets() {
		return StaffSheets;
	}

	public void setStaffSheets(List<Staff> staffSheets) {
		StaffSheets = staffSheets;
	}

	

	public String getAcceptAnnulation() {
		return acceptAnnulation;
	}

	public void setAcceptAnnulation(String acceptAnnulation) {
		this.acceptAnnulation = acceptAnnulation;
	}

	public String getRefusAnnulation() {
		return refusAnnulation;
	}

	public void setRefusAnnulation(String refusAnnulation) {
		this.refusAnnulation = refusAnnulation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRefuse_Reason() {
		return refuse_Reason;
	}

	public void setRefuse_Reason(String refuse_Reason) {
		this.refuse_Reason = refuse_Reason;
	}

	public Boolean getAccepted() {
		return Accepted;
	}

	public void setAccepted(Boolean accepted) {
		Accepted = accepted;
	}
	public int getId_sheet() {
		return id_sheet;
	}
	

	public void setId_sheet(int id_sheet) {
		this.id_sheet = id_sheet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey_words() {
		return key_words;
	}

	public void setKey_words(String key_words) {
		this.key_words = key_words;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Boolean getRequest_Modification() {
		return Request_Modification;
	}

	public void setRequest_Modification(Boolean request_Modification) {
		Request_Modification = request_Modification;
	}
	

	public String getFeaturesRequest() {
		return featuresRequest;
	}

	public void setFeaturesRequest(String featuresRequest) {
		this.featuresRequest = featuresRequest;
	}

	public String getIssueRequest() {
		return issueRequest;
	}

	public void setIssueRequest(String issueRequest) {
		this.issueRequest = issueRequest;
	}


	public String getRefuseModificationReason() {
		return refuseModificationReason;
	}

	public void setRefuseModificationReason(String refuseModificationReason) {
		this.refuseModificationReason = refuseModificationReason;
	}

	public Boolean getCanelProject() {
		return canelProject;
	}

	public void setCanelProject(Boolean canelProject) {
		this.canelProject = canelProject;
	}
	
	public SheetStatus getSheetstatus() {
		return sheetstatus;
	}

	public void setSheetstatus(SheetStatus sheetstatus) {
		this.sheetstatus = sheetstatus;
	}

	public Sheet(int id_sheet, String title, String key_words, String description, String issue, String features,
			Boolean accepted, String refuse_Reason, Date date, Boolean request_Modification, String featuresRequest,
			String issueRequest, String refuseModificationReason, Boolean canelProject, String acceptAnnulation,
			String refusAnnulation, SheetStatus sheetstatus, Student student, List<Staff> staffSheets,
			Set<Category> categorys) {
		super();
		this.id_sheet = id_sheet;
		this.title = title;
		this.key_words = key_words;
		this.description = description;
		this.issue = issue;
		this.features = features;
		Accepted = accepted;
		this.refuse_Reason = refuse_Reason;
		this.date = date;
		Request_Modification = request_Modification;
		this.featuresRequest = featuresRequest;
		this.issueRequest = issueRequest;
		this.refuseModificationReason = refuseModificationReason;
		this.canelProject = canelProject;
		this.acceptAnnulation = acceptAnnulation;
		this.refusAnnulation = refusAnnulation;
		this.sheetstatus = sheetstatus;
		this.student = student;
		StaffSheets = staffSheets;
		this.categorys = categorys;
	}

	public Sheet() {
		super();
	}

	public Sheet(String refuseModificationReason) {
		super();
		this.refuseModificationReason = refuseModificationReason;
	}

	public Teacher getEncadreur() {
		return encadreur;
	}

	public void setEncadreur(Teacher encadreur) {
		this.encadreur = encadreur;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Defense getDefense() {
		return defense;
	}

	public void setDefense(Defense defense) {
		this.defense = defense;
	}

	public boolean isProgrammed_defense() {
		return programmed_defense;
	}

	public void setProgrammed_defense(boolean programmed_defense) {
		this.programmed_defense = programmed_defense;
	}

	public int getMarkReporter() {
		return markReporter;
	}

	public void setMarkReporter(int markReporter) {
		this.markReporter = markReporter;
	}

	public Teacher getReporter() {
		return reporter;
	}

	public void setReporter(Teacher reporter) {
		this.reporter = reporter;
	}

	public Teacher getPresident() {
		return president;
	}

	public void setPresident(Teacher president) {
		this.president = president;
	}
	

	

	
	
}
