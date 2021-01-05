package Webservice;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
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

import entites.Encadreurentreprise;

import iservices.CompanystaffLocal;

@Path("Encadreur")
@RequestScoped
public class CompanystaffWS {
	
	
	@EJB(beanName="CompanyStaffService")
	CompanystaffLocal CL;
	
	
	/*********** ADD COMPANY Staff ************/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addEntreprise(Encadreurentreprise e) {
		CL.AddStaff(e);
		return Response.status(Status.CREATED).entity(e).build();
	}
	
	
	/****** DELETE COMPANY ***********/

	@DELETE
	@Path("{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeleteCompany(@PathParam("idE") int idE) {
		if (CL.DeleteStaff(idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
	
	@GET
	@Path("/GetStaff")
	@Produces(MediaType.APPLICATION_JSON)
	/***** GET ALL Staff *****/
	public Response GetallEntreprise() {
		List<Encadreurentreprise> l = CL.ListStaff();

		if (!(CL.ListStaff().isEmpty()))
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	

}
