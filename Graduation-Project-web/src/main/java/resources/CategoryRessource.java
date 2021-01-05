package resources;

import java.io.FileNotFoundException;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



import entites.Sheet;
import services.CategoryService;
import services.DefenseService;
import services.SheetService;
import services.TeacherService;

@Path("categoryressource")
@SessionScoped
public class CategoryRessource {
	@EJB
	private DefenseService defenseservice;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategories(@PathParam(value = "id") int idTeacher) {
		//categoryservice.updateNumberDefense(idTeacher);
//		try {
//			defenseservice.generatePdfOfAllTheDefenses();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	return Response.status(Status.CREATED).build();

	}
}
