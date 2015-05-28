import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Class handles each column of the connect 4 board 
 */
public class ColumnButton extends JLayeredPane{
	private Image backgroundImage;
	private Image player1;
	private Image player2;
	private Image player0;
	private Image winner1;
	private Image winner2;
	private ArrayList<Token> tokens;
	
	/**
	 * Constructor sets border of columns and initialises other fields of ColumnButton
	 * @throws IOException
	 */
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
		     winner2 = ImageIO.read(new File("assets/token1Highlight.png"));
		     winner1 = ImageIO.read(new File("assets/token2Highlight.png"));
		} else if (theme.equals("2016 Election")){
			player1 = ImageIO.read(new File("assets/abbott1.png"));
	        player2 = ImageIO.read(new File("assets/shorten2.png"));
	        winner2 = ImageIO.read(new File("assets/abbott1Highlight.png"));
		    winner1 = ImageIO.read(new File("assets/shorten2Highlight.png"));
		} else if (theme.equals("Wes Anderson")){
			player1 = ImageIO.read(new File("assets/lobby1.png"));
	        player2 = ImageIO.read(new File("assets/racoon2.png"));
	        winner2 = ImageIO.read(new File("assets/lobby1Highlight.png"));
		    winner1 = ImageIO.read(new File("assets/racoon2Highlight.png"));
		} else if (theme.equals("Retro")){
			player1 = ImageIO.read(new File("assets/retro1.png"));
	        player2 = ImageIO.read(new File("assets/retro2.png"));
	        winner2 = ImageIO.read(new File("assets/retro1Highlight.png"));
		    winner1 = ImageIO.read(new File("assets/retro2Highlight.png"));
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
	 * @param playerID ID of current player
	 * @param rowNum number of the row
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
	 * Highlights the winning sequence of tokens
	 * @param currentPlayer player who won
	 * @param rowNum row where token is located
	 */
	public void highlightToken(int currentPlayer, int rowNum){		
		if(currentPlayer == 0){
			tokens.get(rowNum).setBackgroundImg(winner1);
		}else{
			tokens.get(rowNum).setBackgroundImg(winner2);
		}
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
	
	/**
	 * @return Dimension of column button
	 */
	public Dimension getPreferredSize() {
        return new Dimension(90,480);
    }
	
	/**
	 * Paint background image onto token
	 */
	public void paintComponent(Graphics g) {   
        
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
	
	/**
	 * Sets column background
	 * @param fileName name of file
	 * @throws IOException
	 */
	public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
	}
}
