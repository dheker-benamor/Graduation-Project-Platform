package iservices;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import entites.Sheet;
import entites.Staff;
import entites.User;

@Remote
public interface StaffRemote {
public void consommation();
 public int addStaff(Staff e);
 public List<User> ListeStaff();
void AcceptSheet(int idSheet);
Boolean RefuseSheet(Sheet sheet, int idSheet);
Boolean AcceptModification(int idSheet);

Boolean RefuseModification(int idSheet , Sheet sheet , String ref);
Boolean AcceptCancelRequest(int idSheet);
Boolean RefusCancelRequest(int idSheet);
List<Sheet> ListAllSheet();
List<Sheet> ListArchive();
List<Sheet> ListNotAcceptedSheets();
Set<Sheet> ListModification(int idStaff);
Set<Sheet> DisplayStaffSheets(int idStaff);
Staff findStaffbyId(int idStaff);
void AddStaffSheetManually(int idSheet, int idStaff);
Boolean RefuseSheetbySupervisor(Sheet sheet, int idSheet);
Boolean AcceptSheetbySupervisor(Sheet sheet, int idSheet);
String findCategoryforPdf(int idSheet);
Map<String, Integer> SheetStats();
int addSupervisor(Staff e);
int adddepartmentManager(Staff e);
void DeleteStaffFromSheet(int idSheet);






 
}
