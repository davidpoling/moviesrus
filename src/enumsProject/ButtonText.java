package enumsProject;

public enum ButtonText {
	ADD_ACTOR("Add"),
	ADD_AWARD("Add"),
	ADD_CATEGORY("Add"),
	ADD_CAST("Add to Cast"),
	ADD_DIRECTOR("Add"),
	ADDINVENTORY("Add Inventory"),
	ADD_TITLE("Add Title"),
	ADD_USER("Add User"),
	CLEAR("Clear Fields"),
	DELETE("Delete"),
	HOME("Home"),
	LOGIN("Login"),
	LOGOUT("Logout"),
	RENT("Rent"),
	RESET("Reset Buttons"),
	RETURNED("Returned"),
	SAVE("Save and Exit"),
	SAVE_ADD("Save and Add"),
	SETTINGS("Settings"),
	SEARCH("Search..."),
	SHIP("Ship"),
	VIEW_AWARDS("Awards");
	
	private String buttonText;

	private ButtonText(String labelText) {
		this.buttonText = labelText;
	}

	public String getButtonText() {
		return buttonText;
	}
}
