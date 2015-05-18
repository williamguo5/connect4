import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class StatusBar extends JPanel {
	
	/**
	 * R: 233
	 * G: 232
	 * B: 207
	 * 
	 * Text RGB: 122, 160, 170
	 * Text RGB: 10, 121, 133
	 */
	
	private JLabel turnAnswer;
	private JLabel scoreAnswer;
	private JLabel movesAnswer;
	
	private ImageIcon yellow;
	private ImageIcon red;
	private Font style;
	private Color textGreen;
	private Color textBlue;
	
	private int player;	//PLAYER RED = 0?, PLAYER YELLOW = 1?
	private int numMoves;
	private int yellowScore;
	private int redScore;
	
	public StatusBar() throws IOException {
		setLayout(new GridLayout(3, 2));
		style = new Font("Myriad Pro", Font.PLAIN, 30);
		textGreen = new Color(10, 121, 133);
		textBlue = new Color(122, 160, 170);
		
		// initialize the fields
		yellow = new ImageIcon("token2.png");	
		red = new ImageIcon("token1.png");
		yellowScore = 0;
		redScore = 0;
		numMoves = 0;
		player = 0;
		
		// Initialize and add the panels
		initialize();
    }
	
	private void initialize() {		 
		
		// create the status labels
		JLabel currentTurn = new JLabel("Turn", SwingConstants.CENTER);
		JLabel moves = new JLabel("Moves", SwingConstants.CENTER);
		JLabel score = new JLabel("Score", SwingConstants.CENTER);
		
		turnAnswer = new JLabel();
		turnAnswer.setHorizontalAlignment(SwingConstants.CENTER);
		movesAnswer = new JLabel("", SwingConstants.CENTER);
		scoreAnswer = new JLabel("", SwingConstants.CENTER);
		
		// set style for labels
		currentTurn.setFont(style);
		currentTurn.setForeground(textGreen);
		moves.setFont(style);
		moves.setForeground(textGreen);
		score.setFont(style);
		score.setForeground(textGreen);
		movesAnswer.setFont(style);
		movesAnswer.setForeground(textBlue);
		scoreAnswer.setFont(style);
		scoreAnswer.setForeground(textBlue);
		
		//turnAnswer.setIcon(red);
		//movesAnswer.setText(Integer.toString(numMoves));
		newTurn();
		newRound(3); 
		scoreAnswer.setText(Integer.toString(yellowScore) + " : " + Integer.toString(redScore));
		
		// adds on the labels to statusMenu
		add(currentTurn);
		add(turnAnswer);
		add(moves);
		add(movesAnswer);
		add(score);
		add(scoreAnswer);
	}
	
	/**
	 * Check which player is which
	 * After a player has won the game
	 * moves = 0
	 * reset player 0
	 * @param player
	 */
	public void newRound(int winner) {
		if(winner == 0) {
			yellowScore++;
		} else if (winner == 1) {
			redScore++;
		}
		scoreAnswer.setText(Integer.toString(yellowScore) + " : " + Integer.toString(redScore));
	}
	
	/**
	 * 
	 */
	private void newTurn() {
		player++;
		player%=2; // CHECK THIS
		if(player == 1) {
			turnAnswer.setIcon(red);
		} else {
			turnAnswer.setIcon(yellow);
		}
		movesAnswer.setText(Integer.toString(numMoves));
		numMoves++;
	}
	/**
	 * public UPDATE STATUS BAR
	 * player++ % 2
	 * moves++
	 */
	
}