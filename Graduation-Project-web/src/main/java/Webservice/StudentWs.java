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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import RestPackage.Secured;
import entites.Category;
import entites.Sheet;
import entites.Staff;
import entites.Student;
import iservices.StudentRemote;

@Path("/Student")
@RequestScoped
public class StudentWs {
	@EJB
	StudentRemote StudentRemote;
	@POST

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addStudent(Student s) {
		StudentRemote.addStudent(s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	@POST
    @Path("/RegisterStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response RegisrationStudent(Student s) {
		StudentRemote.RequestStudetRegistration(s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	
	@GET
	@Path("/GetListRegistrationStudent")
	@Produces(MediaType.APPLICATION_JSON)
	/***** GET ALL COMPANIES *****/
	public Response GetListRegistration() {
		
		 return Response.status(Status.ACCEPTED).entity(StudentRemote.DisplayStudentRequest()).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("AcceptStudent/{idStudent}")
	@JsonIgnoreProperties
	public Response AcceptStudent(@PathParam(value="idStudent") int idStudent) {
	 StudentRemote.AcceptRegestration(idStudent);
	 return   javax.ws.rs.core.Response.ok("The student has been accepted").build();
}
	
	@POST
    @Path("/AddSheet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addSheet(Sheet s) {
		
		return Response.status(Status.CREATED).entity(StudentRemote.addSheet(s)).build();
	}
	@POST
	@Path("RequestModification/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response RequestModification (@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (StudentRemote.RequestModify(s, idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("The sheet has been Modified").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot Modify until u are accpeted").build();

}
	
	@POST
	@Path("ModifySheetbyRequest/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response ModifySheetbyRequest (@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (StudentRemote.ModifySheetWhenAccepted(s, idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("Your request has been sent to staff you may wait for supervisor to accept or refuse").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot Modify until u are accpeted").build();

}
	@POST
	@Path("ModifySheet/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response ModifySheet (@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (StudentRemote.ModifySheet(s, idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("The sheet has been Modified").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot Modify until u are accpeted").build(); 
}
	@POST
	@Path("CancelRequest/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response CancelRequest (@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (StudentRemote.RequestCancelProject(s, idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("Cancel request has been sent").build();
		}
	return   javax.ws.rs.core.Response.ok("You have already sent a request to cancel the project").build(); 
}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetSheetByStudentId")
	public Sheet GetSheetByStudentId(@QueryParam(value = "idStudent")int idStudent){
	return StudentRemote.DisplayMySheet(idStudent);
	}
	@POST
    @Path("AddCategory/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addCategory(@PathParam(value="idSheet") int idSheet ,Category c) {
		StudentRemote.addCategory(c, idSheet);
		return Response.status(Status.CREATED).entity(c).build();
	}	
	@POST
	@Path("ModifyCategory/{idCategory}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response ModifyCategory (@PathParam(value="idCategory") int idCategory,Category c) {
		if (StudentRemote.ModifyCategory(c, idCategory).equals(true)) {
			return   javax.ws.rs.core.Response.ok("The category has been Modified").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot Modify").build(); 
}	
	@DELETE
	@Path("DeleteCategory/{idCategory}")
	public Response DeleteCategory (@PathParam(value="idCategory") int idCategory,Category c) {
		StudentRemote.DeleteCategory(idCategory);
	return   javax.ws.rs.core.Response.ok("Your category has been deleted").build(); 
}	
	@POST
	@Path("GeneratePDF/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response GeneratePDF (@PathParam(value="idSheet") int idSheet,Sheet s) {
		StudentRemote.GetSheetpdf(idSheet);
			return   javax.ws.rs.core.Response.ok("The pdf has been downloaded").build();
	
}
}

