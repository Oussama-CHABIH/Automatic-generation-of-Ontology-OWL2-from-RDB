/***********************************************************************
 * Module:  Ontology.java
 * Author:  Oussama
 * Purpose: Generation of ontology
 ***********************************************************************/
package global;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;

import org.apache.jena.ontology.InverseFunctionalProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.EnumeratedClass;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.XSD;


import domain.Attribute;
import domain.Categorization;
import domain.Categorization_Functions;
import domain.Table;

public class Ontology {
	public static FileOutputStream fichierSortie = null;
	public static Connection con ;
    public static String namespace = "http://CHBIHI_CHABIH.com#";
    public static OntModel ontologie = ModelFactory.createOntologyModel();    
    public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Méthode permetant de générer l'inversement fonctionel à partir d'une Table d'attributs Table.Domain	
	 * @return l'ontologie d'inversement fonctionnel OntModel
	 */
	
	public static OntModel InverseFunctionalProperty(Table inversFilter) {
			try{		    
				OntClass Classe = ontologie.createClass (namespace + inversFilter.getName());
				String s_PrimareyKey = "";
				String s_Columns = "";
				int Nb_PrimaryKey = 0 ;
				InverseFunctionalProperty Colum = null;
				for(int i = 0 ;i<inversFilter.getAttributes().size();i++){					
				    if(inversFilter.getAttributes().get(i).getIsPrimaryKey()){
				    	s_PrimareyKey = inversFilter.getAttributes().get(i).getName() +", "+s_PrimareyKey;
				    	Nb_PrimaryKey++;
				    }
				}
				for(int i = 0 ;i<inversFilter.getAttributes().size();i++){ 
					if(!inversFilter.getAttributes().get(i).getIsPrimaryKey()){
						if(inversFilter.getAttributes().get(i).getName().equals(inversFilter.getName())){
							Colum = ontologie.createInverseFunctionalProperty (namespace +  inversFilter.getAttributes().get(i).getName()+"_col");
						}else{
					    	Colum = ontologie.createInverseFunctionalProperty (namespace +  inversFilter.getAttributes().get(i).getName());
						}
					    Colum.setDomain (Classe);
					    if(inversFilter.getAttributes().get(i).getType().toString()=="VARCHAR" || inversFilter.getAttributes().get(i).getType().toString()=="CHAR" || inversFilter.getAttributes().get(i).getType().toString()=="LONGVARCHAR"){
					    	Colum.setRange (XSD.xstring);
					    }else{
					    	Colum.setRange (XSD.xint);
					    }
					    Colum.asResource();
					    Colum.addProperty(RDF.type, OWL2.DatatypeProperty);
					    s_Columns = s_PrimareyKey+inversFilter.getAttributes().get(i).getName();
					    ResultSet enregistrement = Enregistrement(inversFilter.getName(), s_Columns);
						while(enregistrement.next()){
							if(Nb_PrimaryKey>1){
								s_Columns = enregistrement.getString(1);
								for(int j = 0 ;j<Nb_PrimaryKey;j++){
									s_Columns = s_Columns+"_"+ enregistrement.getString(j+1).toString();
								}
							}else{
								s_Columns = inversFilter.getName()+"_"+enregistrement.getString(1);								
							}							
							Individual instance = Classe.createIndividual( namespace + s_Columns);
							instance.setRDFType(OWL2.NamedIndividual);
							instance.addProperty(Colum,enregistrement.getString(Nb_PrimaryKey+1));											
						}	
					}				    
				}	
			    	
				return ontologie;	
			}
			catch (Exception e) {
	            System.out.println(e + " InverseFunctionalProperty");
	            return null;
	        }
		
	}
	public static OntModel OneOf( Categorization categorization_filter ) {
		try{
			
			OntClass Column_Class = ontologie.createClass (namespace +  categorization_filter.getForeignKeyRefTable());
	    	EnumeratedClass OneOf=ontologie.createEnumeratedClass( namespace + categorization_filter.getTableName(), null );
		    ResultSet enregistrement = Enregistrement(categorization_filter.getForeignKeyRefTable(), categorization_filter.getChosenColumn());
		    ObjectProperty ObjectProperty = ontologie.createObjectProperty(namespace + categorization_filter.getColumnName());
		    ObjectProperty.addDomain(OneOf);
		    ObjectProperty.addRange(Column_Class);
		    while(enregistrement.next()){
		    	OneOf.addOneOf( OneOf.createIndividual( namespace + enregistrement.getString(1)));		    	
		    }
		    return ontologie;
		   	}
			catch (Exception e) {
	            System.out.println(e + " OneOf");
	            return null;
	        }
		
	}
	public static OntModel SubClass(Categorization categorization_filter) {
		try{
			OntClass mere = ontologie.createClass (namespace +  categorization_filter.getTableName());
			
		    ResultSet enregistrement = Enregistrement(categorization_filter.getForeignKeyRefTable(), categorization_filter.getChosenColumn());
		    ArrayList<OntClass> listeClasses = new ArrayList<OntClass>();
		    while(enregistrement.next()){
				 OntClass fille = ontologie.createClass(namespace + enregistrement.getString(1));
				 mere.addSubClass(fille);
				 if(listeClasses.size()==0){
					 listeClasses.add(fille);
				 }else{
					 int e = 0;
					 while (e<listeClasses.size()&& listeClasses.get(e)!= fille){
						 e++;
					 }
					 if(e==listeClasses.size()){
						 listeClasses.add(fille);
						 for(int i =1 ;i<listeClasses.size();i++){
							 listeClasses.get(listeClasses.size()-1).addDisjointWith( listeClasses.get(i-1));
						 }
					 }
				 }
			}	
		    return ontologie;
		    }
			catch (Exception e) {
	            System.out.println(e + " SubClass");
	            return null;
	        }		
	}
	public static ResultSet Enregistrement(String TableName, String ColumnName) throws SQLException{		
		Statement stat = con.createStatement();
	    ResultSet enregistrement = stat.executeQuery("select "+ ColumnName +" from "+TableName);
	    return enregistrement;
	}
	public static void Resultat_Owl (OntModel ontologie) throws FileNotFoundException{
		ontologie.setNsPrefix("Entro", "http://CHBIHI_CHABIH.com#");
		fichierSortie = new FileOutputStream (new File ("ontology.owl.xml"));
		ontologie.write(fichierSortie, "RDF/XML");
	}
	public static void Categorisation(ArrayList<Categorization> categorization_filter) throws FileNotFoundException{		
		for (int i = 0 ;i<categorization_filter.size();i++){
			 	if(categorization_filter.get(i).getFunction().equals(Categorization_Functions.SubClass)){
		    		SubClass(categorization_filter.get(i));
		    	}else{
		    		OneOf(categorization_filter.get(i));
		    	}
	    }
		Resultat_Owl(ontologie);
	    sc.close();		
	}
	public static ArrayList<Categorization> categorization_filter(ArrayList<Table> schema) throws  SQLException{
		ArrayList<String> Columns = new ArrayList<String>();
		ArrayList<Categorization> categorization = new ArrayList<Categorization>();
		for(int i = 0;i<schema.size();i++){
			for (int j = 0 ;j<schema.get(i).getAttributes().size();j++){
				if((schema.get(i).getAttributes().get(j).getIsForeignKey()==true &&
					schema.get(i).getAttributes().get(j).getIsPrimaryKey()==false &&
					schema.get(i).getAttributes().get(j).getEntropy_percentage()<=20.0)&&
					schema.get(i).getAttributes().get(j).getType().toString()!="TIMESTAMP" && 
					schema.get(i).getAttributes().get(j).getType().toString()!="TIME" &&
					schema.get(i).getAttributes().get(j).toString()!="DATE")
				{ 
					Columns.clear();
					Statement stat = con.createStatement();
					ResultSet Column_choice = stat.executeQuery("SHOW COLUMNS from "+schema.get(i).getAttributes().get(j).getForeignKeyRefTable());
					while(Column_choice.next()){
						Columns.add(Column_choice.getString(1));
					}
					categorization.add(new Categorization(schema.get(i).getName(),schema.get(i).getAttributes().get(j).getForeignKeyRefTable(),
							schema.get(i).getAttributes().get(j).getName(),Columns,null,null));
				}							
				
			}
			
		}		
		return categorization;
	}
	public static OntModel Inverse_filter(ArrayList<Table> schema){
		ArrayList<Attribute> attribute = new ArrayList<Attribute>();
		ArrayList<Table> Inverse = new ArrayList<Table>();
		for(int i = 0;i<schema.size();i++){
			int Nb_PrimaryKey = 0 ;
			for (int j = 0 ;j<schema.get(i).getAttributes().size();j++){
				if(schema.get(i).getAttributes().get(j).getEntropy_percentage()==100.0 &&
					schema.get(i).getAttributes().get(j).getType().toString()!="TIMESTAMP" && 
					schema.get(i).getAttributes().get(j).getType().toString()!="TIME" &&
					schema.get(i).getAttributes().get(j).toString()!="DATE")
				{					
					attribute.add(schema.get(i).getAttributes().get(j));
					if (schema.get(i).getAttributes().get(j).getIsPrimaryKey()){
						Nb_PrimaryKey++;
					}
				}								
			}
			if(attribute.size()>Nb_PrimaryKey){
				Inverse.add(new Table(schema.get(i).getName(),attribute));
			}
			attribute= new ArrayList<Attribute>();
		}		
		for(int i = 0;i<Inverse.size();i++){
			ontologie= 	InverseFunctionalProperty( Inverse.get(i));
		}
		return ontologie;		
	}
}
