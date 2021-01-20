package domain;

public enum Categorization_Functions {
	SubClass("SubClass"),
	OneOf("OneOf");
	
	private String Cat_Func;

	private Categorization_Functions(String cat_Func) {
		Cat_Func = cat_Func;
	}

	public String getCat_Func() {
		return Cat_Func;
	}
}
