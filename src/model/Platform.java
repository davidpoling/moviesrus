package model;

public class Platform {
	private String console = "";
	private String version = "";

	public Platform() {

	}
	
	public Platform(String console, String version) {
		this.console = console;
		this.version = version;
	}

	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return 	"Console: " + console + " Version: " + version +"\n";
	}
}
