package model;

public class Director extends Human{
	
	public Director(){
		super();
	}

	public Director(String firstName, String surname) {
		super(0, firstName, surname);
	}

	public int getId() {
		return super.getId();
	}
	public void setId(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return 	"Director id: " + getId() + " " + super.toViewString();
	}
}
