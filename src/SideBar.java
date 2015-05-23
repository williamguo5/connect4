import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * When a NEW GAME is created (After New Game Button):
 * 		Call gameSettings.revealStatus();
 * 		Call this.nextPlayer(0);
 * 
 * When a game ends:
 * 		Call gameSettings.hideStatus();
 * 
 * To Find out the Opponent Type for NEW GAME:
 * 		Call gameSettings.getOpponent();
 * 		Will give 0 - 3. 
 * 		0 meaning human
 * 		[1->3] meaning [easy->hard]
 * 
 * To Find out the Theme Chosen for NEW GAME:
 * 		Call gameSettings.getTheme()
 * @author angelayang
 *
 */
public class SideBar extends JPanel implements ActionListener {
	
	private JLabel header;
	private StatusBar gameStatus;
	private JButton newGameButton;
	private ImageIcon headerImage;
	private JLabel padding;
	private Settings gameSettings;
	private Connect4Board connect4Board;
	
	
	private Font fontStyle;
	private Color background;
	private Color text;
	
	/**
	 * Constructor for sideBar
	 */
	public SideBar(Connect4Board connect4Board) throws IOException {
		super();
		this.connect4Board = connect4Board;
		//setLayout(new GridLayout(4, 1, 10, 10));		
		fontStyle = new Font("Myriad Pro", Font.BOLD, 20);
//		text = new Color(122, 160, 170);
		text = new Color(78, 128, 166);
		background = new Color(233,232,207);
		setBackground(background);
		this.setOpaque(true);
		
		headerImage = new ImageIcon("header.png");
		gameStatus = new StatusBar();
		padding = new JLabel();
		gameSettings = new Settings();
		newGameButton = new JButton("NEW GAME");
		newGameButton.setPreferredSize(new Dimension(250, 40));
		newGameButton.addActionListener(this);
		newGameButton.setBackground(null);
		initialisePanels();
	}
	
	/**
	 * Helper function for constructor
	 * Adds all the necessary panels
	 */
	private void initialisePanels() {
		
		
		header = new JLabel();
		header.setIcon(headerImage);
		header.setPreferredSize(new Dimension(250,110));
		
		newGameButton.setFont(fontStyle);
		newGameButton.setForeground(text);
		newGameButton.setOpaque(true);
		
		
		padding.setPreferredSize(new Dimension(250, 100));
		
		add(header);
		add(gameStatus);
		add(padding);
		add(gameSettings);
		add(newGameButton);
		
	}
	

	/**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(250,550);
    }
	
	/**
	 * Gives access to the newGameButton
	 * for connect4Board class
	 * @return
	 */
	public JButton getNewGameButton() {
		return newGameButton;
	}
	
	/**
	 * Pass in:
	 * PLAYER 1 as ZERO
	 * PLAYER 2 as ONE
	 */
	public void nextPlayer(int player) {
		player += 1;
		player %= 2;
		
		gameStatus.setPlayer(2, gameSettings.getOpponent());
	}
	
	/**
	 * Gives access to the gameSettings
	 * for connect4Board class
	 * @return
	 */
	public Settings getSettings() {
		return gameSettings;
	}
	
	public StatusBar getStatus(){
		return gameStatus;
	}

	public void paintCompoent(Graphics g)
	{
	   super.paintComponent(g);
	   setBackground(background);
	}
	
	public void setColorTheme(String theme) {
		if(theme.equals("Classic")) {
			text = new Color(78, 128, 166);
			
			background = new Color(233,232,207);
			gameStatus.setBackgroundShade(background);
		} else if (theme.equals("2016 Election")) {
			background = new Color(230,230,230);
			gameStatus.setBackgroundShade(background);
		} else if (theme.equals("Wes Anderson")){
			//text = new Color(231, 196, 76);
			background = new Color(231, 196, 76);
			gameStatus.setBackgroundShade(background);
		} else {
			background = new Color(230,219,166);
			gameStatus.setBackgroundShade(background);
		}
		setBackground(background);
		newGameButton.setForeground(text);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameButton){

			getStatus().setThemedIcon(gameSettings.getTheme());
			setColorTheme(gameSettings.getTheme());
			gameStatus.revealStatus();
			nextPlayer(1);
			try {
				connect4Board.clearBoard();
			} catch (IOException e1) {

			}
			connect4Board.setBoardSettings(gameSettings.getOpponent(), gameSettings.getTheme());
			//connect4Board.setBoardSettings(gameSettings.getOpponent(), gameSettings.getTheme());
			//getStatus().setThemedIcon(gameSettings.getTheme());
		}
	}
}