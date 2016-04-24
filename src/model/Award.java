package model;

public class Award {
	private int id;
	private String honor;
	private String category;

	public Award(String honor, String category) {
		super();
		this.id = 0;
		this.honor = honor;
		this.category = category;
	}
	
	public Award(int id, String honor, String category) {
		super();
		this.id = id;
		this.honor = honor;
		this.category = category;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getHonor() {
		return honor;
	}
	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return 	" Honor: " + honor + " Category: " + category + "\n";
	}
}
