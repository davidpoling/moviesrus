package enumsProject;

public enum LabelText {
	ACTOR("Actor"),
	ACTOR_DETAILS("Actor Details"),
	ADDRESS_TYPE("Address Type"),
	APPNAME("Movies-R-Us"),
	APTNUM("Apartment #:"),
	AWARDS("Awards"),
	BILLING("Billing"),
	BOTH("Both"),
	CITY("City:"),
	COUNT("0 "),
	DESCRIPTION("Description"),
	DIRECTOR("Director"),
	DIRECTOR_DETAILS("Director Details"),
	EMAIL("e-Mail Address:"),
	EMPTY(""),
	FIRSTNAME("First Name:"),
	GAMES("Games"),
	GENRE("Genre"),
	HONOR("Award Category"),
	INVENTORY("Inventory Type"),
	KEYWORDS("Keywords"),
	LASTNAME("Last Name:"),
	LEVELONE("1-Title"),
	LEVELTWO("2-Titles"),
	LEVELTHREE("3-Titles"),
	LIMIT("Limit to"),
	MEMBERS("Members"),
	MEMBERSHIP("Membership"),
	MOVIES("Movies"),
	NEEDSHIPPED("To be shipped..."),
	PASSWORD("Password"),
	PLATFORM("Console"),
	PREVIOUS("Last 24 hours"),
	RELEASEDATE("Release Date:"),
	RENTALTYPE("Rental type"),
	RENTED("Rented"),
	REQUIRED("Required Value"),
	RESULT("Search Returned: "),
	ROLE("Role:"),
	SEARCH("Search by"),
	SEARCH_RESULT(" Results"),
	SEQUEL("Sequel"),
	SEQUEL_TITLE("Sequel Title"),
	SEQUEL_VERSION("Sequel Version"),
	SHIPPING("Shipping"),
	STATE("State:"),
	STOCK("Stock:"),
	STREET("Street:"),
	SUBLEVEL("Subscription Level"),
	SUBTYPE("Subscription Type"),
	TITLE("Title"),
	TOPRENTALS("Top 10 Last Month"),
	UNRENTED("Unrented"),
	USERNAME("Member"),
	USERINFO("Member Information"),
	VERSION("Version"),
	WELCOME("Welcome back, "),
	ZIPCODE("Zip Code: ");
	
	private String labelText;

	private LabelText(String labelText) {
		this.labelText = labelText;
	}

	public String getLabelText() {
		return labelText;
	}
}
