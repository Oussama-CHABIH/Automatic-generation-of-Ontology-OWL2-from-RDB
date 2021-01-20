package domain;

import java.util.ArrayList;

public class Categorization {
	private String TableName;
	private String foreignKeyRefTable;
	private String ColumnName;
	private ArrayList<String> ColumnsOfRefTable;
	private String chosenColumn;
	
	private Categorization_Functions Function;
	
	public Categorization_Functions getFunction() {
		return Function;
	}
	public void setFunction(Categorization_Functions function) {
		this.Function = function;
	}
	
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		this.TableName = tableName;
	}
	
	public String getForeignKeyRefTable() {
		return foreignKeyRefTable;
	}
	public void setForeignKeyRefTable(String foreignKeyRefTable) {
		this.foreignKeyRefTable = foreignKeyRefTable;
	}
	
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		this.ColumnName = columnName;
	}
	
	public ArrayList<String> getColumnsOfRefTable() {
		return ColumnsOfRefTable;
	}
	public void setColumnsOfRefTable(ArrayList<String> ColumnsOfRefTable) {
		this.ColumnsOfRefTable = new ArrayList<String>(ColumnsOfRefTable);
	}
	
	public String getChosenColumn() {
		return chosenColumn;
	}


	public void setChosenColumn(String chosenColumn) {
		this.chosenColumn = chosenColumn;
	}


	public Categorization(String tableName, String foreignKeyRefTable, String columnName,
			ArrayList<String> columnsOfRefTable, String chosenColumn, Categorization_Functions function) {
		super();
		this.TableName = tableName;
		this.foreignKeyRefTable = foreignKeyRefTable;
		this.ColumnName = columnName;
		this.ColumnsOfRefTable = new ArrayList<String>(columnsOfRefTable);
		this.chosenColumn = chosenColumn;
		this.Function = function;
	}

}
