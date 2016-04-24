package model;

public class Address {
	private int id;
	private String type;
	private String street;
	private String apartment;
	private String city;
	private String state;
	private int postal;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public int getPostal() {
		return postal;
	}
	public void setPostal(int postal) {
		this.postal = postal;
	}

	@Override
	public String toString() {
		return 	"address id: " + id + "\n" +  
				street + "\n" +
				"apt: " + apartment + "\n" +
				"city: " + city + ", " + state + "  " + postal + "\n\n";
	}
}
