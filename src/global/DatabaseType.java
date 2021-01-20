package global;

public enum DatabaseType {
	MySQL("mysql"),
	ORACLE("oracle"),
	SQLServer("sqlserver");
	
	private String DBMSName;

	private DatabaseType(String dBMSName) {
		DBMSName = dBMSName;
	}

	public String getDBMSName() {
		return DBMSName;
	}
}
