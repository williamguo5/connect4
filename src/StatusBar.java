import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The Status bar lets the user know who's 
 * turn is currently in play. It is hidden before
 * a game starts and only appears when a game is 
 * progress.
 */
public class StatusBar extends JPanel{
	private JLabel turn;
	private JLabel turnAnswer;
	private ImageIcon player1Icon;
	private ImageIcon player2Icon;
	private Color backgroundShade;
	private Color text;
	
	
	/**
	 * Constructor for the status bar
	 * @param isTwoPlayer
	 * @throws IOException
	 */
	public StatusBar() throws IOException {
		super();
		setLayout(new GridLayout(1,2));
		backgroundShade = new Color(233,232,207);
		text = new Color(122, 160, 170);
		
		setOpaque(true);
		setBackground(text);
		
		turn = new JLabel();
		turn.setFont(new Font("Myriad Pro", Font.BOLD, 17));
		turn.setPreferredSize(new Dimension(125,110));
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		
		turnAnswer = new JLabel();
		turnAnswer.setHorizontalAlignment(SwingConstants.CENTER);
		turnAnswer.setPreferredSize(new Dimension(125,110));
		
		player1Icon = new ImageIcon("assets/token1.png");
		player2Icon = new ImageIcon("assets/token2.png");
		Image image = player2Icon.getImage().getScaledInstance(90, 90, 0);
		player2Icon = new ImageIcon(image);
		
		hideStatus();
		add(turn);
		add(turnAnswer);
	}
	
	/**
	 * Changes the current GUI theme. Colours the 
	 * status bar background and sets the token
	 * shown in status bar to correct tokens
	 * @param theme string. If will set to 
	 * retro if invalid string given
	 */
	public void setThemedIcon(String theme) {
		
		ImageIcon token1;
		ImageIcon token2;
		Image toAdd;
		if(theme.equals("Classic")) {
			token1 = new ImageIcon("assets/token1.png");
			token2 = new ImageIcon("assets/token2.png");
			/*player1Icon = new ImageIcon("token1.png");
			player2Icon = new ImageIcon("token2.png");
			Image image = player2Icon.getImage().getScaledInstance(90, 90, 0);
			player2Icon = new ImageIcon(image);*/
		} else if (theme.equals("2016 Election")) {
			token1 = new ImageIcon("assets/abbott1.png");
			token2 = new ImageIcon("assets/shorten2.png");
		} else if (theme.equals("Wes Anderson")) {
			token1 = new ImageIcon("assets/lobby1.png");
			token2 = new ImageIcon("assets/racoon2.png");
		} else {
			token1 = new ImageIcon("assets/retro1.png");
			token2 = new ImageIcon("assets/retro2.png");
		}
		toAdd = token1.getImage().getScaledInstance(90, 90, 0);
		player1Icon = new ImageIcon(toAdd);
		toAdd = token2.getImage().getScaledInstance(90, 90, 0);
		player2Icon = new ImageIcon(toAdd);
	}
	
	public void setBackgroundShade(Color newShade) {
		backgroundShade = newShade;
	}
	
	/**
	 * Do not use this function. Use Next Player in SideBar
	 * @param pass in either integer 1 or integer 2 for player 1 or 2
	 * @param type refers to whether it is a human vs human 
	 * game or human vs computer game. Type 0 refers to human 
	 * vs human and Type 1, 2... refer to Ai opponent 
	 */
	public void setPlayer(int player, int type) {
		if(type == 0) {
			if(player == 1) {
				
				turn.setText("<html>Player 2<br> Turn</html>");
				turnAnswer.setIcon(player1Icon);
			} else {
				turn.setText("<html>Player 1<br>Turn</html>");
				turnAnswer.setIcon(player2Icon);
			}
		} else {
			if(player == 1) {
				turn.setText("<html>Opponent's<br> Turn</html>");
				
				turnAnswer.setIcon(player1Icon);
			} else {
				turn.setText("Your Turn");
				turnAnswer.setIcon(player2Icon);
			}
		}
	}
	
	
	/**
	 * Reveals status bar to player view
	 */
	public void revealStatus() {
		setBackground(text);
		turn.setForeground(backgroundShade);
		turnAnswer.setIcon(player1Icon);
	}
	
	/**
	 * Hides the status bar from player view
	 */
	public void hideStatus() {
		setBackground(backgroundShade);
		turnAnswer.setIcon(null);
	}
	
}