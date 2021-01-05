package resources;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entites.Sheet;
import entites.Teacher;
import services.DefenseService;
import services.SheetService;
import services.TeacherService;

@Path("defenseressource")
@SessionScoped
public class DefenseRessource {
	@EJB
	private DefenseService defenseservice;
	@EJB
	private SheetService sheetService;
	@EJB
	private TeacherService teacherService;

	@GET
	@Path("confirmPresident/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmPresident(@PathParam(value = "id") int idDefense) {
		defenseservice.updateDefenseConfirmationPresident(idDefense);
		return Response.status(Status.CREATED).build();
	}

	@GET
	@Path("confirmRapporteur/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmRapporteur(@PathParam(value = "id") int idDefense) {
		defenseservice.updateDefenseConfirmationRapporteur(idDefense);
		return Response.status(Status.CREATED).build();
	}

//	@POST
//	// @Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Response update(@FormParam("ds") String date_soutenance, @FormParam("st") String startTime,
//			@FormParam("dt") String endTime) {
//		//return Response.status(Status.ACCEPTED).entity(defenseservice.generateDefense(date_soutenance, startTime, endTime)).build();
//	}

	@GET
	@Path("getConfirmedDefenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDefensesconfirmed() {
		return Response.status(Status.ACCEPTED).entity(defenseservice.getAllDefensesThatAreConfrimed()).build();

	}
	@GET
	@Path("getAllDefenses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDefenses() {
		return Response.status(Status.ACCEPTED).entity(defenseservice.getAllDefenses()).build();

	}


	@GET
	@Path("getUpcoming/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllupcomingEvents(@PathParam(value = "email") String email) {
        String date="";
		return Response.status(Status.CREATED).entity(defenseservice.getAllUpcomingEventsByEmail(email, date).toString()).build();

	}

	@GET
	@Path("report/{id}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reportDefense(@PathParam(value = "id") int idDefense, @PathParam(value = "date") String date) {

		return Response.status(Status.CREATED).entity(defenseservice.reportDefense(idDefense, date)).build();

	}

	@GET
	@Path("/getAllSheets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheets() {

		return Response.status(Status.ACCEPTED).entity(sheetService.getAllSheets()).build();

	}
	  
	@GET
	@Path("sendReminderemail/{email}/{subject}/{content}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailReminder(@PathParam(value = "email") String email,@PathParam(value = "subject") String subject,@PathParam(value = "content") String content) {
		this.defenseservice.sendReminderEmail(email, subject, content);
		return Response.status(Status.CREATED).build();
	}
	@GET
	@Path("getAllTeachersWithSpecificCategories/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTeachersWithSpecificCategoried(@PathParam(value = "id") int id) {
		
		return Response.status(Status.CREATED).entity(defenseservice.getAllTeachersWithSpecificCategories(id)).build();
	}
	 
	@GET
	@Path("getTeacherById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeacherById(@PathParam(value = "id") int id) {
		
		return Response.status(Status.CREATED).entity(this.teacherService.getTheTeacherById(id)).build();
	}
	
	@POST
	@Path("affectReporter/{idsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response affectReporter(@PathParam(value = "idsheet") int idsheet,Teacher reporter) {
		this.defenseservice.affectAReporterToASheet(idsheet,reporter);
		return Response.status(Status.ACCEPTED).build();
	}
	@POST
	@Path("affectPresident/{idsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response affectPresident(@PathParam(value = "idsheet") int idsheet,Teacher reporter) {
		this.defenseservice.affectAPresidentToASheet(idsheet, reporter);
		return Response.status(Status.ACCEPTED).build();
	}
	@GET
	@Path("getTheReporter/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTheReporter(@PathParam(value = "id") int idSheet) {
	
		return Response.status(Status.OK).entity(defenseservice.getTheReporter(idSheet)).build();
	}
	@GET
	@Path("getThePresident/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThePresident(@PathParam(value = "id") int idSheet) {
	
		return Response.status(Status.OK).entity(defenseservice.getThePresident(idSheet)).build();
	}
	@GET
	@Path("getTheFramer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTheFramer(@PathParam(value = "id") int idSheet) {
	
		return Response.status(Status.OK).entity(defenseservice.getTheFramer(idSheet)).build();
	}
	@POST
	@Path("addDefense/{date}/{location}/{time}")
	 @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addDefense(@PathParam("date") java.sql.Date datedefense, @PathParam("location") String location,Sheet sheet, @PathParam("time")String time) {
	;
		return Response.status(Status.ACCEPTED).entity(	defenseservice.AddDefenseInDatabase(sheet, datedefense, location,time)).build();
	}
	@POST
	@Path("addDefenseGoogleCalendar/{emailReporter}/{emailFramer}/{emailPresident}/{description}/{start}/{end}")
	 @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addDefenseGoogleCalendar(@PathParam("emailReporter") String emailRapporteur , @PathParam("emailFramer") String emailEncadreur,@PathParam("emailPresident") String emailPresident,@PathParam("description") String description,@PathParam("start") String start,@PathParam("end") String end) {
		defenseservice.AddDefenseInGoogleCalendar(emailEncadreur, emailRapporteur, emailPresident, description, start, end);;
		return Response.status(Status.ACCEPTED).build();
	}
	@GET
	@Path("getEmailTeacherById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmailTeacherById(@PathParam(value = "id") int id) {
	
		return Response.status(Status.CREATED).entity(teacherService.getTheEmailOfEncadreurById(id)).build();
	}
	@GET
	@Path("sendEmailToPresident/{email}/{time}/{date}/{description}/{idDefense}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailReminder(@PathParam(value = "email") String email,@PathParam(value = "time") String time,@PathParam(value = "date") String date,@PathParam(value = "description") String description,@PathParam(value = "idDefense")int idDefense) {
		this.defenseservice.sendConfirmationEmailForPresident(email, time, date, description, idDefense);
		return Response.status(Status.CREATED).build();
	}
	@GET
	@Path("sendEmailToReporter/{email}/{time}/{date}/{description}/{idDefense}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailToReporter(@PathParam(value = "email") String email,@PathParam(value = "time") String time,@PathParam(value = "date") String date,@PathParam(value = "description") String description,@PathParam(value = "idDefense")int idDefense) {
		this.defenseservice.sendConfirmationEmailForReporter(email, time, date, description, idDefense);
		return Response.status(Status.CREATED).build();
	}
	@GET
	@Path("sendEmailToFramer/{email}/{time}/{date}/{description}/{idDefense}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailToFramer(@PathParam(value = "email") String email,@PathParam(value = "time") String time,@PathParam(value = "date") String date,@PathParam(value = "description") String description,@PathParam(value = "idDefense")int idDefense) {
		this.defenseservice.sendConfirmationEmailForFramer(email, time, date, description, idDefense);
		return Response.status(Status.CREATED).build();
	}
	@GET
	@Path("getIdDefenseFromSheet/{idSheet}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIdDefenseFromSheet(@PathParam(value = "idSheet")int idSheet) {
		
		return Response.status(Status.CREATED).entity(this.defenseservice.getIdDefenseFromIdSheet(idSheet)).build();
	}
	
	
	
}
