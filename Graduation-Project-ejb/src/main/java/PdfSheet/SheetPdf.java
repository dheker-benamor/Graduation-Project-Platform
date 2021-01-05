package PdfSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entites.Category;
import entites.Sheet;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class SheetPdf {

public String generateSheetPdf(Sheet sheet) throws  JRException{
	
		Map<String, Object> params = new HashMap<>();
		JRDataSource jrDataSource = new JREmptyDataSource();
		params.put("title",sheet.getTitle());
		params.put("features", sheet.getFeatures());
		params.put("issue", sheet.getIssue());
		params.put("keywords",sheet.getKey_words());
		params.put("firstname", sheet.getStudent().getNom());
		params.put("lastname", sheet.getStudent().getPrenom());
		params.put("description", sheet.getDescription());
		
		params.put("stafffirstname", sheet.getStaffSheets().get(0).getNom());
		params.put("stafflastname", sheet.getStaffSheets().get(0).getPrenom());
		
		
		
	
		
		//InputStream inputStream = this.getClass().getResourceAsStream("Internship.jrxml");
        
        String filename = "Sheet"+sheet.getId_sheet()+".pdf";
        
		JasperReport jasperReport = JasperCompileManager.compileReport("/home/moatez/Documents/Graduation-Project-PIDEV/Graduation-Project-ejb/src/main/java/PdfSheet/Blank_Letter.jrxml");
		JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, params ,jrDataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/moatez/Documents/"+filename);

		return filename;
	}
}
