import java.awt.*;
import javax.swing.*;

/**
 * Representation of Board tokens
 */
public class Token extends JPanel {
	private Image backgroundImage;
	
	/**
	 * @return dimension of token image
	 */
	public Dimension getPreferredSize() {
        return new Dimension(80,80);
    }
	
	/**
	 * Paint background image onto token
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
	
	/**
	 * Sets token image
	 * @param image
	 */
	public void setBackgroundImg(Image image){
        backgroundImage = image;
	}
}
