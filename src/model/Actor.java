package model;

public class Actor extends Human{
	private String role = "";
	
	public Actor(){
		super();
	}

	public Actor(String firstName, String surname) {
		super(0, firstName, surname);
	}
	
	public Actor(String firstName, String surname, String role) {
		super(0, firstName, surname);
		this.role = role;
	}

	public int getId() {
		return super.getId();
	}
	public void setId(int id) {
		super.setId(id);
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Actor id: " + getId() + " " + super.toViewString() + " as " + role;
	}
}
