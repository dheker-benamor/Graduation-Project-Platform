package Webservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import RestPackage.Secured;
import entites.Category;
import entites.Entreprise;
import entites.OffreEntreprise;
import entites.Student;
import entites.User_offreentreprise;
import iservices.OffreRemote;
import services.EntrepriseService;

@SuppressWarnings("unused")
@Path("Offre")
@RequestScoped
public class OffreWs {

	@EJB(beanName = "OffreService")
	OffreRemote OR;

	/***************** ADD OFFFRE ********************/
	@POST
	@Secured
	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addOffre(OffreEntreprise e,@QueryParam(value="idE") int idE, @QueryParam(value="idc") int idC ) {
		try {
			Entreprise x = (Entreprise) EntrepriseWs.LoggedPerson;
			idE=x.getId();
		
			OR.addOffre(e,idE,idC);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return Response.status(Status.CREATED).entity(e).build();

	}

	@GET
	@Path("/Getoffre")
	@Produces(MediaType.APPLICATION_JSON)
	/***** GET ALL Offre *****/
	public Response GetallOffre() {
		List<OffreEntreprise> l = OR.ListOffre();

		if (!(OR.ListOffre().isEmpty()))
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
	}

	/****************** DELETE OFFRE *********************/
	@DELETE
	@Path("{idE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeleteOffre(@PathParam("idE") int idE) {
		if (OR.deleteOffre(idE))
			return Response.status(Status.OK).entity(idE).build();
		return Response.status(Status.BAD_REQUEST).build();

		/******************* Update offre ********************/
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void UpdateCompany(OffreEntreprise e) {
		OR.updateOffre(e);

	}

	@POST
	@Secured
	@Path("/Offrestudent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addOffreStudent(@QueryParam(value = "ido") int ido) {
		try {

			Student s = (Student) AuthenticationRessource.LoggedPerson;
			OR.affectStudentoffre(s, ido);
			return Response.status(Status.ACCEPTED).build();

		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
		

	}

	// SELECT user.nom,offreentreprise.annonce FROM
	// user,offreentreprise,user_offreentreprise WHERE
	// user.id=user_offreentreprise.student_id and
	// user_offreentreprise.offreentreprise_id=offreentreprise.id 
	@GET
	@Path("/MyOffres/{ido}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response myoffres(@PathParam("ido") int ido) {
		
		List<User_offreentreprise> x = OR.Listoffrestudent(ido);
		if (!(OR.Listoffrestudent(ido).isEmpty())) {
			
		   
			return Response.ok(x, MediaType.APPLICATION_JSON).build();}
			
		return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Secured
	@Path("/MyOffres")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response OffreC() {
		Entreprise s = (Entreprise) EntrepriseService.LoggedPerson;
		List<OffreEntreprise> x = OR.Coffre( s);
		if (!(OR.Coffre(s).isEmpty())) {
			
		   
			return Response.ok(x, MediaType.APPLICATION_JSON).build();}
			
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/StatO")
	@Produces(MediaType.APPLICATION_JSON)
	public Response CountOffre(@QueryParam(value="ido") int ido) {
		List<Object[]> l =OR.CountOffres(ido);
		return Response.ok("count"+l,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/StatD")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Countperday(@QueryParam("idE") int idE,@QueryParam("date") String date)  {
		
		List<Object[]> l = OR.Countperday(date,idE);
		if (!(OR.Countperday(date,idE).isEmpty()))
			return Response.ok(l, MediaType.APPLICATION_JSON).build();
		return Response.status(Status.NOT_FOUND).build();
		
	}

}
