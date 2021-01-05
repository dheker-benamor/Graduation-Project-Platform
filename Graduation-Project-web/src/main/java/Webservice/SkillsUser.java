package Webservice;


import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.json.JsonObject;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



import RestPackage.Secured;
import entites.Entreprise;
import entites.Skills;
import entites.Student;
import entites.user_skills;
import iservices.SkillsLocal;


@Path("skills")
@RequestScoped
public class SkillsUser {
	@EJB(beanName = "SkillsService")
	SkillsLocal sl;
	
	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateCompany(user_skills x) {
		Student s = (Student) AuthenticationRessource.LoggedPerson;
		if(sl.updateskillsstudent(s, x)) {
			return Response.ok(x,MediaType.APPLICATION_JSON).build();
			}
		return Response.status(Status.BAD_REQUEST).build();
		}
	
	

	@GET
	@Path("/top3")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response top3student(@QueryParam(value="idE") int id) {
		List<Object> x= sl.top3student(id);
		
		if (!(sl.top3student(id).isEmpty())) {
			//System.out.println(Response.ok(x, MediaType.APPLICATION_JSON).build());
			return Response.ok(x, MediaType.APPLICATION_JSON).build();
			
		}
		 
		
		
		
		return Response.status(Status.NOT_FOUND).build();
		
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addOffreStudent(user_skills x) {
		try {
		
			Student s = (Student) AuthenticationRessource.LoggedPerson;
			sl.affectStudentskills(s,x);
			return Response.ok(x,MediaType.APPLICATION_JSON).build();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
		
		

	}
	

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getall() {
		List<Skills> x= sl.getall();
		if (!(sl.getall().isEmpty())) {
			return Response.ok(x, MediaType.APPLICATION_JSON).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Path("/alluserskills")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getalluserskills() {
		List<user_skills> x= sl.userskills();
		if (!(sl.userskills().isEmpty())) {
			return Response.ok(x, MediaType.APPLICATION_JSON).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	
	
	
}
