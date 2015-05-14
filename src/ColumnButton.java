import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ColumnButton extends JLayeredPane{
	private Image backgroundImage;
	private ImageIcon player1;
	private ImageIcon player2;
	private ImageIcon player0;
	private ArrayList<JLabel> tokens;
	
	
	public ColumnButton(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(6,1));
        
        tokens = new ArrayList<JLabel>();
        
        for(int i = 0; i < 6; i++){
        	JLabel t = new JLabel();
        	tokens.add(t);
        }
        
       
        player1 = new ImageIcon("token1.png");
        player2 = new ImageIcon("token2.png");
        player0 = new ImageIcon("tokenBlank.png");
        
        generateTokens();
//		repaint();
	}
	
	/**
	 * Adds and sets the initial states of all tokens
	 */
	public void generateTokens(){
		for(JLabel t : tokens){
			add(t, 1);
			t.setIcon(player0);
		}
	}
	/**
	 * Display the token at the given row, with the token of playerID
	 * @param playerID	ID of current player
	 * @param rowNum
	 */
	public void displayToken(int playerID, int rowNum){
		rowNum++;
		if(rowNum == 6) rowNum = 0;
		if(playerID == 1){
			tokens.get(rowNum).setIcon(player1);
		}else{
			tokens.get(rowNum).setIcon(player2);
		}
	}
	
	/**
	 * Resets all tokens
	 */
	public void resetTokens(){
		for(JLabel t : tokens){
			t.setIcon(player0);
			repaint();
		}
//		for(int i = 0; i < 6; i ++){
//			tokens.get(i).setIcon(player0);
//		}
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(90,480);
    }
	
	public void paintComponent(Graphics g) {
//        super.paintComponent(g);       
        
    	g.drawImage(backgroundImage, 0, 0, null);
    }
	
	public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
	}
}
