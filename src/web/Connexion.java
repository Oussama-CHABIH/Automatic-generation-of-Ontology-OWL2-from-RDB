package web;

import domain.Categorization;
import domain.Categorization_Functions;
import domain.Table;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.jena.ontology.OntModel;

import global.DatabaseType;
import global.Ontology;
import global.RTAXON;
import global.SchemaRecoverer;


public class Connexion extends HttpServlet { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/Page_Connexion.jsp";
    ArrayList<Categorization> categorization_filter = new ArrayList<Categorization>();
	Connection con = null;
	OntModel ontologie =null;
	String databaseName = null;
    private static final int BUFSIZE = 4096;


    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

        /* Affichage de la page d'inscription */

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

    

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	String action = request.getParameter("action");
    	 
    	if (action.equals("Page_Connexion")){ 
    		databaseName = request.getParameter( "DatabaseName" );
    		String login = request.getParameter( "Login" );
    		String password = request.getParameter( "password" );
             ArrayList<String> tableTypes = new ArrayList<String>();
             tableTypes.add("TABLE"); 
            try {
     			SchemaRecoverer a = new SchemaRecoverer("localhost:3306", databaseName, login, password, DatabaseType.MySQL , tableTypes);
     			con = a.connect();
     			ArrayList<Table> SchemaRecover = new ArrayList<Table>();
     			ArrayList<Table> Schema = new ArrayList<Table>();
     			SchemaRecover= a.getSchema();
     			Ontology.con= con;	
     			 RTAXON b = new RTAXON();
     			 Schema = b.Table_List(con,SchemaRecover);
     			 ontologie = Ontology.Inverse_filter(Schema);
     			 categorization_filter = Ontology.categorization_filter(Schema);
     			 } catch (Exception e) {			
     			System.out.println(e + " abass");
     			request.setAttribute("Erreur","Les données sont incorrectes");
     			//
     			//this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
     		}
     		
     		request.setAttribute("categorization",categorization_filter);

     		this.getServletContext().getRequestDispatcher( "/WEB-INF/Catego.jsp" ).forward( request, response );
    	}
    	
        
		if(action.equals("Catego")){
			if(categorization_filter.size()!=0){
				for(int i = 0;i<categorization_filter.size();i++){
					categorization_filter.get(i).setChosenColumn(request.getParameter( i+"_select" ));
					if(request.getParameter( i+"_func" ).equals("oneOf")){
						categorization_filter.get(i).setFunction(Categorization_Functions.OneOf);
					}else{
						categorization_filter.get(i).setFunction(Categorization_Functions.SubClass);
					}
				}
				Ontology.Categorisation(categorization_filter);
			}			
			
			File file = new File ("C:/Users/Oussama/workspace/"+databaseName+"-Ontology.owl.xml");
			 FileOutputStream fichierSortie = new FileOutputStream (file);
			 ontologie.write(fichierSortie);
			 fichierSortie.close();
			 int length = 0;
			 String filePath =  "C:/Users/Oussama/workspace/"+databaseName+"-Ontology.owl.xml";
		        ServletOutputStream outStream = response.getOutputStream();
		        response.setContentType("text/html");
		        response.setContentLength((int) file.length());
		        String fileName = (new File(filePath)).getName();
		        response.setHeader("Content-Disposition", "attachment; filename=\""
		                + fileName + "\"");
		 
		        byte[] byteBuffer = new byte[BUFSIZE];
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		 
		        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
		            outStream.write(byteBuffer, 0, length);
		        }
		 
		        in.close();
		        outStream.close();
			
		}
    }
}