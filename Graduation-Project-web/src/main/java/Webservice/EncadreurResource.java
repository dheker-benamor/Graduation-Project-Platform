package Webservice;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entites.Activity;
import entites.Encadreurentreprise;
import services.ActivityService;
import services.CompanyStaffService;



@Path("/encadreur")
@RequestScoped
public class EncadreurResource {
    @EJB
    CompanyStaffService service;
    

 

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(Encadreurentreprise e) {
		service.AddStaff(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	



}
