package Webservice;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import RestPackage.Secured;
import entites.Encadreurentreprise;
import entites.Project;
import entites.Staff;
import entites.Student;
import entites.User;
import services.ProjectService;



@Path("/project")
@RequestScoped
public class ProjectResource {
    @EJB
    ProjectService projectservice;
    
    @GET
    @Path("/getallprojects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProject() {

    	return Response.ok(projectservice.findAllProjects()).build();
    }
    @GET
    @Path("/getprojectsbystaff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByStaff(@PathParam(value ="id")int id) {

    	return Response.ok(projectservice.findProjectsByStaff(id)).build();
    }
    @GET
    @Path("/getprojects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjects(@PathParam(value ="id")int id) {
    	User user = projectservice.findUserById(id);
    	if (user instanceof Staff)
    	{
    		return Response.ok(projectservice.findProjectsByStaff(id)).build();
    	}
    	else if (user instanceof Encadreurentreprise) {
    		return Response.ok(projectservice.findProjectsBySupervisor(id)).build();
    	}
    
    	
    	return Response.ok().build();
    }
    @GET
    @Path("/getprojectsbysupervisor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsBySupervisor(@PathParam(value ="id")int id) {

    	return Response.ok(projectservice.findProjectsBySupervisor(id)).build();
    }
	@POST
	@Path("/addproject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(Project e) {
		projectservice.AddProject(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	
	@DELETE
	@Path("/deleteproject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTask(@PathParam(value ="id")int id) {
		
		projectservice.DeleteProject(id);
	
	}
	@GET
	@Path("/getproject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProject(@PathParam(value ="id")int id) {
      Project project = projectservice.findProjectById(id);

		return Response.ok(project).build();

	}
	@GET
	@Path("/getprojectbystudent/{student}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectbystudent(@PathParam(value ="student")int student) {
	  
      Project project = projectservice.findProjectByStudent(student);

		return Response.ok(project).build();

	}
	@GET
	@Secured
	@Path("/getpfeproject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPfeProject() {
	  Student s = (Student) AuthenticationRessource.LoggedPerson;
      Project project = projectservice.findProjectByStudent(s.getId());

		return Response.ok(project).build();

	}
    @GET
    @Path("/getlinks/{qry}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlinks(@PathParam(value ="qry")String qry) throws Exception {
        
    	return Response.ok(projectservice.links(qry).toString()).build();
    }

}
