package Webservice;

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
import entites.Options;
import entites.Role;
import entites.Staff;
import iservices.OptionsLocale;
import iservices.OptionsRemote;


@Path("Options")
@RequestScoped
public class OptionsWS
{
	Staff x= (Staff)AuthenticationRessource.LoggedPerson;
	@EJB(beanName="Ooptionservice")
	OptionsLocale cR;
	@Context
	private UriInfo uriInfo; 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOption(Options o )
	{
		if(x.getRole()==Role.DEPARTEMENT_MANAGER)
		{
		cR.addOption(o,x);
		return Response.status(Status.CREATED).entity(o).build();
		}
		else return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
	}
	@GET
	@Path("/getOptions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOptions() {
		if(x.getRole()==Role.DEPARTEMENT_MANAGER)
		{
		List<Options> l = cR.ListOptions();
		
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		}
		else return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
	}
	@GET
	@Path("/getOptionss/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOptionsbydepart(@PathParam("idE") int idE) {
		if(x.getRole()==Role.DEPARTEMENT_MANAGER)
		{
		List<Options> l = cR.ListOptionsDepart(idE);
		
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		}
		else return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
	}
	@DELETE
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOptions(@PathParam("idE") int idE) {
		if(x.getRole()!=Role.DEPARTEMENT_MANAGER)
		{
			return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
		}
		else if (cR.deleteOption(idE))
			return Response.status(Status.OK).entity(idE).build();
		else return Response.status(Status.BAD_REQUEST).build();

	}
	@PUT
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecter(@PathParam("idE") int idE,Departement d) {
		if(x.getRole()!=Role.DEPARTEMENT_MANAGER)
		{
			return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
		}
		else if (cR.affectOption(d, idE))
			return Response.status(Status.OK).entity(idE).build();
		else return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOptions(Options e) {
		if(x.getRole()!=Role.DEPARTEMENT_MANAGER)
		{
			return Response.status(Status.BAD_REQUEST).entity("vous n'êtes pas notre chef de departement").build();
		}
		else if(cR.updateOption(e)) {
			return Response.status(Status.OK).entity(e).build();}
		else return Response.status(Status.BAD_REQUEST).build();
		}
	
}