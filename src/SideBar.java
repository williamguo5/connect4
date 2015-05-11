import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class SideBar extends JPanel {
	
	private Image backgroundImage;

	
	
	
	/**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(250,550);
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
    	g.drawImage(backgroundImage, 0, 0, null);
    }
    
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
      }
}
