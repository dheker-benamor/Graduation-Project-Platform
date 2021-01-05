package Webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import entites.Departement;
import entites.Site;
import iservices.DepartementRemote;


@Path("departement")
@RequestScoped
public class DepartementWS
{
	@EJB(beanName="DepartementService")
	DepartementRemote DR;
	@Context
	private UriInfo uriInfo; 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSdepartement(Departement s )
	{
		DR.adddepartement(s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	@GET
	@Path("/getdepartement")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllschools() {
		List<Departement> l = DR.ListDepartements();

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	@GET
	@Path("/getdepartementt/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDepartements(@PathParam("idE") int idE) {
		List<Departement> l = DR.getListDepartementsbySite(idE);

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	@DELETE
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteDepartement(@PathParam("idE") int idE) {
		if (DR.deleteDepartement(idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
	@PUT
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecter(@PathParam("idE") int idE,Site s) {
		if (DR.affectDepartement(s, idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
	@PUT
	@Path("affecter/{idE}/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affectermanager(@PathParam("idE") int idE,@PathParam("ids") int ids) {
		if (DR.affecterchief(idE, ids))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}

	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateCompany(Departement e) {
		if(DR.updateDepartement(e)) {
			return Response.status(Status.OK).entity(e).build();}
		return Response.status(Status.BAD_REQUEST).build();
		}
	
}