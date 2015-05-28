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

/**
 * Main system starts GUI
 */
public class Connect4 {

	private GUIBoard guiBoard;
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
	
	 /**
	  * Sets up GUI and frame resizing and moving.
	  * @throws IOException
	  */
	public Connect4() throws IOException {
		mainFrame = new JFrame("Connect 4");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setResizable(false);
		guiBoard = new GUIBoard();
		sidebar = new SideBar(guiBoard);
		guiBoard.setSidebar(sidebar);
		mainFrame.setSize(new Dimension(940,570));
		mainFrame.addComponentListener(new ComponentAdapter() {
			boolean overlayed = false;
			JFrame overlayFrame = guiBoard.getOverlayFrame();
			int boardWidth = guiBoard.getWidth();
//			@Override
//			public void componentResized(ComponentEvent e) {
//				width = mainFrame.getWidth();
//				height = mainFrame.getWidth()*570/940;
//				if(width < 940) width = 940;
//				mainFrame.setSize(width, height);
//				int boardWidth = guiBoard.getWidth();
//				int overlayWidth = boardWidth*9/13;
//				guiBoard.getOverlay().setSize(overlayWidth, overlayWidth*4/5);
//				int x = mainFrame.getLocation().x;
//				int y = mainFrame.getLocation().y;
//				int overlayHeight = guiBoard.getHeight();
//				guiBoard.getOverlayFrame().setLocation(x + 308 + boardWidth/7, y + 117);
//			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				int x = mainFrame.getLocation().x;
				int y = mainFrame.getLocation().y; 
				int boardWidth = guiBoard.getWidth();
				guiBoard.getOverlayFrame().setLocation(x + 308 + boardWidth/7, y + 117);
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
				}				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				if(overlayFrame.isVisible()){
					overlayed = true;
					overlayFrame.setVisible(false);
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
		
		
	}
	
	/**
	 * Method to display the main window
	 */
	public void display() {
		mainFrame.getContentPane().add(guiBoard,BorderLayout.CENTER);
		mainFrame.getContentPane().add(sidebar, BorderLayout.WEST);
        mainFrame.setVisible(true);
	}
}
