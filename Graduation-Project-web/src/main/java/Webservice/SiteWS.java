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

import entites.School;
import entites.Site;
import iservices.SiteRemote;

@Path("site")
@RequestScoped
public class SiteWS
{
	@EJB(beanName="SiteService")
	SiteRemote SR;
	@Context
	private UriInfo uriInfo; 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSite(Site s )
	{
		SR.addsite(s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	@GET
	@Path("/getsite")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllsites() {
		List<Site> l = SR.ListSite();

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	@GET
	@Path("/getsitee/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllsitesbyschool(@PathParam("idE") int idE) {
		List<Site> l = SR.getSitesbySchool(idE);

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		
	}
	@DELETE
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeleteSite(@PathParam("idE") int idE) {
		if (SR.deleteSite(idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
//	@PUT
//	@Path("{cl}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response UpdateEquipe(@PathParam(value="cl" )int cin,School l) {
//		int index= getIndexByCin(cin);
//		if (index!=-1) {
//			List<School> e = SR.ListSchool();
//			e.set(index, l);
//			return  Response.ok(getListeEquipeByClasement(cin), MediaType.APPLICATION_JSON).build();}
//			else 
//				return Response.status(Response.Status.NOT_FOUND).build();
//					
//					
//	}
	
	@PUT
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecter(@PathParam("idE") int idE,School s) {
		if (SR.affectSite(s, idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateSite(Site e) {
		if(SR.updateSite(e)) {
			return Response.status(Status.OK).entity(e).build();}
		return Response.status(Status.BAD_REQUEST).build();
		}
	
}