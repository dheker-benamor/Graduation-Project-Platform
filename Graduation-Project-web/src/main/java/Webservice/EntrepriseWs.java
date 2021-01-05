package Webservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import entites.Entreprise;
import entites.Message;
import entites.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import iservices.EntrepriseRemote;


@Path("Entreprise")
@RequestScoped
public class EntrepriseWs {

	@EJB(beanName = "EntrepriseService")
	EntrepriseRemote ER;
	public static Entreprise LoggedPerson;
	
	@Context
	private UriInfo uriInfo; 

	/*********** ADD COMPANY ************/
	@POST

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addEntreprise(Entreprise e) {
		ER.addentreprise(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	/********************Authentificate***********************/
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@QueryParam("identifiant") String identifiant, @QueryParam("password") String password) {
		     JSONObject  obj = new JSONObject();
		     String type = "entreprise";
		     String autorise="autorise"; 
			Entreprise entreprise = authenticate(identifiant, password);
			if(entreprise.getId()!=0) {
				String token = issueToken(identifiant);
				obj.put("token", token);
				obj.put("autorise",entreprise.getAutorise());
				obj.put("entreprise", entreprise);
				System.out.println(obj);
				return Response.ok(obj).build();
				
			}else
			 {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
	}
	/*****************MethodtoUse********************/
	private Entreprise authenticate(String identifiant, String password) {
		Entreprise entreprise = ER.authentification(identifiant, password);
		if (entreprise != null) {
			LoggedPerson = entreprise;
		}
		return entreprise;
	}

	@GET
	@Path("/GetEntreprise")
	@Produces(MediaType.APPLICATION_JSON)
	/***** GET ALL COMPANIES *****/
	public Response GetallEntreprise() {
		List<Entreprise> l = ER.Listentreprises();

		if (!(ER.Listentreprises().isEmpty()))
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}

	/****** DELETE COMPANY ***********/

	@DELETE
	@Path("{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeleteCompany(@PathParam("idE") int idE) {
		if (ER.deleteEntreprise(idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
	/****************UPDATE COMPANY**********************/
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateCompany(Entreprise e) {
		if(ER.updateEntreprise(e)) {
			return Response.status(Status.OK).entity(e).build();}
		return Response.status(Status.BAD_REQUEST).build();
		}
	/******************Approve Company*********************/
	@PUT
	@Path("/approved/{idE}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void approve(@PathParam("idE") int idE) {
		try {
			ER.approveCompany(idE) ;
		} catch (MessagingException e) {
			
			e.printStackTrace();
			System.out.println("error sending message");
		}
			
		
	}

	/*******************COMPANY APPROVED************************/
	@GET
	@Path("/GetEntrepriseA")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response GetallEntrepriseA() {
		List<Entreprise> l = ER.approvedComapnies();

		if (!(ER.approvedComapnies().isEmpty()))
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	private String issueToken(String identifiant) {
		
		 
		  String keyString = "simplekey";
		  SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		  System.out.println("the key is : " + key.hashCode()); 
		 
		  System.out.println("uriInfo.getAbsolutePath().toString() : " + uriInfo.getAbsolutePath().toString());
		  System.out.println("Expiration date: " + toDate(LocalDateTime.now().plusMinutes(15L))); 
		 
		  String jwtToken = Jwts.builder().setSubject(identifiant).setIssuer(uriInfo.getAbsolutePath().toString()).setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15L))).signWith(SignatureAlgorithm.HS512, key).compact(); 
		 
		  System.out.println("the returned token is : " + jwtToken);  
		  return jwtToken;  }

	private Date toDate(LocalDateTime localDateTime) { 
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
		}
	public Entreprise getLoggedPerson() {
		if (LoggedPerson == null) {
			return null;
		} else {
			return LoggedPerson;
		}
		}
	
	@POST
	@Path("/sendMsg")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessageToClient(Message message) {
		try {
			ER.sendMessageToClient(message);
		} catch (MessagingException e) {
			System.out.println("error sending message");
		}
	}
	
		
			
	}





