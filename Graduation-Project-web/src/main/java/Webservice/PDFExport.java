package Webservice;


import javax.ejb.EJB;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.io.IOException;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import entites.Entreprise;
import entites.Student;
import entites.User;
import iservices.EntrepriseRemote;
import iservices.StudentRemote;
import iservices.UserRemote;


@WebServlet("/pdfexport")
public class PDFExport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB(beanName = "EntrepriseService")
	EntrepriseRemote er;
	@EJB(beanName="UserService")
	UserRemote sr;

	@Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException, java.io.IOException {
		
        int id = 0;
          int idE=0; 
        try { 
        	
            id = Integer.parseInt( request.getParameter( "id" ) );
           idE= Integer.parseInt( request.getParameter( "idE" ) );
        } catch (Exception exception ) {
            System.out.println(exception);
        }
       Entreprise [] lines = {
            er.getinfo(id),
            
        };
       User [] liness = {
    		   sr.getinfo(idE)
       };
       //raed
        String masterPath = ((GenericServlet) request).getServletContext().getRealPath( "/WEB-INF/Convention.pdf" );
        response.setContentType( "application/pdf" );
        
        try ( PdfReader reader = new PdfReader( masterPath );
              PdfWriter writer = new PdfWriter( response.getOutputStream() );
              PdfDocument document = new PdfDocument( reader, writer ) ) {
            
            PdfPage page = document.getPage( 1 );
            PdfCanvas canvas = new PdfCanvas( page );
            
            FontProgram fontProgram = FontProgramFactory.createFont();
            PdfFont font = PdfFontFactory.createFont( fontProgram, "utf-8", true );
            canvas.setFontAndSize( font, 12 );
            
            canvas.beginText();
            
          
            
           
            
           
            
            int top = 500;
        
            
            
            for (Entreprise line : lines) {
            	System.out.println(line);
            	 canvas.setTextMatrix( 250, 693 );
                 canvas.showText(" " + line.getNomEntreprise());
                 
                 canvas.setTextMatrix(250, 670);
                 canvas.showText(" "+line.getDescription());
                 canvas.setTextMatrix(250, 650);
                 canvas.showText(" "+line.getAdresse());
                 canvas.setTextMatrix(150, 627);
                 canvas.showText(" "+line.getNumtelentre());
                 canvas.setTextMatrix(250, 605);
                 canvas.showText(" "+line.getMail());
        
            }
            for(User linex :liness) {
            	canvas.setTextMatrix(100, 520);
            	canvas.showText(""+linex.getNom()+" "+linex.getPrenom());
            	canvas.setTextMatrix(300, 475);
            	canvas.showText(""+linex.getEmail());
            	canvas.setTextMatrix(100, 475);
            	canvas.showText(""+linex.getNumTel());
            }

           canvas.endText();
            
        }
	}}
        
   
	
	
	
	
	
	

