package Webservice;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



import entites.TaskComment;
import services.TaskCommentService;


@Path("/taskcomment")
@RequestScoped
public class TaskCommentResource {
    @EJB
    TaskCommentService commentservice;
    
    @GET
    @Path("/getallcomments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTask(@PathParam(value ="id")int id) {

    	return Response.ok(commentservice.findAllTaskComments(id)).build();
    }
    @GET
    @Path("/getcommentsbytask/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@PathParam(value ="id")int id) {

    	return Response.ok(commentservice.findCommentsByTask(id)).build();
    }
	@POST
	@Path("/addcomment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(TaskComment e) {
		commentservice.AjouterTaskComment(e);
		return Response.status(Status.CREATED).build();
	}
	
	
	@DELETE
	@Path("/deletecomment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@PathParam(value ="id")int id) {
		
		commentservice.DeleteTaskComment(id);
	return Response.ok().build();
	}
	@GET
	@Path("/getcomment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam(value ="id")int id) {
	
		return Response.ok(commentservice.findTaskCommentById(id)).build();

	}
	@PUT
	@Path("/updatecomment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(TaskComment taskcomment) {
		commentservice.UpdateTaskComment(taskcomment);
		return Response.ok().build();

	}


}
