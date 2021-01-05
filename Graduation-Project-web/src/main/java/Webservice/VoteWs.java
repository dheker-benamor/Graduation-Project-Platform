package Webservice;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import RestPackage.Secured;
import entites.CommentPost;
import entites.Student;
import entites.VoteComment;
import services.CommentPostService;
import services.VoteService;

@Path("/addvote")
public class VoteWs {
	@EJB(beanName = "VoteService")
	VoteService vs;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(VoteComment v,@QueryParam(value="id")int id) {
		//Student s = (Student) AuthenticationRessource.LoggedPerson;
		//Student s = ps.findUserbyid(LoggedPerson.getId());
		//ps.affectrUserToPost(LoggedPerson.getId(), p);
		
		//int verif =vs.verif(s, id);
		//if(verif==0) {
			//v.setUsers(s);
		vs.ajouterVote(v, id);
		
		return Response.status(Status.CREATED).entity(vs).build();
		//}
		//else
		//	return Response.status(Status.CONFLICT).entity("vous avez déja voté ").build();

	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response nbrvote(@PathParam("id")int id) { 
		int nbr =vs.getNbrVotePerComment(id);
		return Response.status(Status.ACCEPTED).entity(nbr).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response nbrvote2(@QueryParam("id")int id) { 
		List<VoteComment> c  =vs.getNbrVotePerComment2(id);
		return Response.status(Status.ACCEPTED).entity(c).build();
		
	}
}
