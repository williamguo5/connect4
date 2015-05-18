import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class SideBar extends JPanel {
	
	private Image backgroundImage;
	private ImageIcon title;
	private Color backgroundShade;
	private boolean reset;
	
	private JButton newGame;
	private StatusBar gameStatus;
	private JLabel header;
	
	private Connect4Board connect4Board;
	
	/**
	 * Contains 
	 * 	CONNECT FOUR HEADER
	 * 	NEW GAME BUTTON
	 * 	STATUS BAR
	 * 
	 */
	public SideBar(Connect4Board connect4Board) throws IOException{
		this.connect4Board = connect4Board;
		setBorder(BorderFactory.createLineBorder(Color.white));
		title = new ImageIcon("header.png");
		reset = false;
		setupSideBar();
	}
	
	/**
	 * setupSideBar()
	 * @throws IOException 
	 */
	private void setupSideBar() throws IOException {
		ImageIcon newGameLabel = new ImageIcon("newGame.png");
		backgroundShade = new Color(233,232,207);
		
		header = new JLabel();
		header.setIcon(title);
		header.setPreferredSize(new Dimension(250, 110));
		
		newGame = new JButton(newGameLabel);
		newGame.setPreferredSize(new Dimension(250,110));
		newGame.setBorderPainted(false);
		newGame.setBackground(backgroundShade);
		newGame.setOpaque(true);
		//newGame.addActionListener(this);	// ISSUE HERE
		
		gameStatus = new StatusBar();
		//gameStatus.setPreferredSize(new Dimension(250,330));
		gameStatus.setPreferredSize(new Dimension(250,250));
		gameStatus.setBackground(backgroundShade);
		
		add(header);
		add(gameStatus);
		add(newGame);
	}
	
	
	/**
	 * Action for when newGAme occurs
	 */
	
	public void setNewGame() {
		
		try {
			gameStatus = new StatusBar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reset = true;
	}
	
	/**
	 * Gives access to the game's newGame button
	 */
     public JButton getNewGameButton() {
    	 return newGame;
     }
      
	/**
	 * Gives access to the game's status bar
	 * 
	 */
     public StatusBar getStatusBar() {
    	 return gameStatus;
     }
	
	
	/**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(250,550);
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
    	g.drawImage(backgroundImage, 0, 0, null);
    }
    
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
      }
}
