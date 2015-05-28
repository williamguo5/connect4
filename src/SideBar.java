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
		//setLayout(new GridLayout(4, 1, 10, 10));		
		fontStyle = new Font("Myriad Pro", Font.BOLD, 20);
//		text = new Color(122, 160, 170);
		text = new Color(78, 128, 166);
		background = new Color(233,232,207);
		setBackground(background);
		this.setOpaque(true);
		
		headerImage = new ImageIcon("assets/header.png");
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
	

	public void paintComponent(Graphics g)
	{
	   super.paintComponent(g);
	   setBackground(background);
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
	
	/**
	 * Gives access to the game's status bar
	 * which only appear when game is in progress
	 * It displays the current player
	 * @return
	 */
	public StatusBar getStatus(){
		return gameStatus;
	}

	
	/**
	 * Setter which sets the background and text
	 * color for each theme
	 * @param theme as a string
	 */
	public void setColorTheme(String theme) {
		if(theme.equals("Classic")) {
			text = new Color(78, 128, 166);
			
			background = new Color(233,232,207);
		} else if (theme.equals("2016 Election")) {
			background = new Color(230,230,230);
		} else if (theme.equals("Wes Anderson")){
			background = new Color(231, 196, 76);
		} else {
			background = new Color(230,219,166);
		}
		setBackground(background);
		gameStatus.setBackgroundShade(background);
		//newGameButton.setForeground(text);
	}
	
	/**
	 * Gets the current background Color used 
	 * @return Color used for the background of the sidebar
	 */
	public Color getBackgroundTheme() {
		return background;
	}
	
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
	 * It initializes the current player for the sidebar
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