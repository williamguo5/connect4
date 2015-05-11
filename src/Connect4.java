import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

public class Connect4 {

	private Connect4Board connect4Board;
	
	private JFrame mainFrame;
	private JButton newGameButton;
	private SideBar sideBar;
	
	 public static void main(String[] args) throws IOException {
    	final Connect4 c4 = new Connect4();
		
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	c4.display();
            }
        });	
    }
	
	public Connect4() throws IOException {
		mainFrame = new JFrame("Connect4 Board Demo");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		connect4Board = new Connect4Board();
		sideBar = new SideBar();
		
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(connect4Board);

	}
	
	/**
	 * Method to display the main window
	 */
	public void display() {
		mainFrame.getContentPane().add(connect4Board,BorderLayout.CENTER);
		mainFrame.getContentPane().add(newGameButton,BorderLayout.SOUTH);
		mainFrame.getContentPane().add(sideBar, BorderLayout.WEST);
		mainFrame.pack();
        mainFrame.setVisible(true);
	}
}
