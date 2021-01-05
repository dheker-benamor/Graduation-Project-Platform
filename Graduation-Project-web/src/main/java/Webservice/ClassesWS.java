package Webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import entites.Classes;
import entites.Options;
import entites.Role;
import entites.Staff;
import entites.Student;
import iservices.ClassesLocale;
import iservices.ClassesRemote;


@Path("classe")
@RequestScoped
public class ClassesWS
{
	@EJB(beanName="Cclasseservice")
	ClassesLocale CR;
	@Context
	private UriInfo uriInfo; 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClasse(Classes s )
	{
		
		Staff x= (Staff)AuthenticationRessource.LoggedPerson;
		CR.addclasse(s,x);
		return Response.status(Status.CREATED).entity(s).build();
		
		
	}
	@GET
	@Path("/getclasses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllClasses() {
		List<Classes> l = CR.ListClasses();
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	@GET
	@Path("/getclassess/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllClasses(@PathParam("idE") int idE) {
		List<Classes> l = CR.ListClassesOption(idE);
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	
	@DELETE
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClasses(@PathParam("idE") int idE) {
		 if (CR.deleteClasse(idE))
			return Response.status(Status.OK).entity(idE).build();
		else return Response.status(Status.BAD_REQUEST).build();

	}
	@PUT
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecter(@PathParam("idE") int idE,Options s) {
		 if (CR.affecterClasse(s, idE))
			return Response.status(Status.OK).entity(idE).build();
		else return Response.status(Status.BAD_REQUEST).build();

	}
	@PUT
	@Path("/{s}/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecterStudent(@PathParam("idE") int idE,@PathParam("s") int s) {
	
		 if (CR.affecterEtudiant(s, idE))
			return Response.status(Status.OK).entity(idE).build();
		else return Response.status(Status.BAD_REQUEST).build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateClasse(Classes e) {
		
		 if(CR.updateClasse(e)) {
			return Response.status(Status.OK).entity(e).build();}
		else return Response.status(Status.BAD_REQUEST).build();
		}
	
}