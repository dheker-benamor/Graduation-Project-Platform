package services;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;


import entites.Category;
import entites.Defense;
import entites.Jury;
import entites.Sheet;
import entites.Teacher;
import iservices.DefenseServiceInterface;
import iservices.StudentLocal;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import utilities.JavaMailUtil;
import utilities.Test;

@Stateless
@LocalBean
public class DefenseService implements DefenseServiceInterface {
	@PersistenceContext
	EntityManager entityManager;

	// the service of sheet
	@EJB
	private SheetService sheetservice;
	// the service of category
	@EJB
	private CategoryService categoryservice;
	@EJB
	private TeacherService teacherService;
	@EJB
	private StudentLocal studentservice;
	public String result;
	private static List<Time> data = new ArrayList<Time>();;
	private List<Sheet> sheetsatraites = new ArrayList<Sheet>();
	private List<String> sallesaaffectes = new ArrayList<String>();
	private String s_ran;
	private String s_ran_name_Salle;

	private LocalTime localtime;
	private String[] nameBloc = { "A", "B" };
	private String[] name_class = { "01", "02", "03" };

	private static final String APPLICATION_NAME = "GoogleCalenderApi";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/utilities/credentials.json";
	private Events eventss;
	private Events eventss2;
	private NetHttpTransport HTTP_TRANSPORT;
	private boolean testCalendar = false;
//
//	@Override
//	public String generateDefense(String date_soutenance, String startTime, String endTime) {
//		// First : get all the defenses that has a mark
//
//		long diff = Math.abs(java.sql.Time.valueOf(endTime).getTime() - java.sql.Time.valueOf(startTime).getTime());
//		long res = (diff / 60000) / 60;
//		java.sql.Time myTime = java.sql.Time.valueOf(startTime);
//		localtime = myTime.toLocalTime();
//		String startDate = date_soutenance + " " + startTime;
//		Timestamp sDate = Timestamp.valueOf(startDate);
//		String endDate = date_soutenance + " " + endTime;
//		Timestamp eDate = Timestamp.valueOf(endDate);
//		DateTime start = new DateTime(sDate, TimeZone.getTimeZone("UTC"));
//		DateTime end = new DateTime(eDate, TimeZone.getTimeZone("UTC"));
//		int hourCount = 0;
//		for (Sheet sheet : sheetservice.getAllSheetsWithMarks()) {
//			if (testifthesheetAlreadyExists(sheet.getId_sheet())) {
//				Defense defense = new Defense();
//				defense.setDateDefense(java.sql.Date.valueOf(date_soutenance));
//				LocalTime defenseTime = localtime.plusHours(hourCount);
//				System.out.println("Defense time : " + defenseTime);
//				defense.setTimeDefense(java.sql.Time.valueOf(defenseTime));
//				String startDateCalendar = date_soutenance + " " + defenseTime + ":00";
//				Timestamp sDateCalendar = Timestamp.valueOf(startDateCalendar);
//				DateTime startDefense = new DateTime(sDateCalendar, TimeZone.getTimeZone("UTC"));
//				String endDateCalendar = date_soutenance + " " + defenseTime.plusHours(1) + ":00";
//				Timestamp eDateCalendar = Timestamp.valueOf(endDateCalendar);
//				DateTime endDefense = new DateTime(eDateCalendar, TimeZone.getTimeZone("UTC"));
//				// For the affectation Salles
//				Random ran_bloc = new Random();
//				Random ran_class = new Random();
//				boolean foundClass = false;
//				while (!foundClass) {
//					String classRoom = nameBloc[ran_bloc.nextInt(nameBloc.length)]
//							+ name_class[ran_class.nextInt(name_class.length)];
//					if (this.verifyIfTheClassroomIsAffected(String.valueOf(defenseTime), classRoom, date_soutenance)) {
//						defense.setNameClassroom(classRoom);
//						foundClass = true;
//					}
//				}
//
//				// Second :get all the categories from sheets
//				sheetsatraites.add(sheet);
//				System.out.println("liste de sheets " + sheetsatraites);
//				List<Teacher> teachersaffectes = new ArrayList<Teacher>();
//				for (Category category : categoryservice.getAllCategoriesFromSheet(sheet.getId_sheet())) {
//					// Third : get all the teachers with the same categories
//					for (Teacher teacher : categoryservice.getAllTeachersWithCategory(category.getIdCategory())) {
//
//						// Verify the disponibility of teacher by consumming a web service
//						Client client = ClientBuilder.newClient();
//						result = client
//								.target("http://localhost:8082/result.php?startTime=" + startTime + "&dateCourse="
//										+ date_soutenance + "&IdTeacher="
//										+ teacherService.getCodeTeacherById(teacher.getId()) + "&endTime=" + endTime)
//								.request(MediaType.TEXT_PLAIN).get(String.class);
//
//						// Verify the disponibility from Google Calendar
//
//						try {
//							HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//							Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
//									getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
//
//							eventss = service.events().list(teacher.getEmail()).setTimeMin(startDefense)
//
//									.setSingleEvents(true).execute();
//							eventss2 = service.events().list(teacher.getEmail()).setTimeMin(endDefense)
//
//									.setSingleEvents(true).execute();
//							System.out.println("evennnnts" + eventss);
//							System.out.println("empty wala le" + eventss.getItems().isEmpty());
//							System.out.println("empty wala le 222" + eventss2.getItems().isEmpty());
//
//						} catch (GeneralSecurityException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//						// add the list of affected teachers
//						if (result.equals("true")
//								&& verifyIfTheTeacherHasDefenseOrNot(teacher.getCodeTeacher(), date_soutenance,
//										String.valueOf(defenseTime), s_ran_name_Salle)
//								&& sheetservice.getTheEncadreurOfSheet(sheet.getId_sheet()) != teacher.getId()
//								&& eventss2.getItems().isEmpty() && eventss.getItems().isEmpty())
//							teachersaffectes.add(teacher);
//						else
//							return "Any or not all the defense will take place today:No enough teachers";
//					}
//
//				}
//				System.out.println("liste de teachers " + teachersaffectes);
//
//				// Verify the disponibility of classrooms
//				Client client_ws_classrooms = ClientBuilder.newClient();
//				String result_ws_classrooms = client_ws_classrooms
//						.target("http://localhost:8082/salles.php?nomSalle=" + s_ran_name_Salle)
//						.request(MediaType.TEXT_PLAIN).get(String.class);
//				System.out.println("defeenseee" + defense);
//				defense.setSheet(sheet);
//
//				if (teachersaffectes.size() >= 2) {
//					defense.setConfirmPresident(false);
//					defense.setConfirmRapporteur(false);
//					defense.setStateDefense("Waiting");
//					entityManager.persist(defense);
//					sheetservice.programAdefenseToSheet(sheet.getId_sheet());
//
//					List<Teacher> subItems = new ArrayList<Teacher>(teachersaffectes.subList(0, 2));
//
//					// if (teacherService.getTheNumberOfeacher(t.getCodeTeacher())<2) {
//
//					Random ran = new Random();
//					List<String> listTitles = new ArrayList<String>();
//					listTitles.add("PRESIDENT");
//					listTitles.add("RAPPORTEUR");
//					for (int j = 0; j < 2; j++) {
//						Jury jury = new Jury();
//						jury.setDefense(defense);
//						int a = ran.nextInt(listTitles.size());
//						System.out.println(ran);
//						s_ran = listTitles.get(a);
//						System.out.println(s_ran);
//						jury.setTitleJyry(s_ran);
//						listTitles.remove(a);
//						// teacherService.updateNumberDefense(t.getCodeTeacher());
//						jury.setTeacher(subItems.get(j));
//						entityManager.persist(jury);
//						// add the event in google calendar
//						Event event = new Event();
//						event.setSummary("Defense");
//						event.setDescription(
//								"You will be the" + s_ran + "of this defense whose subject:" + sheet.getDescription());
//						ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
//						attendees.add(new EventAttendee().setEmail(subItems.get(j).getEmail()));
//
//						attendees.add(new EventAttendee().setEmail(studentservice
//								.getEmailStudentById(sheetservice.getTheIdOfStudentFromSheet(sheet.getId_sheet()))));
//
//						attendees.add(new EventAttendee().setEmail(teacherService
//								.getTheEmailOfEncadreurById(sheetservice.getTheEncadreurOfSheet(sheet.getId_sheet()))));
//						event.setAttendees(attendees);
//
//						event.setStart(new EventDateTime().setDateTime(startDefense));
//						event.setEnd(new EventDateTime().setDateTime(endDefense));
//						EventReminder[] reminderOverrides = new EventReminder[] {
//								new EventReminder().setMethod("email").setMinutes(24 * 60),
//								new EventReminder().setMethod("popup").setMinutes(10), };
//						Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
//								.setOverrides(Arrays.asList(reminderOverrides));
//						event.setReminders(reminders);
//						try {
//							HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//							Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
//									getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
//							Event createdEvent = service.events().insert("primary", event).execute();
//
//						} catch (GeneralSecurityException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//						this.sendConfirmationEmail(subItems.get(j), defense, s_ran, sheet);
//
//					}
//					// } else {
//
//					// System.out.println("MSG:the teachers are already assigned to the defense");
//
//					// }
//
//				} else {
//
//					return "All the defenses were not programmed for lack of available teachers";
//
//				}
//
//				hourCount++;
//				if (hourCount > res - 1)
//					hourCount = 0;
//			} else {
//
//				return "These sheets are already affected";
//
//			}
//
//		}
//		return "All the sheets were programmed and defenses are ready!!";
//
//	}

	@Override
	public boolean verifyIfTheTeacherHasDefenseOrNot(int idTeacher, String date_soutenance, String time_defense,
			String name_classroom) {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery(
				"select * from defense d ,jury j where d.id_defense=j.id_defensee and j.id_teacher=" + idTeacher
						+ " and d.date_defense='" + date_soutenance + "' and d.time_defense='" + time_defense + "'");
		List<?> result = query.getResultList();
		if (result.isEmpty()) {

			return true;
		}

		return false;

	}

	@Override
	public boolean getTheNumberOfDefensesPerTeacher(int idTeacher) {
		Query query = entityManager.createNativeQuery("SELECT COUNT(*) AS nbr FROM Jury where id_teacher=" + idTeacher);
		List<BigInteger> result = query.getResultList();
		for (BigInteger l : result) {
			if (l.equals(2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean testifthesheetAlreadyExists(int idSheet) {
		Query query = entityManager.createNativeQuery("SELECT * FROM Defense where sheet_id_sheet =" + idSheet);
		List<?> result = query.getResultList();
		if (result.isEmpty()) {
			return true;

		}
		return false;
	}

	@Override
	public void sendConfirmationEmail(Teacher t, Defense d, String titreJury, Sheet sheet) {
		// TODO Auto-generated method stub
		if (titreJury.equals("PRESIDENT")) {
			System.out.println(t.getEmail() + t.getEmail());
			try {
				JavaMailUtil.sendMail(t.getEmail(), "Confirmation defense", "<h2>Confirmation defense</h2>"
						+ "<h4>Date of Defense:" + String.valueOf(d.getDateDefense()) + "</h4>" + "<h4>Time of defense:"
						+ String.valueOf(d.getTimeDefense()) + "</h4>" + "<h4>Jury title:" + titreJury + "</h4>"
						+ "<h4>Description of the Subject:" + sheet.getDescription() + "</h4>"
						+ "<a href='http://localhost:9080/Graduation-Project-web/defenseressource/confirmPresident/"
						+ String.valueOf(d.getId_defense()) + "'>Confirmer votre présence<a/>");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				JavaMailUtil.sendMail(t.getEmail(), "Confirmation defense", "<h2>Confirmation defense</h2>"
						+ "<h4>Date of Defense:" + String.valueOf(d.getDateDefense()) + "</h4>" + "<h4>Time of defense:"
						+ String.valueOf(d.getTimeDefense()) + "</h4>" + "<h4>Jury title:" + titreJury + "</h4>"
						+ "<h4>Description of the Subject:" + sheet.getDescription() + "</h4>"

						+ "<a href='http://localhost:9080/Graduation-Project-web/defenseressource/confirmRapporteur/"
						+ String.valueOf(d.getId_defense()) + "'>Confirmer votre présence<a/>");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void updateDefenseConfirmationPresident(int idDefense) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("update Defense d set confirmPresident=1 where d.id_defense=:idDefense");
		query.setParameter("idDefense", idDefense);
		int modified = query.executeUpdate();
		if (modified == 1) {
			System.out.println("successfully updated");
		} else {
			System.out.println("failed to update");
		}

	}

	@Override
	public void updateDefenseConfirmationRapporteur(int idDefense) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("update Defense d set confirmRapporteur=1 where d.id_defense=:idDefense");
		query.setParameter("idDefense", idDefense);
		int modified = query.executeUpdate();
		if (modified == 1) {
			System.out.println("successfully updated");
		} else {
			System.out.println("failed to update");
		}

	}

	@Override
	public boolean verifyIfTheClassroomIsAffected(String time_defense, String name_classroom, String date_defense) {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("select * from defense  where name_classroom='" + name_classroom
				+ "' and time_defense='" + time_defense + "' and date_defense='" + date_defense + "'");
		List<?> result = query.getResultList();
		if (result.isEmpty()) {

			return true;
		}

		return false;
	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		InputStream in = Test.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8885).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	@Override
	public List<Defense> getAllDefensesThatAreConfrimed() {
		List<Defense> defenses = entityManager
				.createQuery("select e from Defense e",
						Defense.class)
				.getResultList();
		return defenses;

	}

//	@Override
//	public String generatePdfOfAllTheDefenses() throws FileNotFoundException, DocumentException {
//
//		Map<String, Object> params = new HashMap<>();
//		JRDataSource jrDataSource = new JREmptyDataSource();
//		for (Defense defense : this.getAllDefensesThatAreConfrimed()) {
//			// params.put("Fistname",sheet.getTitle());
//			// params.put("Lastname", sheet.getFeatures());
//			params.put("Start time", defense.getTimeDefense());
//			params.put("Classroom", defense.getNameClassroom());
//			params.put("President", "");
//			params.put("Reporter", "");
//			params.put("Encadreur", "");
//
//		}
//
//		InputStream inputStream = this.getClass().getResourceAsStream("Internship.jrxml");
//
//		String filename = "listpdf.pdf";
//
//		JasperReport jasperReport;
//		try {
//			jasperReport = JasperCompileManager.compileReport("F:/");
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
//			JasperExportManager.exportReportToPdfFile(jasperPrint, "F:/" + filename);
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return filename;
//
//	}

	@Override
	public List<Defense> getAllDefenses() {
		List<Defense> defenses = entityManager.createNativeQuery(
				"Select id_defense,date_defense,state_defense,confirm_encadreur,confirm_president,confirmRapporteur,time_defense,name_classroom,location,sheet_id_sheet from defense",
				Defense.class).getResultList();
		System.out.println("liste defenses" + defenses);
		return defenses;
	}

	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	@Override
	public void updateStateDefenseAutomaticaly() {
		// TODO Auto-generated method stub
		Date date = new Date();

		for (Defense d : getAllDefenses()) {

			long diff = Math.round((date.getTime() - d.getDateDefense().getTime()) / (double) 86400000);
			System.out.println("timee" + diff);
			if (diff >= 2 && d.getStateDefense().equals("Waiting")) {
				Query query = entityManager
						.createQuery("update Defense e set stateDefense=:state where e.id=:defenseId");
				query.setParameter("state", "Reported");
				query.setParameter("defenseId", d.getId_defense());
				int modified = query.executeUpdate();
				try {
					Defense de = entityManager.find(Defense.class, d.getId_defense());
					JavaMailUtil.sendMail(
							studentservice.getEmailStudentById(sheetservice
									.getTheIdOfStudentFromSheet(sheetservice.getTheIdOfSheetFromDefense(d))),
							"Annulation defense", "<h2>The defense of " + d.getDateDefense() + "at "
									+ d.getTimeDefense() + " is reported</h2>");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (modified == 1) {
					System.out.println("successfully updated");
				} else {
					System.out.println("failed to update");
				}

			}
		}

	}

	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	@Override
	public void checkIfTheDefenseIsConfirmed() {
		for (Defense d : getAllDefenses()) {
			if (d.isConfirmPresident() && d.isConfirmRapporteur()) {
				Query query = entityManager
						.createQuery("update Defense e set stateDefense=:state where e.id=:defenseId");
				query.setParameter("state", "Confirmed");
				query.setParameter("defenseId", d.getId_defense());
				int modified = query.executeUpdate();
				try {
					JavaMailUtil.sendMail(
							studentservice.getEmailStudentById(sheetservice
									.getTheIdOfStudentFromSheet(sheetservice.getTheIdOfSheetFromDefense(d))),
							"Confirmation defense", "<h2>The defense of " + d.getDateDefense() + "at "
									+ d.getTimeDefense() + " is confirmed</h2>");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (modified == 1) {
					System.out.println("the state of defense is successfully updated");
				} else {
					System.out.println("failed to update");
				}

			}

		}

	}

	@Override
	public void addADefenseToArchive() {
		// TODO Auto-generated method stub
		Date date = new Date();

		for (Defense d : getAllDefenses()) {

			;

			if (date.compareTo(d.getDateDefense()) > 0 && d.getStateDefense().equals("Confirmed")) {
				Query query = entityManager
						.createQuery("update Defense e set stateDefense=:state where e.id=:defenseId");
				query.setParameter("state", "Archived");
				query.setParameter("defenseId", d.getId_defense());
				int modified = query.executeUpdate();
				if (modified == 1) {
					System.out.println("successfully updated");
				} else {
					System.out.println("failed to update");
				}

			}
		}
	}

	@Override
	public List<JSONObject> getAllUpcomingEventsByEmail(String email, String date) {
		List<JSONObject> listevents = new ArrayList<JSONObject>();
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			//Timestamp sDate = Timestamp.valueOf(date);
			//DateTime start = new DateTime(sDate, TimeZone.getTimeZone("UTC"));
			eventss = service.events().list(email).execute();
			for (Event ev : eventss.getItems()) {
				
				JSONObject obj = new JSONObject();
				
				obj.put("Subject",ev.getDescription());
				obj.put("StartTime",ev.getStart().getDateTime().getValue());
				obj.put("EndTime",ev.getEnd().getDateTime().getValue());

			
				listevents.add(obj);
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listevents;
	}

	@Override
	public String reportDefense(int idDefense, String date) {
		String a = "Waitingg";
		Query query = entityManager
				.createNativeQuery("update defense set date_defense='" + date + "' where id_defense=" + idDefense);
		int modified = query.executeUpdate();
		if (modified == 1) {
			System.out.println("successfully updated");
		} else {
			System.out.println("failed to update");
		}
		return "the defense was reported";

	}

	@Override
	public void sendReminderEmail(String email, String subject, String content) {
		try {
			JavaMailUtil.sendMail(email, subject, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Teacher> getAllTeachersWithSpecificCategories(int id) {
		List<Teacher> listTeachers = new ArrayList<Teacher>();

		for (Category category : categoryservice.getAllCategoriesFromSheet(id)) {

			for (Teacher teacher : categoryservice.getAllTeachersWithCategory(category.getId())) {

				listTeachers.add(teacher);
			}
		}
		return listTeachers;
	}

	@Override
	public void affectAReporterToASheet(int idSheet, Teacher reporter) {
		Query query = entityManager.createQuery("update Sheet d set reporter=:reporterId where d.id_sheet=:idSheet");
		query.setParameter("idSheet", idSheet);
		query.setParameter("reporterId", reporter);
		int modified = query.executeUpdate();
		if (modified == 1) {
			System.out.println("successfully updated");
		} else {
			System.out.println("failed to update");
		}

	}

	@Override
	public List<Teacher> getAllTheTeachersDisponibles(String date_soutenance, String startTime, String endTime,
			List<Category> categories) {
		List<Teacher> listteachers = new ArrayList<>();
		for (Category category : categories) {
			for (Teacher teacher : categoryservice.getAllTeachersWithCategory(category.getId())) {
				// Verify the disponibility of teacher by consumming a web service
				Client client = ClientBuilder.newClient();
				result = client.target("http://localhost:8082/result.php?startTime=" + startTime + "&dateCourse="
						+ date_soutenance + "&IdTeacher=" + teacherService.getCodeTeacherById(teacher.getId())
						+ "&endTime=" + endTime).request(MediaType.TEXT_PLAIN).get(String.class);

				// add the list of affected teachers
				if (result.equals("true")) {
					listteachers.add(teacher);
					return listteachers;
				} else
					return null;
			}
		

		}
		return listteachers;
	}

	@Override
	public void affectAPresidentToASheet(int idSheet, Teacher reporter) {
		Query query = entityManager.createQuery("update Sheet d set president=:reporterId where d.id_sheet=:idSheet");
		query.setParameter("idSheet", idSheet);
		query.setParameter("reporterId", reporter);
		int modified = query.executeUpdate();
		if (modified == 1) {
			System.out.println("successfully updated");
		} else {
			System.out.println("failed to update");
		}
		
	}

	@Override
	public Teacher getTheReporter(int idSheet) {
		Query query = entityManager.createNativeQuery("select reporter_id FROM Sheet where id_sheet="+idSheet);
		Teacher teacher=entityManager.find(Teacher.class,  (int)query.getSingleResult());
		return teacher;
	}

	@Override
	public Teacher getThePresident(int idSheet) {
		Query query = entityManager.createNativeQuery("select president_id  FROM Sheet where id_sheet="+idSheet);
		Teacher teacher=entityManager.find(Teacher.class,  (int)query.getSingleResult());
		return teacher;
	}

	@Override
	public Teacher getTheFramer(int idSheet) {
		Query query = entityManager.createNativeQuery("select encadreur_id FROM Sheet where id_sheet="+idSheet);
		Teacher teacher=entityManager.find(Teacher.class,  (int)query.getSingleResult());
		return teacher;
	}


	@Override
	public boolean AddDefenseInDatabase(Sheet sheet, Date datedefense, String location,String time) {
		// TODO Auto-generated method stub

 boolean test=true;
			for(Defense def:this.getAllDefenses()) {
	System.out.println(def.getSheet());
			if(def.getSheet().getId_sheet()==sheet.getId_sheet()) {
				
				test= false;
				return false;
		}
	
	
		}
			if(test==true){
				System.out.println("sheet"+sheet);
				Defense defense = new Defense();
				defense.setDateDefense(datedefense);
				defense.setLocation(location);
				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
				Date d;
				try {
					d = dateFormat.parse(time);
					defense.setTimeDefense(d);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				defense.setStateDefense("Confirmed");
			defense.setSheet(sheet);
			// For the affectation Salles
			Random ran_bloc = new Random();
			Random ran_class = new Random();
			boolean foundClass = false;
			while (!foundClass) {
				String classRoom = nameBloc[ran_bloc.nextInt(nameBloc.length)]
						+ name_class[ran_class.nextInt(name_class.length)];
				if (this.verifyIfTheClassroomIsAffected(String.valueOf(datedefense), classRoom, String.valueOf(datedefense))) {
					defense.setNameClassroom(classRoom);
					foundClass = true;
				}
			}
			entityManager.persist(defense);
	   
			}
			return true;
	
		
	}

	@Override
	public void AddDefenseInGoogleCalendar(String emailEncadreur,String emailRapporteur,String emailPresident,String description,String start,String end) {
		// TODO Auto-generated method stub
		// add the event in google calendar
		Event event = new Event();
		event.setSummary("Defense");
		event.setDescription(description);
		ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		attendees.add(new EventAttendee().setEmail(emailEncadreur));
		attendees.add(new EventAttendee().setEmail(emailRapporteur));
		attendees.add(new EventAttendee().setEmail(emailPresident));
		event.setAttendees(attendees);

		Timestamp sDateCalendar = Timestamp.valueOf(start);
		DateTime startDefense = new DateTime(sDateCalendar, TimeZone.getTimeZone("UTC"));
	
		Timestamp eDateCalendar = Timestamp.valueOf(end);
		DateTime endDefense = new DateTime(eDateCalendar, TimeZone.getTimeZone("UTC"));
		event.setStart(new EventDateTime().setDateTime(startDefense));
		event.setEnd(new EventDateTime().setDateTime(endDefense));
		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
			Event createdEvent = service.events().insert("primary", event).execute();

		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private boolean isNull(Object obj) {
	    return obj == null;
	}

	@Override
	public void sendConfirmationEmailForPresident(String email,String time,String date,String description,int idDefense) {
		// TODO Auto-generated method stub
		try {
			JavaMailUtil.sendMail(email, "Confirmation defense", "<h2>Confirmation defense</h2>"
					+ "<h4>Date of Defense:" +date + "</h4>" + "<h4>Time of defense:"
					+ time + "</h4>" + "<h4>Jury title: PRESIDENT</h4>"
					+ "<h4>Description of the Subject:" + description+ "</h4>"
					+ "<a href='http://localhost:9080/Graduation-Project-web/defenseressource/confirmPresident/"
					+ String.valueOf(idDefense) + "'>Confirmer votre présence<a/>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public void sendConfirmationEmailForReporter(String email,String time,String date,String description,int idDefense) {
		// TODO Auto-generated method stub
		try {
			JavaMailUtil.sendMail(email, "Confirmation defense", "<h2>Confirmation defense</h2>"
					+ "<h4>Date of Defense:" +date + "</h4>" + "<h4>Time of defense:"
					+ time + "</h4>" + "<h4>Jury title: REPORTER</h4>"
					+ "<h4>Description of the Subject:" + description+ "</h4>"
					+ "<a href='http://localhost:9080/Graduation-Project-web/defenseressource/confirmRapporteur/"
					+ String.valueOf(idDefense) + "'>Confirmer votre présence<a/>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void sendConfirmationEmailForFramer(String email,String time,String date,String description,int idDefense) {
		// TODO Auto-generated method stub
	
		try {
			JavaMailUtil.sendMail(email, "Confirmation defense", "<h2>Confirmation defense</h2>"
					+ "<h4>Date of Defense:" +date + "</h4>" + "<h4>Time of defense:"
					+ time + "</h4>" + "<h4>Jury title: FRAMER</h4>"
					+ "<h4>Description of the Subject:" + description+ "</h4>"
					+ "<a href='http://localhost:9080/Graduation-Project-web/defenseressource/confirmEncadreur/"
					+ String.valueOf(idDefense) + "'>Confirmer votre présence<a/>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getIdDefenseFromIdSheet(int idSheet) {
		Query query = entityManager.createNativeQuery("select id_defense FROM defense where sheet_id_sheet="+idSheet);
		return (int)query.getSingleResult();
	}


}
