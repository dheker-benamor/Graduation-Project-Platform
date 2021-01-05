package Webservice;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import entites.User;
import services.PostForumService;

@Path("post")
public class PostServices {
	@EJB(beanName = "PostForumService")
	PostForumService ps;
	public  static User LoggedPerson;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPost(PostForum p) {
		//Student s = (Student) AuthenticationRessource.LoggedPerson;
		//Student s = ps.findUserbyid(LoggedPerson.getId());
		//ps.affectrUserToPost(LoggedPerson.getId(), p);
		//p.setUsers(s);
		
		ps.ajouterPost(p);
		return Response.status(Status.CREATED).entity(ps).build();

	}

	@GET
    @Path("/allposts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallPosts() {
		List<PostForum> p = ps.findAllPosts();

		return Response.status(Status.ACCEPTED).entity(p).build();

	}

	@GET
	@Path("{ref}")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response search(@PathParam(value="ref") int ref) {
		
	 PostForum p = ps.findPostById(ref);
	 return Response.status(Status.ACCEPTED).entity(p).build();

	}
	@DELETE
	@Path("{idP}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response delete(@PathParam("idP") int id) {
		PostForum p = ps.getbyid(id);
		
		for(int i =0 ; i<p.getC().size();i++)
			p.getC().get(i).getV().clear();
		p.getC().clear();
		
		 ps.deletePostId(id); 
			return Response.status(Status.OK).entity(ps).build();
		
	

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response delete(@QueryParam(value="lookingfor") String lookingfor) {
		 List<PostForum> p = ps.RechercherPost(lookingfor);
		 if(p.size()==0)
			return Response.status(Status.NO_CONTENT).entity("aucun resultat correspondant a votre recherche").build();
		 else 
			 return Response.status(Status.OK).entity(p).build();
	

	}
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response setetat(@QueryParam(value="idposte") int id) { 
		//Student s = (Student) AuthenticationRessource.LoggedPerson;
		PostForum p = new PostForum();
		p = ps.findPostById(id);
		//if(s == null)
		//	return Response.status(Status.NOT_FOUND).entity("vous n'etes pas conecté").build();
		//else if(p == null)
	 	 //    return Response.status(Status.NOT_FOUND).entity("vérifiez l'id du post").build();
		
		//else if(p.getUsers().getId()==s.getId()) { 
			ps.setEtat(p);
			return Response.status(Status.OK).entity(ps).build();
		//}
		//else 
			//return Response.status(Status.NOT_ACCEPTABLE).entity("vous n'etes pas l'auteur de cet post").build();
	}
	
	@GET
	@Path("/{categorie}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbycategorie(@PathParam(value="categorie") String categorie) {
		List<PostForum> p = ps.getByCategorie(categorie);
		 if(p==null)
			return Response.status(Status.NO_CONTENT).entity("aucun resultat correspondant a votre recherche").build();
		 else 
			 return Response.status(Status.OK).entity(p).build();
	

	}
	@PUT
	@Path("UpdatePost/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePost(PostForum e,@PathParam(value="id") int id) { 

		
		
		ps.updatePost(e,id);
		return Response.status(Status.ACCEPTED).entity("Post mis a jour").build();
		}
}
