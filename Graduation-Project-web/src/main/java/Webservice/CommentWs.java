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
import entites.PostForum;
import entites.Student;
import entites.VoteComment;
import services.CommentPostService;
import services.PostForumService;

@Path("comment")
public class CommentWs {
	@EJB(beanName = "CommentPostService")
	CommentPostService cpS;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(CommentPost cp,@QueryParam(value="id")int id) {
		//Student s = (Student) AuthenticationRessource.LoggedPerson;
		//Student s = ps.findUserbyid(LoggedPerson.getId());
		//ps.affectrUserToPost(LoggedPerson.getId(), p);
		//cp.setS(s);
		cpS.ajouterComment(cp, id);
		
		return Response.status(Status.CREATED).entity(cpS).build();

	}
	
@GET
@Path("{idp}")
@Produces(MediaType.APPLICATION_JSON)
public Response getallComments(@PathParam(value="idp") int idp) {
	List<CommentPost> cp = cpS.getAllCommentsByPost(idp);

	return Response.status(Status.ACCEPTED).entity(cp).build();

}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallComments2(@QueryParam(value="post_id")int post_id) {
		List<String> cp = cpS.getAll(post_id);
		return Response.status(Status.ACCEPTED).entity(cp).build();

}
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getall() {
		List<CommentPost> cp = cpS.getAllComments();
		return Response.status(Status.ACCEPTED).entity(cp).build();

}
	
	@GET
	@Path("/notifs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getnotifs() {
		//Student s = (Student) AuthenticationRessource.LoggedPerson;
	
		//int id = s.getId();
		Student s = new Student();
		s.setId(1);
		List<String> cp = cpS.Notifs(s.getId());
		return Response.status(Status.ACCEPTED).entity(cp).build();

}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallVotesPerComments(@QueryParam(value="cmt_id")int cmt_id) {
		List<VoteComment> cp= cpS.getAllVotePerComment(cmt_id);
		return Response.status(Status.ACCEPTED).entity(cp).build();

}
	
	

}

