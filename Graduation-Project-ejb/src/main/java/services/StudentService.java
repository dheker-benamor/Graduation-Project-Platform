package services;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import PdfSheet.SheetPdf;
import entites.Category;
import entites.Sheet;
import entites.SheetStatus;
import entites.Staff;
import entites.Student;
import entites.User;
import iservices.StudentLocal;
import iservices.StudentRemote;
import utilities.CryptPasswordMD5;

@Stateless
@LocalBean
public class StudentService implements StudentRemote,StudentLocal  {
	@PersistenceContext
	EntityManager em;
	AuthenticationService as;
	
	@Override
	public int addStudent(Student s) {
      em.persist(s);
      s.setPassword(CryptPasswordMD5.cryptWithMD5(s.getPassword()));
      s.setIsActive(true);
      return s.getMoney_owned();
	}
	@Override
	public int RequestStudetRegistration(Student s) {
		  em.persist(s);
		  s.setPassword(CryptPasswordMD5.cryptWithMD5(s.getPassword()));
	      s.setIsActive(false);
	      return s.getMoney_owned();
	}
	
	@Override
	public List<Student> DisplayStudentRequest(){
		TypedQuery<Student> query = em.createQuery("SELECT u FROM Student u where u.isActive='false'",Student.class);
		return query.getResultList();	
		
	}
	
	@Override
	public Student AcceptRegestration(int idStudent) {
		Student s = em.find(Student.class, idStudent);
		if (s.getIsActive().equals(false)){
	    s.setIsActive(true);
		}
	    return s;
	}
	@Override
		public Student findStudentbyId(int idStudent) {
			return em.find(Student.class, idStudent);
			
		} 
	@Override 
	    public int addSheet(Sheet sheet) {
		sheet.setAccepted(false);
		sheet.setRequest_Modification(false);
		sheet.setCanelProject(false);
		sheet.setStudent((Student)AuthenticationService.LoggedPerson);
		System.out.println((Student)AuthenticationService.LoggedPerson);
		sheet.setSheetstatus(SheetStatus.DEFAULT);
		long timeNow = Calendar.getInstance().getTimeInMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
        
		em.persist(sheet);	
		
		return sheet.getId_sheet();
}
	@Override 
    public int addCategory(Category category ,int idSheet) {
    Sheet s = em.find(Sheet.class,idSheet);
	category.setSheet(s);
	em.persist(category);
	return category.getId();
}
	
	   public Boolean RequestModify (Sheet sheet , int idSheet) {
		   Sheet s = em.find(Sheet.class,idSheet);
		  if ((s.getRefuse_Reason()!=null)&&(s.getAccepted().equals(false))) {
			  s.setRequest_Modification(true);
			  return true;
		  }
		      return false;
		  
	   }
	@Override
	    public Boolean ModifySheetWhenAccepted (Sheet sheet,int idSheet) {
	    	Sheet s = em.find(Sheet.class, idSheet);
			long timeNow = Calendar.getInstance().getTimeInMillis();
			java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
			if ((s.getAccepted().equals(true))&&(s.getSheetstatus().equals(SheetStatus.REFUSED))){
		    s.setFeaturesRequest(sheet.getFeaturesRequest());
		    s.setIssueRequest(sheet.getIssueRequest());
		    s.setRequest_Modification(true);
		    s.setDate(ts);
		    return true;
			} 
			return false;
	    }
	    @Override
	    public Boolean ModifySheet (Sheet sheet,int idSheet) {
	    	Sheet s = em.find(Sheet.class, idSheet);
			long timeNow = Calendar.getInstance().getTimeInMillis();
			java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
	        if (s.getAccepted().equals(true)) {
			s.setDate(ts);
	        s.setDescription(sheet.getDescription());
	        s.setKey_words(sheet.getKey_words());
	        s.setTitle(sheet.getTitle());
	        return true;
	        }
	        return false;
	    }
	    @Override
	    public Boolean RequestCancelProject (Sheet sheet,int idSheet) {
	    	Sheet s = em.find(Sheet.class, idSheet);
			long timeNow = Calendar.getInstance().getTimeInMillis();
			java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
			if (s.getCanelProject().equals(false)) {
				s.setDate(ts);
		        s.setCanelProject(true);
		        return true;
			}
			return false;
	    }    
	    @Override
	    public Sheet DisplayMySheet (int idStudent) {
	    	Student st = em.find(Student.class, idStudent);
	    	TypedQuery<Sheet> query = em.createQuery("select  x from Sheet x where student_id=:idStudent", Sheet.class);
			query.setParameter("idStudent",st.getSheet().getStudent().getId());

			return query.getSingleResult();
			
	    }  
	    @Override 
	    public Boolean ModifyCategory(Category category ,int idCategory) {
	    Category s = em.find(Category.class,idCategory);
	    if (s.getName_Category()!=null) {
		s.setName_Category(category.getName_Category());
		return true;
	    }
		return false;
	}
	    @Override
	    
	    public void DeleteCategory(int idCategory) {
	    	Category s = em.find(Category.class,idCategory);
		    em.remove(s);
	    }
	    @Override
	   	public boolean GetSheetpdf(int idSheet) {
	   		
	    	Sheet s = em.find(Sheet.class, idSheet);
	   		try {	
	   			String filename = new SheetPdf().generateSheetPdf(s);
	   			return true;
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   			return false;
	   		}
	   		

	   		
	   	}
		@Override
		public String getEmailStudentById(int id) {
			// TODO Auto-generated method stub
			Student student=em.find(Student.class, id);
			return student.getEmail();
		}   
}

