package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import entites.Category;
import entites.Email;
import entites.Role;
import entites.Sheet;
import entites.SheetStatus;
import entites.Staff;
import entites.Student;
import entites.User;
import iservices.StaffLocal;
import iservices.StaffRemote;
import utilities.CryptPasswordMD5;

@Stateless
@LocalBean
public class StaffService implements StaffRemote ,StaffLocal{
	@PersistenceContext
	EntityManager em;
	Email email ;
	AuthenticationService as;
	private CryptPasswordMD5 cryptPasswordMD5 = new CryptPasswordMD5();
	@Override
	public void consommation() {

		// create new JAX-RS Client
		Client client = ClientBuilder.newClient();
		// The base URL of Web service
		WebTarget target = client.target("http://localhost:9080/PIDEV-web/rest/");
//		WebTarget hello = target.path("Entreprise");
		
		Response response = target.request().get();
		String result = response.readEntity(String.class);
		System.out.println(result);
		response.close();

	}
	@Override
	public int addStaff(Staff e) {
      em.persist(e);
      e.setPassword(cryptPasswordMD5.cryptWithMD5(e.getPassword()));
      return e.getId();
	}
	@Override
	public int addSupervisor(Staff e) {
      em.persist(e);
      e.setPassword(cryptPasswordMD5.cryptWithMD5(e.getPassword()));
      e.setRole(Role.SUPERVISOR);
      return e.getId();
	}@Override
	public int adddepartmentManager(Staff e) {
	      em.persist(e);
	      e.setPassword(cryptPasswordMD5.cryptWithMD5(e.getPassword()));
	      e.setRole(Role.DEPARTEMENT_MANAGER);
	      return e.getId();
		}
	@Override
	public List<User> ListeStaff() {
		//List<Entreprise> l=new ArrayList<>();
		List<User> u= em.createQuery("SELECT x FROM User x",User.class).getResultList();
		System.out.println(u);
		return u;
	}
	
	@Override
	public void AcceptSheet (int idSheet) {
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		Sheet s = em.find(Sheet.class, idSheet);
		Query q = em.createNativeQuery("SELECT * FROM Category where sheet_id_sheet=:idsheet");
		List<User> u= em.createQuery("SELECT x FROM User x",User.class).getResultList();
		
		q.setParameter("idsheet", idSheet);
		List<Object[]> categories = q.getResultList();
		List<String> cname = new ArrayList<String>();
		List<Sheet> listsheet = new ArrayList<Sheet>();
		List<Staff> listuser= new ArrayList<Staff>();
		List<Staff> listRandomStaff = new ArrayList<Staff>();
		
		for (Object[] a : categories) {
		    cname.add((String) a[1]);		             		           
		}
		
		for (User user : u){
			if(user instanceof Staff) {
				for(String c:cname){
					if((((Staff) user).getMain_skill().equals(c))&&(DisplayStaffSheets(((Staff) user).getId()).size()<5)){
						listsheet=((Staff) user).getSheet();
						listuser=s.getStaffSheets();
						listsheet.add(s);
						listuser.add((Staff) user);
						System.out.println(listuser.size());	
						Random rand = new Random();
						Staff randomStaff = listuser.get(rand.nextInt(listuser.size()));
						System.out.println(randomStaff.getId()+"id eli 5tarou");
						listRandomStaff.add(randomStaff);
						while(s.getAccepted().equals(false)) {
							System.out.println(listRandomStaff.size()+" Size");
							
							s.setStaffSheets(listRandomStaff);
							((Staff) user).setSheet(listsheet);
							s.setAccepted(true);
						    s.setRefuse_Reason(null);
						    s.setDate(ts);
						    break;
						}
				       break;
					}
				}
				
			}
			if(listRandomStaff.size()==1)
		   break; 
		}
	
	}
	@Override
	public String findCategoryforPdf(int idSheet) {
		Category e = em.find(Category.class,idSheet);
		return e.getName_Category();
	
	}
	
	@Override
	public void AddStaffSheetManually(int idSheet ,int idStaff) {
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		Staff e = em.find(Staff.class,idStaff);
		Sheet s = em.find(Sheet.class, idSheet);
		List<Sheet> listsheet = new ArrayList<Sheet>();
		List<Staff> listuser= new ArrayList<Staff>();
		listsheet=e.getSheet();
		listuser=s.getStaffSheets();
		listsheet.add(s);
		listuser.add(e);
		s.setStaffSheets(listuser);
		e.setSheet(listsheet);
		s.setAccepted(true);
	    s.setDate(ts);	
	}
	
	@Override
	public void DeleteStaffFromSheet(int idSheet ) {
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		Sheet s = em.find(Sheet.class, idSheet);
		List<Sheet> listsheet = new ArrayList<Sheet>();
		s.setStaffSheets(null);
		s.setAccepted(false);
	    s.setRefuse_Reason(null);
	    s.setDate(ts);	
	}
	@Override
	public Staff findStaffbyId(int idStaff) {
		Staff e = em.find(Staff.class,idStaff);
		return e;
	}
	@Override
	public Boolean RefuseSheetbySupervisor (Sheet sheet,int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		if (s.getAccepted().equals(true)){
	    s.setSheetstatus(SheetStatus.REFUSED);
	    email.sendMail(s.getStudent().getEmail(), "Sheet refused", "Your sheet has been refused please modify your sheet");
	    s.setDate(ts);
	    return true;
		}
		return false;
	}
	@Override
	public Boolean AcceptSheetbySupervisor (Sheet sheet,int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		if (s.getAccepted().equals(true)){
			 s.setSheetstatus(SheetStatus.ACCEPTED);
	    s.setDate(ts);
	    email.sendMail(s.getStudent().getEmail(), "Sheet has been Accepted you may start your graduation project", "Sheet has been Accepted you may start your graduation project");
	    return true;
		}
		return false;
	}
	@Override
	public Boolean RefuseSheet (Sheet sheet,int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
		if (s.getRefuse_Reason()==null){
	    s.setRefuse_Reason(sheet.getRefuse_Reason());
	    s.setDate(ts);
	    return true;
		}
		return false;
	}
	
	@Override
	public Boolean AcceptModification(int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		if (s.getSheetstatus().equals(SheetStatus.REFUSED)){
	    s.setRequest_Modification(false);
	    s.setSheetstatus(SheetStatus.ACCEPTED);
	    s.setFeatures(s.getFeaturesRequest());
	    s.setIssue(s.getIssueRequest());
	    email.sendMail(s.getStudent().getEmail(), "Sheet modification has been accepted", "Your modification has been acceped and applyed");
	    return true;
		}
		return false;
	    
	}
	@Override
	public Boolean RefuseModification(int idSheet , Sheet sheet , String ref) {
		Sheet s = em.find(Sheet.class, idSheet);
		if (s.getSheetstatus().equals(SheetStatus.REFUSED)){
			s.setRequest_Modification(false);
	    s.setRefuseModificationReason(ref);
	    return true;
		}
		return false;
	    
	}
	
	@Override
	public Boolean AcceptCancelRequest(int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		if(s.getCanelProject().equals(true)) {
			s.setAcceptAnnulation("You have been accepted to cancel your project");
			s.setRefusAnnulation("");
			return true ;
		}
		    return false;
	}
	@Override
	public Boolean RefusCancelRequest(int idSheet) {
		Sheet s = em.find(Sheet.class, idSheet);
		if(s.getCanelProject().equals(true)&&(s.getAcceptAnnulation()==null)) {
			s.setRefusAnnulation("You have been refused to cancel your project please contact the administration for more information");
			s.setCanelProject(false);
			return true ;
		}
		    return false;
	}
	@Override
    public Set<Sheet> DisplayStaffSheets (int idStaff) {
    	Staff staff = em.find(Staff.class, idStaff);
    	staff.getSheet();
    	Set<Sheet> hSet = new HashSet<Sheet>(staff.getSheet()); 
    	hSet.addAll(staff.getSheet()); 
    	return hSet;	
    }  
	@Override
	public Map<String,Integer>SheetStats() {
		List<Category> categorysBack = new ArrayList<Category>();
		List<Category> categoryReact = new ArrayList<Category>();
		List<Category> categoryVueJs = new ArrayList<Category>();
		List<Category> categoryAngular = new ArrayList<Category>();
		List<Category> categorydotNet = new ArrayList<Category>();
		List<Category> categorysRuby = new ArrayList<Category>();
		List<Category> categorySymfony = new ArrayList<Category>();
		List<Category> categorysSwift = new ArrayList<Category>();
		List<Category> categoryAndroid = new ArrayList<Category>();
		List<Category> categoryIonic = new ArrayList<Category>();
		List<Category> categoryBa = new ArrayList<Category>();
		List<Category> categoryFront = new ArrayList<Category>();
		List<Category> categoryMobile = new ArrayList<Category>();
		List<Category> categoryDevops = new ArrayList<Category>();
		
		List<Sheet> notSignedforastaff= em.createQuery("SELECT x FROM Sheet x where x.Accepted=false ",Sheet.class).getResultList();
		List<Sheet> canceledSheets= em.createQuery("SELECT x FROM Sheet x where x.acceptAnnulation is NOT NULL ",Sheet.class).getResultList();
		List<Sheet> cannotCancelProject= em.createQuery("SELECT x FROM Sheet x where x.refusAnnulation is NOT NULL ",Sheet.class).getResultList();
		List<Sheet> getHelpFromStaf = em.createQuery("SELECT x FROM Sheet x where x.Request_Modification=true",Sheet.class).getResultList();
		List<Category> categorys = em.createQuery("SELECT x FROM Category x",Category.class).getResultList();
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("JAVA EE")){
				categorysBack.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("React")){
				categoryReact.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Angular")){
				categoryAngular.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals(".NET")){
				categorydotNet.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("VueJS")){
				categoryVueJs.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Ruby")){
				categorysRuby.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Symfony")){
				categorySymfony.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Swift")){
				categorysSwift.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Android")){
				categoryAndroid.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Ionic")){
				categoryIonic.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals(".NET")||(cat.getName_Category().equals("Ruby"))||(cat.getName_Category().equals("JAVA EE"))||(cat.getName_Category().equals("Symfony")||(cat.getName_Category().equals("Ionic")))){
				categoryBa.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Angular")||(cat.getName_Category().equals("React"))||(cat.getName_Category().equals("Android"))||(cat.getName_Category().equals("Swift")||(cat.getName_Category().equals("VueJs")))){
				categoryFront.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Swift")||(cat.getName_Category().equals("Android"))||(cat.getName_Category().equals("Ionic"))){
				categoryMobile.add(cat);
			}
		}
		for(Category cat: categorys) {
			System.out.println(cat.getName_Category());
			if(cat.getName_Category().equals("Devops")){
				categoryDevops.add(cat);
			}
		}
		int nbCategoryDevops = categoryDevops.size();
		int nbCategoryMobile = categoryMobile.size();
		int nbCategoryFront = categoryFront.size();
		int nbCategoryB = categoryBa.size();
		int nbCategoryIonic = categoryIonic.size();
		int nbCategorySwift = categorysSwift.size();
		int nbCategoryAndroid = categoryAndroid.size();
		int nbCategoryReact = categoryReact.size();
		int nbCategoryAngular = categoryAngular.size();
		int nbCategoryVueJs = categoryVueJs.size();
		int nbCategorydotnet = categorydotNet.size();
		int nbCategorySymfony = categorySymfony.size();
		int nbCategoryRuby = categorysRuby.size();
		int nbCategoryBack = categorysBack.size();
		int nbNotAccepted = notSignedforastaff.size();
		int nbCannotCancelSheets = cannotCancelProject.size();
		int nbCanceledSheets = canceledSheets.size();
		int nbHelpFromStaff = getHelpFromStaf.size();
		 Map<String, Integer> Statsmap = new HashMap<String, Integer>();
			Statsmap.put("StudentsNotSignedForStaff", nbNotAccepted);
			Statsmap.put("StudentsWhoCanceledTheirProject", nbCanceledSheets);
			Statsmap.put("StudentsWhoCantCancelProject",  nbCannotCancelSheets);
			Statsmap.put("StudentsWhoGotHelpFromStaff",  nbHelpFromStaff);
			Statsmap.put("JAVAEE", nbCategoryBack);
			Statsmap.put("NET", nbCategorydotnet);
			Statsmap.put("Symfony", nbCategorySymfony);
			Statsmap.put("Ruby", nbCategoryRuby);
			Statsmap.put("Devops", nbCategoryDevops);
			Statsmap.put("Angular", nbCategoryAngular);
			Statsmap.put("React", nbCategoryReact);
			Statsmap.put("VueJs", nbCategoryVueJs);
			Statsmap.put("Ionic", nbCategoryIonic);
			Statsmap.put("Android", nbCategoryAndroid);
			Statsmap.put("Swift", nbCategorySwift);
			Statsmap.put("Frontend", nbCategoryFront);
			Statsmap.put("Mobile", nbCategoryMobile);
			Statsmap.put("Backend", nbCategoryB);
		    return Statsmap;	    
	}

	@Override
	public List<Sheet> ListAllSheet() {
		//List<Entreprise> l=new ArrayList<>();
		List<Sheet> s= em.createQuery("SELECT x FROM Sheet x",Sheet.class).getResultList();
		return s;
	}
	@Override
	public List<Sheet> ListArchive() {
	List<Sheet> s = em.createQuery("SELECT x FROM Sheet as x where x.acceptAnnulation is not null",Sheet.class).getResultList();
		return s;
	}
	@Override
	public List<Sheet> ListNotAcceptedSheets() {
	List<Sheet> s = em.createQuery("SELECT x FROM Sheet as x where x.Accepted = false",Sheet.class).getResultList();
		return s;
	}
	@Override
	public Set<Sheet> ListModification(int idStaff) {
		List<Sheet> mod = new ArrayList<>();
		Staff staff = em.find(Staff.class, idStaff);
    	List<Sheet> ListMod = staff.getSheet();
    	for (Sheet s : ListMod){
    		if(s.getRequest_Modification()==true) {
    			mod.add(s);
    			continue;
    		}
    	}
    	Set<Sheet> hSet = new HashSet<Sheet>(mod); 
    	hSet.addAll(mod); 
    	return hSet;	
    	
	}

	}
