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


public class StatusBar extends JPanel{
	private JLabel turn;
	private JLabel turnAnswer;
	private ImageIcon player1Icon;
	private ImageIcon player2Icon;
	private Color backgroundShade;
	private Color text;
	
	/**
	 * 
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
//		revealStatus();
		
	}
	
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
	 * @precondition Values should be according to the below when
	 * passed in
	 * PLAYER 1 IS ONE
	 * PLAYER 2 IS TWO
	 * @param player
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
	
	
	public void revealStatus() {
		setBackground(text);
		turn.setForeground(backgroundShade);
		turnAnswer.setIcon(player1Icon);
	}
	
	public void hideStatus() {
		setBackground(backgroundShade);
		turnAnswer.setIcon(null);
	}
	
}