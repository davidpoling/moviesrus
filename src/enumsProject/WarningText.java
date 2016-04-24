package enumsProject;

public enum WarningText {
	DATABASE("Database"),
	DB_CONN("Connection Failed"),
	INPUT("Input"),
	INPUT_NUM("Input numbers only"),
	QUERY_FAILED("Current query failed");
	
	private String warningText;

	private WarningText(String warningText) {
		this.warningText = warningText;
	}

	public String getText() {
		return warningText;
	}
}
