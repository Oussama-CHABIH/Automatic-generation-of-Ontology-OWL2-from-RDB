package global;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import domain.Table;

public class RTAXON {
	private BufferedWriter out;
	
	public ArrayList<Table> Table_List(Connection con, ArrayList<Table> Salam) {
		try{
			out =  new BufferedWriter (new FileWriter ("Résultat.txt"));
		for(int n = 0 ;n<Salam.size();n++){
			String s = "";
			int Nbr_primaryKey = 0;
			for (int j = 0 ;j<Salam.get(n).getAttributes().size();j++){
				if(Salam.get(n).getAttributes().get(j).getIsPrimaryKey()){
					s = s+ Salam.get(n).getAttributes().get(j).getName()+", ";
					Nbr_primaryKey++;
				}
			}
			out.newLine();out.write(" "+Salam.get(n).getName());
				s = s.substring(0, s.length()-2);	
			Double PrimaryKeyEntropy = this.Entropie(con, Salam.get(n).getName(),s, Nbr_primaryKey);			
			for (int j = 0 ;j<Salam.get(n).getAttributes().size();j++){	
				if(Salam.get(n).getAttributes().get(j).getIsPrimaryKey())
					Salam.get(n).getAttributes().get(j).setEntropy(PrimaryKeyEntropy);
				else
				Salam.get(n).getAttributes().get(j).setEntropy(Entropie(con, Salam.get(n).getName(),Salam.get(n).getAttributes().get(j).getName(), 1));
				Salam.get(n).getAttributes().get(j).setEntropy_percentage(Percentage(PrimaryKeyEntropy, Salam.get(n).getAttributes().get(j).getEntropy()));
				out.newLine();out.write(" "+Salam.get(n).getAttributes().get(j));
			}
			out.newLine();
		}
		out.close();
		return Salam;
		}
		catch (Exception e) {
            System.out.println(e + " Table");
            return null;
        }
	}
	private double Entropie(Connection con ,String TableName, String ColonnesName, int Nbr_colonnes){
		ArrayList<String> Colonnes_list = new ArrayList<String>();
		ArrayList<String> Nbr_ele_unique = new ArrayList<String>();
		Double Entro=0.0,cp=0.0;
		String s;
		int k = 0;
		try{
			Statement state = con.createStatement();
			ResultSet enregistrement = state.executeQuery("Select "+ColonnesName +" from " +TableName );
			while (enregistrement.next()){
				s="";
				for(int i =0;i<Nbr_colonnes;i++){
					if(enregistrement.getString(i+1)=="" || enregistrement.getString(i+1)==null){
						s = s+" walo walo walo";
					}else{
						 s = s+" "+enregistrement.getString(i+1);						 
					}
				}
				Colonnes_list.add(s);				
			}
			if (Colonnes_list.size()>1){
				for(int i =0 ;i<Colonnes_list.size();i++){
					while (k < Nbr_ele_unique.size() && !Nbr_ele_unique.get(k).equals(Colonnes_list.get(i))) {
	                    k++;
	                }
					if (k == Nbr_ele_unique.size()) {
						Nbr_ele_unique.add(Colonnes_list.get(i));
	                    for (int j = i; j < Colonnes_list.size(); j++) {
	                        if (Colonnes_list.get(i) == Colonnes_list.get(j)) {
	                            cp++;                            
	                        }
	                    }
	                    Double proba = cp / Colonnes_list.size();
	                    Entro = Entro - proba * Math.log(proba) / Math.log(Colonnes_list.size());        			
	                }
				k=0;
				cp=0.0;
				}
			}
			else{
				return 1.0;				
			}
		return Entro;	
		}
		catch (Exception e) {
            System.out.println(e + " Entropie");
            return 0;
        }		
	}
	private double Percentage(double PrimaryKeyEntropy, double ColumnEntropy){
		return ColumnEntropy*100/PrimaryKeyEntropy;
	}
}