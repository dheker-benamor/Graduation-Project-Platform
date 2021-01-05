package Webservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.persistence.DiscriminatorValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

import entites.Encadreurentreprise;
import entites.Staff;
import entites.Student;
import entites.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import iservices.AuthenticationServiceRemote;

@Path("authenticate")
public class AuthenticationRessource {
	@EJB
	AuthenticationServiceRemote local ;
	public static User LoggedPerson;	

	@Context
	private UriInfo uriInfo; 

@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@QueryParam("email") String email, @QueryParam("password") String password) {
		    JSONObject obj = new JSONObject();
		    String type = "user";
			User user = authenticate(email, password);
			if(user.getId()!=0) {
				String token = issueToken(email);
				if(user instanceof Staff) {
				type ="staff";
				//obj.put("user", (Staff)user);
				}
				else if(user instanceof Encadreurentreprise) {
					type ="encadreur";
				//	obj.put("user", (Encadreurentreprise)user);
				}
				else if(user instanceof Student) {
					type ="student";
				//	obj.put("user", (Student)user);
				}else {				obj.put("role", user.getClass().getAnnotation(DiscriminatorValue.class).value());
}
				obj.put("token", token);
				obj.put("type", type);
				obj.put("user", user);
				obj.put("role", user.getClass().getAnnotation(DiscriminatorValue.class).value());
				
				return Response.ok(obj).build();
			}else
			 {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
	}

	private User authenticate(String email, String password) {
		User user = local.authentificationUser(email, password);
		if (user != null) {
			LoggedPerson = user;
		}
		return user;
	}

	private String issueToken(String email) {
		
		 
		  String keyString = "simplekey";
		  SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		  System.out.println("the key is : " + key.hashCode()); 
		 
		  System.out.println("uriInfo.getAbsolutePath().toString() : " + uriInfo.getAbsolutePath().toString());
		  System.out.println("Expiration date: " + toDate(LocalDateTime.now().plusMinutes(15L))); 
		 
		  String jwtToken = Jwts.builder().setSubject(email).setIssuer(uriInfo.getAbsolutePath().toString()).setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15L))).signWith(SignatureAlgorithm.HS512, key).compact(); 
		 
		  System.out.println("the returned token is : " + jwtToken);  
		  return jwtToken;  }

	private Date toDate(LocalDateTime localDateTime) { 
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
		}
	public User getLoggedPerson() {
		if (LoggedPerson == null) {
			return null;
		} else {
			return LoggedPerson;
		}}
	
}
