package Webservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import services.TaskAttachmentService;

@Path("/taskfile")
@RequestScoped
public class TaskFileRessource {
	private final String UPLOADED_FILE_PATH = "C:\\Users\\dheker\\Desktop\\";
	@EJB
	TaskAttachmentService ts;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(MultipartFormDataInput input, @QueryParam("id") int id) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				System.out.println(id);
				System.out.println(fileName);
				ts.AddAttachment(fileName, id);
				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				writeFile(bytes, fileName);

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();

	}

	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFileWithGet(@QueryParam("file") String file) {

		File fileDownload = new File(UPLOADED_FILE_PATH + File.separator + file);
		ResponseBuilder response = Response.ok((Object) fileDownload);
		response.header("Content-Disposition", "attachment;filename=" + file);
		return response.build();
	}

	@GET
	@Path("/downloadbyid/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFileById(@PathParam(value = "id") int id) {

		String file = ts.findAttachmentById(id).getName();
		File fileDownload = new File(UPLOADED_FILE_PATH + File.separator + file);
		ResponseBuilder response = Response.ok((Object) fileDownload);
		response.header("Content-Disposition", "attachment;filename=" + file);
		return response.build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response deleteFileById(@PathParam(value = "id") int id) {

		String file = ts.findAttachmentById(id).getName();
		File fileDownload = new File(UPLOADED_FILE_PATH + File.separator + file);
		if (fileDownload.delete()) {
			ts.DeleteAttachment(id);
			
			return Response.ok("File deleted successfully").build();
		} else {
			
			return Response.status(Status.NOT_FOUND).entity("Failed to delete the file").build();
		}

	}
	@GET
	@Path("/getallfiles/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTask(@PathParam(value = "id") int id) {

		return Response.ok(ts.findAllAttachments(id)).build();
	}
	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
}
