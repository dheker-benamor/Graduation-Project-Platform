package iservices;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.json.JSONObject;

import com.google.api.services.calendar.model.Event;


import entites.Category;
import entites.Defense;
import entites.Sheet;
import entites.Teacher;

@Local
public interface DefenseServiceInterface {

	//public String generateDefense(String date_soutenance, String startTime, String endTime);

	public boolean verifyIfTheTeacherHasDefenseOrNot(int idTeacher, String date_soutenance, String time_defense,
			String name_classroom);

	public boolean getTheNumberOfDefensesPerTeacher(int idTeacher);

	public boolean testifthesheetAlreadyExists(int idSheet);

	public void sendConfirmationEmail(Teacher t, Defense d, String titreJury, Sheet sheet);

	public void updateDefenseConfirmationPresident(int idDefense);

	public void updateDefenseConfirmationRapporteur(int idDefense);

	public boolean verifyIfTheClassroomIsAffected(String time_defense, String name_classroom, String date_defense);

	public List<Defense> getAllDefensesThatAreConfrimed();

	//public String generatePdfOfAllTheDefenses() throws FileNotFoundException, DocumentException;

	public void updateStateDefenseAutomaticaly();

	public List<Defense> getAllDefenses();

	public void checkIfTheDefenseIsConfirmed();

	public void addADefenseToArchive();

	public List<JSONObject> getAllUpcomingEventsByEmail(String email, String date);

	public String reportDefense(int idDefense, String date);

	public void sendReminderEmail(String email, String subject, String content);

	public List<Teacher> getAllTeachersWithSpecificCategories(int id);

	public void affectAReporterToASheet(int idSheet, Teacher reporter);

	public void affectAPresidentToASheet(int idSheet, Teacher reporter);

	public List<Teacher> getAllTheTeachersDisponibles(String date_soutenance, String startTime, String endTime,
			List<Category> categories);

	public Teacher getTheReporter(int idSheet);

	public Teacher getThePresident(int idSheet);

	public Teacher getTheFramer(int idSheet);

	public boolean AddDefenseInDatabase(Sheet sheet, Date datedefense, String location, String time);

	public void AddDefenseInGoogleCalendar(String emailEncadreur, String emailRapporteur, String emailPresident,
			String description, String start, String end);

	public void sendConfirmationEmailForPresident(String email,String time,String date,String description,int idDefense);

	public void sendConfirmationEmailForReporter(String email,String time,String date,String description,int idDefense);

	public void sendConfirmationEmailForFramer(String email,String time,String date,String description,int idDefense);
	
	public int getIdDefenseFromIdSheet(int idSheet);


}
