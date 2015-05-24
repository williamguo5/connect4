import java.awt.*;
import javax.swing.*;


public class Token extends JPanel {
	private Image backgroundImage;
	
	public Dimension getPreferredSize() {
        return new Dimension(80,80);
    }
	
	public void paintComponent(Graphics g) {
//        super.paintComponent(g);       
        
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
	
	public void setBackgroundImg(Image image){
        backgroundImage = image;
	}
}
