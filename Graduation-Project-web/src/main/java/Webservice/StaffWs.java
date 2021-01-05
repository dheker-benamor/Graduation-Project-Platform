package Webservice;



import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
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

import org.json.simple.JSONObject;

import RestPackage.Secured;
import entites.Sheet;
import entites.Staff;

import iservices.StaffRemote;

@Path("/Staff")
@RequestScoped
public class StaffWs {
	
	@EJB
	StaffRemote SR;

	/*********** ADD STAFF ************/
	@POST

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addStaff(Staff e) {
		SR.addStaff(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	@POST
	@Path("/addSupervisor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addSupervisor(Staff e) {
		SR.addSupervisor(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	@POST
	@Path("/addDepartmentManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addDepartmentManager(Staff e) {
		SR.adddepartmentManager(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	@GET
	@Path("/GetListStaff")
	@Produces(MediaType.APPLICATION_JSON)
	/***** GET ALL COMPANIES *****/
	public Response GetallUser() {
		 return Response.status(Status.ACCEPTED).entity(SR.ListeStaff()).build();
	}
	
	@POST
	@Path("RefuseSheet/{idSheet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response RefuseSheet (@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (SR.RefuseSheet(s, idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("The sheet has been refused").build();
		}
	return   javax.ws.rs.core.Response.ok("The sheet is already refused").build();

}
	@POST
	@Path("AcceptSheet/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AcceptSheet(@PathParam(value="idSheet") int idSheet) {
		SR.AcceptSheet(idSheet);
			return   Response.status(Status.ACCEPTED).build();

}
	@PUT
	@Path("AcceptModification/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AcceptSheetModification(@PathParam(value="idSheet") int idSheet) {
		if (SR.AcceptModification(idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok().build();
		}
	return   javax.ws.rs.core.Response.status(Status.FORBIDDEN).build();

}
	@GET
	@Path("RefuseModification/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response RefuseSheetModification(@PathParam(value="idSheet") int idSheet, @QueryParam(value="ref") String ref,Sheet s) {
		if (SR.RefuseModification(idSheet,s,ref).equals(true))
		{
			return   javax.ws.rs.core.Response.ok().build();
		}
	return   javax.ws.rs.core.Response.status(Status.FORBIDDEN).build();

}
	@PUT
	@Path("AcceptSheetBySupervisor/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AcceptSheetBySupervisor(@PathParam(value="idSheet") int idSheet,Sheet s) {
		if (SR.AcceptSheetbySupervisor(s, idSheet)) {
			return   javax.ws.rs.core.Response.ok().build();
		}
	return   javax.ws.rs.core.Response.status(Status.FORBIDDEN).build();
}
	@PUT
	@Path("RefuseSheetBySupervisor/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response RefuseSheetBySupervisor(@PathParam(value="idSheet") int idSheet ,Sheet s) {
		if (SR.RefuseSheetbySupervisor(s, idSheet)) {
			return   javax.ws.rs.core.Response.ok().build();
		}
	return   javax.ws.rs.core.Response.status(Status.FORBIDDEN).build();
}
	
	@POST
	@Path("AcceptCancelRequest/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AcceptCancelRequest(@PathParam(value="idSheet") int idSheet) {
		if (SR.AcceptCancelRequest(idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("Your project has been canceled ").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot cancel your project unless u send a request ").build();
}	
	@POST
	@Path("AffectStaffManually/{idSheet}/{idStaff}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AffectStaffManually(@PathParam(value="idSheet") int idSheet,@PathParam(value="idStaff") int idStaff) {
		SR.AddStaffSheetManually(idSheet, idStaff);
			return   Response.ok("The staff has been affected manually").build();
	
}	
	@POST
	@Path("RefusCancelRequest/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response RefuseCancelRequest(@PathParam(value="idSheet") int idSheet) {
		if (SR.RefusCancelRequest(idSheet).equals(true)) {
			return   javax.ws.rs.core.Response.ok("Your request has been refused contact the administarion").build();
		}
	return   javax.ws.rs.core.Response.ok("You cannot cancel your project unless u send a request ").build();
}	

	@GET
	@Path("/DisplayAllSheet")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response DisplayAllSheet() {
		 return Response.status(Status.ACCEPTED).entity(SR.ListAllSheet()).build();
}
	@GET
	@Path("/DisplayArchive")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response DisplayArchive() {
		 return Response.status(Status.ACCEPTED).entity(SR.ListArchive()).build();
}
	@GET
	@Path("/DisplayNotAcceptedSheets")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response DisplayAcceptedSheets() {
		 return Response.status(Status.ACCEPTED).entity(SR.ListNotAcceptedSheets()).build();
}
	@GET
	@Path("/DisplayModificationRequests/{idStaff}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DisplayModificationRequests(@PathParam(value="idStaff") int idStaff) {
		 return Response.status(Status.ACCEPTED).entity(SR.ListModification(idStaff)).build();
}
	@GET
	@Path("DisplayStaffSheets/{idStaff}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response DisplayStaffSheets(@PathParam("idStaff")int idStaff) {
		 return Response.status(Status.ACCEPTED).entity(SR.DisplayStaffSheets(idStaff)).build();
}
	@GET
	@Path("DisplayStaffbyId/{idStaff}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response DisplayStaffbyId(@PathParam("idStaff")int idStaff) {
		 return Response.status(Status.ACCEPTED).entity(SR.findStaffbyId(idStaff)).build();
	}
	
	@GET
	@Path("DisplaySheetStats")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DisplaySheetStats() {
		 return Response.status(Status.ACCEPTED).entity(SR.SheetStats()).build();
	}
	
	@POST
	@Path("DeleteStaffFromSheet/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AcceptSheetBySupervisor(@PathParam(value="idSheet") int idSheet) {
		SR.DeleteStaffFromSheet(idSheet);
			return   javax.ws.rs.core.Response.ok().build();

}
}

