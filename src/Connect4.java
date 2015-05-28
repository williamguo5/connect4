import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import javax.swing.*;

public class Connect4 {

	private GUIBoard guiBoard;
//	private Board board;
	private JFrame mainFrame;
	private SideBar sidebar;
	private int width;
	private int height;
	
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
		
		guiBoard = new GUIBoard();
		sidebar = new SideBar(guiBoard);
		guiBoard.setSidebar(sidebar);
//		sideBar = new SideBar();
		mainFrame.setSize(new Dimension(890,570));
		mainFrame.addComponentListener(new ComponentAdapter() {
			boolean overlayed = false;
			JFrame overlayFrame = guiBoard.getOverlayFrame();
			int boardWidth = guiBoard.getWidth();
			@Override
			public void componentResized(ComponentEvent e) {
				width = mainFrame.getWidth();
				height = mainFrame.getWidth()*19/30;
				if(width < 890) width = 890;
//				if(width)
				mainFrame.setSize(width, height);
				int boardWidth = guiBoard.getWidth();
				int overlayWidth = boardWidth*9/13;
				guiBoard.getOverlay().setSize(overlayWidth, overlayWidth*4/5);
				int x = mainFrame.getLocation().x;
				int y = mainFrame.getLocation().y;
				int overlayHeight = guiBoard.getHeight();

				guiBoard.getOverlayFrame().setLocation(256 + boardWidth/7,140);
				guiBoard.getOverlayFrame().setLocation(x + 258 + boardWidth/7, y + 117);
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				int x = mainFrame.getLocation().x;
				int y = mainFrame.getLocation().y; 
				int boardWidth = guiBoard.getWidth();
				guiBoard.getOverlayFrame().setLocation(x + 258 + boardWidth/7, y + 117);
			}
		});	
		mainFrame.addWindowListener(new WindowAdapter() {
		
			boolean overlayed = false;
			JFrame overlayFrame = guiBoard.getOverlayFrame();
			@Override
			public void windowIconified(WindowEvent e) {
				if(overlayFrame.isVisible()){
					overlayed = true;
					overlayFrame.setVisible(false);
				}
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				if(overlayed){
					overlayFrame.setVisible(true);
//					overlayFrame.toFront();
				}
			}
		});
		
		guiBoard.getOverlayFrame().addWindowFocusListener(new WindowFocusListener() {
			boolean overlayed = false;
			JFrame overlayFrame = guiBoard.getOverlayFrame();

			@Override
			public void windowGainedFocus(WindowEvent e) {
				if(overlayed){
					overlayFrame.setVisible(true);
//					overlayFrame.setAlwaysOnTop(true);
				}				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				if(overlayFrame.isVisible()){
					overlayed = true;
					overlayFrame.setVisible(false);
//					overlayFrame.setAlwaysOnTop(false);
//					overlayFrame.toBack();
				}
				
			}
		});
		guiBoard.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
		        guiBoard.setBorder(BorderFactory.createLineBorder(Color.blue));
		        guiBoard.repaint();
			}
		
			@Override
			public void focusLost(FocusEvent e) {
		        guiBoard.setBorder(BorderFactory.createLineBorder(Color.black));
		        guiBoard.repaint();
			}
		});
		
    	
		// newgame - resets the board with the input settings		
		
	}
	
	/**
	 * Method to display the main window
	 */
	public void display() {
		mainFrame.getContentPane().add(guiBoard,BorderLayout.CENTER);
		mainFrame.getContentPane().add(sidebar, BorderLayout.WEST);
//		mainFrame.pack();
        mainFrame.setVisible(true);
	}
}
