package model;

public class Genre {
	private String title = "";

	public Genre() {
	}
	
	public Genre(String title) {
		super();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return 	" Genre: " + title + "\n";
	}
}
