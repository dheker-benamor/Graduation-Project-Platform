package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Defense;
import entites.Sheet;
import entites.Teacher;

@Local
public interface SheetServiceInterface {

	public List<Sheet> getAllSheetsWithMarks();
	public List<Sheet> getAllSheets();

	public int getTheEncadreurOfSheet(int idSheet);
	
	public  void  programAdefenseToSheet(int idSheet);
	
	public int getTheIdOfStudentFromSheet(int idSheet);
	
	public Integer getTheIdOfSheetFromDefense(Defense d);
}
