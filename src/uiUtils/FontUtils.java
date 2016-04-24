package uiUtils;

import java.awt.Font;

public class FontUtils {
	public static Font createAppFont(){
		Font font = new Font("Arial", Font.ITALIC, 24);
		return font;
	}
	public static Font createHeadingFont(){
		Font font = new Font("Arial", Font.BOLD, 18);
		return font;
	}
	
	public static Font createMainFont(){
		Font font = new Font("Arial", Font.PLAIN, 14);
		return font;
	}
	
	public static Font createButtonFont(){
		Font font = new Font("Arial", Font.BOLD, 16);
		return font;
	}
}
