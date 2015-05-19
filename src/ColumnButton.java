import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ColumnButton extends JLayeredPane{
	private Image backgroundImage;
	private Image player1;
	private Image player2;
	private Image player0;
	private ArrayList<Token> tokens;
	
	
	public ColumnButton() throws IOException{
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(Board.NUM_ROWS,1));
        
        tokens = new ArrayList<Token>();
        
        for(int i = 0; i < Board.NUM_ROWS; i++){
        	Token t = new Token();
        	tokens.add(t);
        }
       
        player1 = ImageIO.read(new File("token1.png"));
        player2 = ImageIO.read(new File("token2.png"));
        player0 = ImageIO.read(new File("tokenBlank.png"));        
        
        generateTokens();
//		repaint();
	}
	
	/**
	 * Adds and sets the initial states of all tokens
	 */
	public void generateTokens(){
		for(Token t : tokens){
			add(t, 0);
			t.setBackgroundImg(player0);
			t.repaint();
		}
	}
	/**
	 * Display the token at the given row, with the token of playerID
	 * @param playerID	ID of current player
	 * @param rowNum
	 */
	public void displayToken(int playerID, int rowNum){
		if(playerID == 1){
			tokens.get(rowNum).setBackgroundImg(player1);
		}else{
			tokens.get(rowNum).setBackgroundImg(player2);
		}
		repaint();
	}
	
	/**
	 * Resets all tokens
	 */
	public void resetTokens(){
		for(Token t : tokens){
			t.setBackgroundImg(player0);
			repaint();
		}
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(90,480);
    }
	
	public void paintComponent(Graphics g) {
//        super.paintComponent(g);       
        
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
	
	public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
	}
}
