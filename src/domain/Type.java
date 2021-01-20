/***********************************************************************
 * Module:  Type.java
 * Author:  Reda
 * Purpose: classe d'énumeration définissant les types existant dans une BD
 ***********************************************************************/
package domain;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;

public enum Type {
	TINYINT("TINYINT"),
	SMALLINT("SMALLINT"),
	MEDIUMINT("MEDIUMINT"),
	INT("INT"),
	INTEGER("INTEGER"),
	BIGINT("BIGINT"),
	FLOAT("FLOAT"),
	DOUBLE("DOUBLE"),
	REAL("REAL"),
	DECIMAL("DECIMAL"),
	NUMERIC("NUMERIC"),
	DATE("DATE"),
	DATETIME("DATETIME"),
	TIMESTAMP("TIMESTAMP"),
	TIME("TIME"),
	YEAR("YEAR"),
	CHAR("CHAR"),
	BIT("BIT"),
	BOOLEAN("BOOLEAN"),
	VARCHAR("VARCHAR"),
	TINYBLOB("TINYBLOB"),
	TINYTEXT("TINYTEXT"),
	BLOB("BLOB"),
	TEXT("TEXT"),
	MEDIUMBLOB("MEDIUMBLOB"),
	MEDIUMTEXT("MEDIUMTEXT"),
	LONGBLOB("LONGBLOB"),
	LONGTEXT("LONGTEXT"),
	SET("SET"),
	ENUM("ENUM"),
	SERIAL("SERIAL"),
	BINARY("BINARY"),
	VARBINARY("VARBINARY"),
	GEOMETRY("GEOMETRY"),
	POINT("POINT"),
	LINESTRING("LINESTRING"),
	POLYGON("POLYGON"),
	MULTIPOINT("MULTIPOINT"),
	MULTILINESTRING("MULTILINESTRING"),
	MULTIPOLYGON("MULTIPOLYGON"),
	GEOMETRYCOLLECTION("GEOMETRYCOLLECTION");
	
	private String stringType;
	
	/**
	 * Constructeur
	 * @param st : nom du type 
	 */
	private Type(String st){
		this.stringType = st;
	}

	public String getStringType() {
		return stringType;
	}
	
	public Resource getXSDType(){
		switch (this) {
			case TINYINT:
			case SMALLINT:
				return XSD.xshort;
			case MEDIUMINT:
			case INT:
			case INTEGER:
			case BIGINT:
				return XSD.integer;
			case REAL:
			case FLOAT:
				return XSD.xfloat;
			case DECIMAL:
			case NUMERIC:
				return XSD.decimal;
			case DATE:
			case TIMESTAMP:
				return XSD.date;
			case DATETIME:
				return XSD.dateTime;
			case TIME:
				return XSD.time;
			case BIT:
				return XSD.xbyte;
			case YEAR:
				return XSD.gYear;
			case BOOLEAN:
				return XSD.xboolean;
			default:
				return XSD.xstring;
		}
	}
}