package global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import org.apache.log4j.Logger;

import domain.Attribute;
import domain.Table;
import domain.Type;

/**
 * @author Reda
 * 
 */
public class SchemaRecoverer {
	
	//static Logger logger = Logger.getLogger(SchemaRecoverer.class);

	private String host;
	private String databaseName;
	private String login;
	private String password;
	private DatabaseType dbType;
	private ArrayList<String> tableTypes;
	
	/**
	 * Constructeur
	 */
	public SchemaRecoverer(String host, String databaseName, String login,
			String password, DatabaseType dbType, ArrayList<String> tableTypes) {
		super();
		this.host = host;
		this.databaseName = databaseName;
		this.login = login;
		this.password = password;
		this.dbType = dbType;
		this.tableTypes = tableTypes;
	}

	public ArrayList<String> getTableTypes() {
		return tableTypes;
	}

	public void setTableTypes(ArrayList<String> tableTypes) {
		this.tableTypes = tableTypes;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DatabaseType getDbType() {
		return dbType;
	}

	public void setDbType(DatabaseType dbType) {
		this.dbType = dbType;
	}
	
	public Connection connect() throws ClassNotFoundException, SQLException{
			// choix du Driver selon le type du SGBD
			switch (this.getDbType()) {
			case MySQL:
				Class.forName("com.mysql.jdbc.Driver");
				break;

			case ORACLE:
				Class.forName("oracle.jdbc.driver.OracleDriver");
				break;

			case SQLServer:
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				break;
			}
			
			// Connexion à la BD
			return DriverManager.getConnection("jdbc:" + this.dbType.getDBMSName() + "://" + this.host + "/" + this.databaseName, this.login, this.password);
	}

	/**
	 * Méthode permetant de recourvir le schéma de la BD à partir des attributs de la classe 
	 * @return liste d'objets de type Domain.Table
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public ArrayList<Table> getSchema() throws ClassNotFoundException, SQLException {
			Connection cnx = this.connect();
			//logger.info("Database Name : " + this.getDatabaseName());
			
			/**
			 *  Récuperation du schéma
			 *  On commence par récuperer la liste des tables
			 *  Pour chaque table on récupère la liste des attributs
			 *  Pour chaque attribut, on doit savoir si :
			 *  - il a une contrainte UNIQUE
			 *  - il a une contrainte NOT NULL
			 *  - il a une contrainte PK
			 *  - il est une clé étrangère, et chercher la table à laquelle il réfère
			 */
			ArrayList<Table> tables = new ArrayList<Table>();
			ResultSet rs =cnx.getMetaData().getTables(null, null, null, this.tableTypes.toArray(new String[tableTypes.size()]));
			/*int attributesCount = 0;
			int foreignKeysCount = 0;
			int primaryKeysCount = 0;
			int unikAttributesCount = 0;
			int notNullAttributesCount = 0;*/

			while (rs.next()) {
				if (tableTypes.contains(rs.getString(4))){
					String tableName = rs.getString(3);
					ArrayList<Attribute> attributes = new ArrayList<Attribute>();
					
					// récuperation de la liste compléte des attributs
					ResultSet rsCols = cnx.getMetaData().getColumns(null, null, tableName, null);
					while (rsCols.next()) {
						//attributesCount++;
						
						// Test si l'attribut est NOT NULL ou pas
						int isNullInt = rsCols.getInt(11);
						Boolean isNullBool = false;
						if (isNullInt == 1){
					//		notNullAttributesCount++;
							isNullBool = true;
						}
						
						// récuperation de la taille de l'attribut
						int size = -1;
						try {
							size = Integer.parseInt(rsCols.getString(7));
						} catch (Exception e) {}
						
						// récuperation du type de la colonne
						StringTokenizer st = new StringTokenizer(rsCols.getString(6), " ");
						
						// Ajout de l'attribut à la liste des attributs de la table en cours
						attributes.add(new Attribute(rsCols.getString(4), size, false, isNullBool, false, Type.valueOf(st.nextToken()), false, "", rsCols.getString(13), "", 0.0, 0.0));
					}
					
					/** 
					 * Tests des clé primaires
					 * Parcours de la liste des attributs de la table en cours, 
					 * Pour chacun des attributs vérifier s'il est une clé primaire
					 * Si oui, on modifie l'attribut en changeant la valeur du flag "IsPrimaryKey" vers TRUE
					 */
					ResultSet rsPK = cnx.getMetaData().getPrimaryKeys(null, null, tableName);
					while (rsPK.next()) {
				//		primaryKeysCount++;
						for (Attribute attribute : attributes) {
							if (attribute.getName().equals(rsPK.getString(4)))
								attribute.setIsPrimaryKey(true);
						}
					}
					
					/**
					 * Tests sur l'existance de la contrainte UNIQUE
					 * Parcours de la liste des attributs de la table en cours,
					 * Pour chacun des attributs vérifier s'il est NON-UNIQUE ou non
					 * Si oui, on modifie l'attribut en changeant la valeur du flag "IsUnique" vers TRUE
					 */
					ResultSet rsUnik = cnx.getMetaData().getIndexInfo(null, null, tableName, true, false);
					while (rsUnik.next()) {
				//		unikAttributesCount++;
						for (Attribute attribute : attributes) {
							if (attribute.getName().equals(rsUnik.getString(9)))
								attribute.setIsUnique(!Boolean.parseBoolean(rsUnik.getString(4)));
						}
					}
					
					/**
					 * Tests sur l'existance de Foreign Keys
					 * Parcours de la liste des attributs de la table en cours,
					 * Pour chacun des attributs vérifier s'il est une clé étrangère
					 * Si oui, remplacer l'Objet de l'attribut en cours par un objet de type ForeignKey
					 */
					ResultSet rsFK = cnx.getMetaData().getImportedKeys(null, null, tableName);
					while (rsFK.next()) {
				//		foreignKeysCount++;
						for (Attribute attribute : attributes) {
							if (attribute.getName().equals(rsFK.getString(8))){
								attribute.setIsForeignKey(true);
								attribute.setForeignKeyRefTable(rsFK.getString(3));
								//logger.info(rsFK.getString(8) + " : " + tableName + " --> " + rsFK.getString(3) + " / " + rsFK.getString(12));
								attribute.setRoleName(rsFK.getString(12));
								//attribute = new ForeignKey(attribute, rsFK.getString(3)); // utilisation d'un String pour la table de référence de la FK
								//attributes.add(new ForeignKey(attribute, rsFK.getString(3)));
								//fks.add(new ForeignKey(attribute, rsFK.getString(3)));
							}
						}
					}
					
					// Ajout à la liste des tables
					tables.add(new Table(tableName, attributes));
				}
			}
			
			/*
			 * Statistiques sur le schéma de la BD
			 */
			/*logger.info("Tables count: " + tables.size());
			logger.info("Attributes count : " + attributesCount);
			logger.info("Foreign Keys count : " + foreignKeysCount);
			logger.info("Primary Keys count : " + primaryKeysCount);
			logger.info("Unique constraites count : " + unikAttributesCount);
			logger.info("Not Null constraites count : " + notNullAttributesCount);*/
			return tables;
	}
}
