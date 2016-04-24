package enumsProject;

public enum ToolTipText {
	ADD_ACTOR("Add Actor to Cast"),
	ADD_AWARD("Add Award to Title"),
	ADD_CATEGORY("Add Award Category"),
	ADD_CAST("Add person to cast"),
	ADD_DIRECTOR("Add Director to Title"),
	ADD_GENRE("Select/Add Genre"),
	ADD_INVENTORY("Add to Inventory"),
	ADD_PLATFORM("Select Player/Console"),
	ADD_ROLE("Add actor movie role"),
	ADD_SEQUEL("Select/Add Sequel Title"),
	ADD_TITLE("Select/Add Title"),
	ADD_USER("Add User"),
	ADD_VERSION("Select Player/Console Version"),
	ADDRESS_BILLING("Billing address only"),
	ADDRESS_SHIPPING("Shipping address only"),
	ADDRESS_BOTH("Both billing and shipping address"),
	APT_NUM("Enter apartment number"),
	AWARD("Award winning movies or games"),
	BOTH("Both Movies and Games"),
	CITY("Enter name of City"),
	CLEAR("Clear all search fields"),
	CURRENT("Inventory currently rented"),
	DELETE("Delete Selected"),
	EMAIL("Enter email address"),
	FIRST_NAME("Enter First Name"),
	GAMES("Games Only"),
	HOME("Return to Main Screen"),
	LOGIN("User Login"),
	MEMBERS("Show list of current members"),
	MOVIE("Movies Only"),
	NEEDSHIPPED("Rentals to be shipped"),
	PASSWORD("Enter password (8 character minimum)"),
	PAST24("Past 24 hour rentals"),
	POSTAL("Enter Postal/Zip code"),
	RENT("Rent Selected"),
	RENTED("Previous Rentals"),
	RESET("Clear button selections"),
	RETURN("Return Selected to Inventory"),
	SAVE("Save settings"),
	SAVE_ADD("Save and Add Another"),
	SAVE_MEMBER("Save and Return to Previous"),
	SAVE_TITLE("Save Title"),
	SEARCH("Search for Titles"),
	SEARCH_ACTOR("Search ACTOR with name containing"),
	SEARCH_DIRECTOR("Search DIRECTOR with name containing"),
	SEARCH_GENRE("Search for genre containing"),
	SEARCH_KEYWORD("Search all possible containing"),
	SEARCH_PLATFORM("Search for platform containing"),
	SEARCH_VERSION("Search for platform version"),
	SETTINGS("User Settings/Logout"),
	SEQUEL("Titles that are sequels"),
	SHIPPING("Create Shipping List"),
	STATE("Select State"),
	STOCK("Units in stock"),
	STREET("Enter street number and name"),
	SURNAME("Enter Surname/Last Name"),
	TITLE_ONE("Rent One(1) Title at a time"),
	TITLE_TWO("Rent Two(2) Titles at a time"),
	TITLE_THREE("Rent Three(3) Titles at a time"),
	TOP10("Last Month Top 10 Rentals"),
	USERNAME("Enter email address"),
	UNRENTED("Not previously rented");
	
	private String toolTipText;

	private ToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public String getToolTipText() {
		return toolTipText;
	}
}
