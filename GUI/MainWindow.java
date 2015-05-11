import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MainWindow{
	
	private JFrame mainFrame;
	private Connect4Board connect4Board;
	private JButton newGameButton;

	/**
	 * Method to bootstrap the main frame
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		final MainWindow mw = new MainWindow();
		
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	mw.display();
            }
        });
	}
	
	/**
	 * Constructor for the main window.
	 * @throws IOException 
	 */
	public MainWindow() throws IOException {
		mainFrame = new JFrame("Connect4 Board Demo");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create the soduku panel
		connect4Board = new Connect4Board();
		
		connect4Board.setBackgroundImg("background2.png");
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(connect4Board);
		connect4Board.setLayout(new GridLayout(1, 7));

	}


	/**
	 * Method to display the main window
	 */
	private void display() {
		mainFrame.getContentPane().add(connect4Board,BorderLayout.CENTER);
		mainFrame.getContentPane().add(newGameButton,BorderLayout.SOUTH);
		mainFrame.pack();
        mainFrame.setVisible(true);
	}

}
