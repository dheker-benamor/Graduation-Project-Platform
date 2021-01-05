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
import services.ActivityService;



@Path("/activity")
@RequestScoped
public class ActivityResource {
    @EJB
    ActivityService activityservice;
    

    @GET
    @Path("/getactivities/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByStaff(@PathParam(value ="id")int id) {

    	return Response.ok(activityservice.findAllActivities(id)).build();
    }

	@POST
	@Path("/addactivity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(Activity e) {
		activityservice.AjouterActivity(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	

	@GET
	@Path("/getactivity/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProject(@PathParam(value ="id")int id) {
      Activity act = activityservice.findActivityById(id);

		return Response.ok(act).build();

	}


}
