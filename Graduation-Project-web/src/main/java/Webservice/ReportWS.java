package Webservice;

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
import entites.ReportPost;
import entites.Student;
import entites.VoteComment;
import services.PostForumService;
import services.ReportService;
import services.VoteService;

@Path("/report")
public class ReportWS {
	@EJB(beanName = "ReportService")
	ReportService rs;
	@EJB(beanName = "PostForumService")
	PostForumService ps;
	
	@POST
	@Secured
	@Produces(MediaType.TEXT_XML)
	public Response addReport(ReportPost v,@QueryParam(value="id")int id) {
		Student s = (Student) AuthenticationRessource.LoggedPerson;
		//Student s = ps.findUserbyid(LoggedPerson.getId());
		//ps.affectrUserToPost(LoggedPerson.getId(), p);
		
		
		
			v.setUsers(s);
			int i = rs.getNbrReportPerPost(id);
			if (i<5) {
		
		rs.ajouterReport(v, id);
		
		
		return Response.status(Status.CREATED).build();}
			else
				rs.deletePostIdfromReport(id);
				ps.deletePostId(id);
			return Response.status(Status.OK).entity("Post got deleted because number of report =5").build();


	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response nbrvote(@PathParam("id")int id) { 
		int nbr =rs.getNbrReportPerPost(id);
		return Response.status(Status.ACCEPTED).entity(nbr).build();
		
	}
}
