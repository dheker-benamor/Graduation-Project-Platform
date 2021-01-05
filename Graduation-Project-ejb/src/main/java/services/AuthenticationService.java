package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Encadreurentreprise;
import entites.Staff;
import entites.Student;
import entites.User;
import iservices.AuthenticationServiceRemote;
import utilities.CryptPasswordMD5;



@LocalBean
@Stateless
public class AuthenticationService implements AuthenticationServiceRemote{
	@PersistenceContext(unitName = "Graduation-Project-ejb")
	EntityManager em;
	private CryptPasswordMD5 cryptPasswordMD5 = new CryptPasswordMD5();
	public static User LoggedPerson;
	
	
		
//	@Override
//	public User authentificationUser(String email, String password) {
//		User person = new User();
//		try{
//		TypedQuery<User> query = em.createQuery("SELECT p FROM User p where p.email=:email", User.class);
//		query.setParameter("email", email);
//		person = query.getSingleResult();
//		if(person instanceof Staff) {
//			if (person.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))) {
//				LoggedPerson = person;
//				System.out.println(person.getId());
//				
//				return LoggedPerson;
//				
//			} else {  
//				System.out.println(person);
//				return new User();
//			
//			}
//		}else {
//			if (person.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))&&((Student) person).getIsActive().equals(true)) {
//				LoggedPerson = person;
//				System.out.println(person.getId());
//				
//				return LoggedPerson;
//				
//			} else {  
//				System.out.println(person);
//				return new User();
//			
//			}		
//		}
//		
//		}
//
//		catch(NoResultException n) {
//			System.out.println("err");
//		}
//		
//        System.out.println(person.getId());
//		return person;
//	}
    
	@Override
	public User authentificationUser(String email, String password) {
		User person = new User();
		try{
		TypedQuery<User> query = em.createQuery("SELECT p FROM User p where p.email=:email", User.class);
		query.setParameter("email", email);
		person = query.getSingleResult();
		if(person instanceof Staff) {
			if (person.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))) {
				LoggedPerson = person;
				System.out.println(person.getId());
				
				return LoggedPerson;
				
			} else {  
				System.out.println(person);
				return new User();
			
			}
		}
		else if(person instanceof Encadreurentreprise) {
			if (person.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))) {
				LoggedPerson = person;
				System.out.println(person.getId());
				
				return LoggedPerson;
				
			} else {  
				System.out.println(person);
				return new User();
			
			}
		}
		else {
			if (person.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))) {
				LoggedPerson = person;
				System.out.println(person.getId());
				
				return LoggedPerson;
				
			} else {  
				System.out.println(person);
				return new User();
			
			}		
		}
		
		}

		catch(NoResultException n) {
			System.out.println("err");
		}
		
        System.out.println(person.getId());
		return person;
	}

	


	
	
}
