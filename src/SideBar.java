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
 * This is a menu side bar which contains a information
 * about the game state, drop down menus to alter
 * settings and a new game button
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
	private GUIBoard guiBoard;
	
	
	private Font fontStyle;
	private Color background;
	private Color text;
	
	/**
	 * Constructor for sideBar
	 */
	public SideBar(GUIBoard connect4Board) throws IOException {
		super();
		this.guiBoard = connect4Board;	
		fontStyle = new Font("Myriad Pro", Font.BOLD, 20);

		text = new Color(78, 128, 166);
		background = new Color(233,232,207);
		setBackground(background);
		this.setOpaque(true);
		
		headerImage = new ImageIcon("assets/header2.png");
		gameStatus = new StatusBar();
		padding = new JLabel();
		gameSettings = new Settings();
		
		newGameButton = new JButton("NEW GAME");
		newGameButton.setPreferredSize(new Dimension(300, 40));
		newGameButton.addActionListener(this);
		newGameButton.setBackground(null);
		
		initialisePanels();
	}
	
	/**
	 * Helper function to the constructor
	 * Adds all the necessary panels
	 */
	private void initialisePanels() {
		
		header = new JLabel();
		header.setIcon(headerImage);
		
		header.setPreferredSize(new Dimension(300,110));
		header.setHorizontalAlignment(HEIGHT);
		
		
		newGameButton.setFont(fontStyle);
		newGameButton.setForeground(text);
		newGameButton.setOpaque(true);
		
		padding.setPreferredSize(new Dimension(300, 100));
		
		add(header);
		add(gameStatus);
		add(padding);
		add(gameSettings);
		add(newGameButton);
	}
	

	/**
     * Set the size of the side bar panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(300,550);
    }
	
	/**
	 * Set the background color of side bar
	 */
	public void paintComponent(Graphics g)
	{
	   super.paintComponent(g);
	   setBackground(background);
	}
	
	/**
	 * Gives access to the newGameButton
	 * for connect4Board class
	 * @return newGameButton, JButton
	 */
	public JButton getNewGameButton() {
		return newGameButton;
	}
	
	/**
	 * Sets the gameStatus bar with the whose turn
	 * is next.
	 * 
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
	 * @return Settings JPanel 
	 */
	public Settings getSettings() {
		return gameSettings;
	}
	
	/**
	 * Gives access to the game's status bar
	 * which only appears when game is in progress
	 * It displays the current player
	 * @return StatusBar, JPanel
	 */
	public StatusBar getStatus(){
		return gameStatus;
	}

	
	/**
	 * Setter which sets the background and text
	 * color according to the chosen theme
	 * @param theme as a string
	 */
	public void setColorTheme(String theme) {
		if(theme.equals("Classic")) {
			text = new Color(78, 128, 166);
			
			background = new Color(233,232,207);
		} else if (theme.equals("2016 Election")) {
			background = new Color(216,232,220);
		} else if (theme.equals("Wes Anderson")){
			background = new Color(231, 196, 76);
		} else {
			background = new Color(230,219,166);
		}
		setBackground(background);
		gameStatus.setBackgroundShade(background);
	}
	
	/**
	 * Gets the current background Color used 
	 * @return Color used for the background of the side bar
	 */
	public Color getBackgroundTheme() {
		return background;
	}
	
	/**
	 * Updates the entire GUI after new game has been pressed
	 * according to the settings selected.
	 */
	public void newGamePressed(){
		getStatus().setThemedIcon(gameSettings.getTheme());
		setColorTheme(gameSettings.getTheme());
		gameStatus.revealStatus();
		nextPlayer(1);
		try {
			guiBoard.clearBoard();
		} catch (IOException e1) {

		}
		guiBoard.setBoardSettings(gameSettings.getOpponent(), gameSettings.getTheme());
	}

	/**
	 * Actions to be taken when New Game button has been pressed
	 * It changes the icons according to chosen theme
	 * It reveals the status bar
	 * It initializes the current player for the side bar
	 * It clears the connect 4 board
	 * It tells connect 4 board to setup the game according to the 
	 * chosen settings
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameButton){	
			newGamePressed();
		}
	}
}