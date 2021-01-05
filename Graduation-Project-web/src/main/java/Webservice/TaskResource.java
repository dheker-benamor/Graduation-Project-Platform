package Webservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.simple.JSONObject;
import entites.Task;
import services.TaskService;
import utilities.Rake;
import entites.TaskStatus;

@Path("/task")
@RequestScoped
public class TaskResource {
	@EJB
	TaskService taskservice;

	@GET
	@Path("/getalltask")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTask() {
	/*	Student s = (Student) AuthenticationRessource.LoggedPerson;
		System.out.println(s.getId());*/
		return Response.ok(taskservice.findAllTask()).build();
	}

	@POST
	@Path("/addtask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(Task e) {
		taskservice.AjouterTask(e);
		return Response.status(Status.CREATED).entity(e).build();
	}

	@DELETE
	@Path("/deletetask/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@PathParam(value = "id") int id) {

		taskservice.DeleteTask(id);
		 return Response.ok().build();
	}

	@GET
	@Path("/gettask/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam(value = "id") int id) {
		Task task = taskservice.findTaskById(id);

		return Response.ok(task).build();

	}

	@PUT
	@Path("/updatetask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(Task task) {

		return Response.ok(taskservice.UpdateTask(task)).build();

	}

	@PUT
	@Path("/updatestatus")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateStatus(Task task) {

		taskservice.UpdateStatus(task);
		return Response.ok("updated").build();
	}
	@PUT
	@Path("/updateapproved")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateApproved(Task task) {

		String desc = taskservice.UpdateApproved(task);
		return Response.ok(desc).build();
	}

	@GET
	@Path("/gettasksbyproject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasksByProject(@PathParam(value = "id") int id) {

		return Response.ok(taskservice.findTaskByProject(id)).build();

	}
	
	@GET
	@Path("/getkeywords/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTaskKeywords(@PathParam(value = "id") int id) {
		Task task = taskservice.findTaskById(id);
		String languageCode = "ENGLISH";
		Rake rake = new Rake(languageCode);
		Map<String, Double> results = rake.getKeywordsFromText(task.getDescription());	
		List <JSONObject> keywords =new ArrayList<JSONObject>();
		results.forEach((k, v) -> {
			
			JSONObject obj = new JSONObject();
			obj.put("keyword",k);
			obj.put("score", v);
			keywords.add(obj);
		});
		return Response.ok(keywords).build();

	}
	@GET
	@Path("/getstatsbyproject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStats(@PathParam(value = "id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("TO_DO",taskservice.getstats(TaskStatus.TO_DO, id));
		obj.put("DOING", taskservice.getstats(TaskStatus.DOING, id));
		JSONObject done = new JSONObject();
		done.put("number",taskservice.getstats(TaskStatus.DONE, id));
		done.put("approved", taskservice.getapprovedstats(1, id));
		done.put("notapproved", taskservice.getapprovedstats(0, id));
		obj.put("DONE", done);
		return Response.ok(obj).build();

	}
	@GET
	@Path("/getdatestats/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDateStats(@PathParam(value = "id") int id) {
		JSONObject obj = new JSONObject();
		obj.put("inTime",taskservice.dateStats("=",id));
		obj.put("withDelay",taskservice.dateStats("<",id));
		obj.put("withoutDelay",taskservice.dateStats(">",id));
		return Response.ok(obj).build();

	}
}
