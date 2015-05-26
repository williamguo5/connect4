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
       
        player1 = ImageIO.read(new File("assets/token1.png"));
        player2 = ImageIO.read(new File("assets/token2.png"));
        player0 = ImageIO.read(new File("assets/tokenBlank.png"));        
        
        generateTokens();
//		repaint();
	}
	
	/**
	 * Sets the type of token to be dropped in the column
	 * according to the theme
	 * @param current theme from the setting (String)
	 * @throws IOException
	 */
	public void setToken(String theme) throws IOException {
		if(theme.equals("Classic")) {
			 player1 = ImageIO.read(new File("assets/token1.png"));
		     player2 = ImageIO.read(new File("assets/token2.png"));
		} else if (theme.equals("2016 Election")){
			player1 = ImageIO.read(new File("assets/abbott1.png"));
	        player2 = ImageIO.read(new File("assets/shorten2.png"));
		} else if (theme.equals("Wes Anderson")){
			player1 = ImageIO.read(new File("assets/lobby1.png"));
	        player2 = ImageIO.read(new File("assets/racoon2.png"));
		} else if (theme.equals("Retro")){
			player1 = ImageIO.read(new File("assets/retro1.png"));
	        player2 = ImageIO.read(new File("assets/retro2.png"));
		}
	};
	
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
