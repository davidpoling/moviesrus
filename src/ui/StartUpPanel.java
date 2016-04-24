package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartUpPanel extends JPanel{
	
	private BufferedImage image;
	
	public StartUpPanel(){
		
       try {                
           image = ImageIO.read(getClass().getClassLoader().getResource("resources/images/logo.png"));
        } catch (IOException ex) {
             // handle exception...
        }
       
       ImageIcon icon = new ImageIcon(image, "Movies n' Games-R-Us");
       JLabel logo = new JLabel(icon);
       this.add(logo);
       
	}
}
