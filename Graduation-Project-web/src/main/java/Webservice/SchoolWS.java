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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import entites.Entreprise;
import entites.School;
import entites.Staff;
import iservices.SchoolRemote;

@Path("school")
@RequestScoped
public class SchoolWS
{
	
	@EJB(beanName="SchoolService")
	SchoolRemote SR;
	@Context
	private UriInfo uriInfo; 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSchool(School s)
	{		
		SR.addSchool(s);
		return Response.status(Status.CREATED).entity(s).build();
	}
	@GET
	@Path("/getschool")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllschools() {
		List<School> l = SR.ListSchool();

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
			}
	@GET
	@Path("/getschoolbyname/{schoolname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllschoolsbyname(@PathParam("schoolname") String schoolname) {
		List<School> l = SR.findbyname(schoolname);

			return Response.ok(l, MediaType.APPLICATION_JSON).build();
			}
	@DELETE
	@Path("/{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeleteSchool(@PathParam("idE") int idE) {
		if (SR.deleteSchool(idE))
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response UpdateCompany(School e) {
		if(SR.updateSchool(e)) {
			return Response.status(Status.OK).entity(e).build();}
		return Response.status(Status.BAD_REQUEST).build();
		}
	public List<School> getListeEquipeByClasement(int reference) {
		List<School> l = SR.ListSchool();
		List<School> liste=new ArrayList<School>();
		for(School r: l){
			if(r.getId()==reference)
				liste.add(r);
		}
		return liste;
	}


	public int getIndexByCin(int cin) {
		List<School> l = SR.ListSchool();
		for(School emp: l) {
			if (emp.getId()==cin)
				return l.indexOf(emp);
		}
		return -1;
	}
}