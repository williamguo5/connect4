import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

public class Connect4 {

	private Connect4Board connect4Board;
//	private Board board;
	private JFrame mainFrame;
	private SideBar sidebar;
	
	 public static void main(String[] args) throws IOException{
    	final Connect4 c4 = new Connect4();
    	
    	
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	c4.display();
            }
        });	
    }
	
	public Connect4() throws IOException {
		mainFrame = new JFrame("Connect 4");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		board = new Board();
		
		connect4Board = new Connect4Board();
		sidebar = new SideBar(connect4Board);
		connect4Board.setSidebar(sidebar);
//		sideBar = new SideBar();
		mainFrame.setSize(new Dimension(900,570));
		mainFrame.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				mainFrame.setSize(mainFrame.getWidth(), mainFrame.getWidth()*19/30);
				int overlayWidth = connect4Board.getWidth()*9/13;
				connect4Board.getOverlay().setSize(overlayWidth, overlayWidth*4/5);
				System.out.println(mainFrame.getSize());
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println("moved");
				System.out.println(mainFrame.getLocationOnScreen());
				int x = mainFrame.getLocation().x;
				int y = mainFrame.getLocation().y; 
				connect4Board.getOverlay().setLocation(x + 350, y + 120);
			}
		});		
		// newgame - resets the board with the input settings		
		
	}
	
	/**
	 * Method to display the main window
	 */
	public void display() {
		mainFrame.getContentPane().add(connect4Board,BorderLayout.CENTER);
		mainFrame.getContentPane().add(sidebar, BorderLayout.WEST);
//		mainFrame.pack();
        mainFrame.setVisible(true);
	}
}
