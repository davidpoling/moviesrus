package model;

public class Human {
	private int id;
	private String firstName;
	private String surName;

	public Human() {
		super();
	}
	
	public Human(int id, String firstName, String surName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Override
	public String toString() {
		return 	" human id: " + id + "\n" + 
				surName + ", " + firstName + "\n";
	}
	
	public String toViewString() {
		return surName + ", " + firstName;
	}
}
