import java.awt.event.*;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class Connect4Board extends JLayeredPane implements ActionListener {
	
	private Image backgroundImage;
	private String colBlank;
	private String colMouseOver;
	private String colClick;
	private ArrayList<ColumnButton> columnButtons;

	private Board board;
	
	/**
	 * Constructor for a soduku panel
	 * @throws IOException 
	 */
	public Connect4Board() throws IOException {
        setBorder(BorderFactory.createLineBorder(Color.black));
        board = new Board();
        colBlank = "colBlank.png";
        colMouseOver = "colMouseOver.png";
        colClick = "colClick.png";
        setBackgroundImg("background2.png");
		setLayout(new GridLayout(1, 7));
		
		columnButtons = new ArrayList<ColumnButton>();
		for(int i = 0; i < board.NUM_COLS; i++){
			columnButtons.add(new ColumnButton(i));
		}
		generateBoard();
    }

	/**
	 * Method to generate a new board
	 * NOTE: this isn't a real/valid soduku board.
	 * @throws IOException 
	 */
    public void generateBoard() throws IOException {    	
    	for(int i = 0; i < board.NUM_COLS; i++){
    		add(columnButtons.get(i));
    		columnButtons.get(i).setBackgroundImg(colBlank);
    		final int colNum = i;
	    	columnButtons.get(i).addMouseListener(new MouseListener() {
	    		@Override
		        public void mouseReleased(MouseEvent e) {
	    			try {
	    	      		columnButtons.get(colNum).setBackgroundImg(colMouseOver);
	    	      		columnButtons.get(colNum).repaint();
	    			} catch (IOException e1){
	    				e1.printStackTrace();
	    			}
		        }
		        @Override
		        public void mousePressed(MouseEvent e) {
		        	dropToken(colNum);
		    		try {
		    			columnButtons.get(colNum).setBackgroundImg(colClick);
		    			columnButtons.get(colNum).repaint();
		    		} catch (IOException e1) {
		    			e1.printStackTrace();
		    		}
		        }
		        @Override
		        public void mouseClicked(MouseEvent e) {
		
		        }
				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						columnButtons.get(colNum).setBackgroundImg(colMouseOver);
						columnButtons.get(colNum).repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					try {
						columnButtons.get(colNum).setBackgroundImg(colBlank);
						columnButtons.get(colNum).repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
    	}
	}
    
    public void dropToken(int colNum){
    	if(board.isColumnFull(colNum)){
  		  	System.out.println("COL FULL");
  		  	return;
  	  	}
	  	if(board.dropToken(colNum, board.getCurrentPlayer())){
	  		columnButtons.get(colNum).addToken(board.getCurrentPlayer());
	  	}  
    }
    
    public void clearBoard(){
    	for(int i = 0; i < board.NUM_COLS; i++){
    		columnButtons.get(i).resetTokens();
    	}
    }

    /**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(650,550);
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
 

	@Override
	public void actionPerformed(ActionEvent e) {
		board.resetBoard();
		clearBoard();
	}  

}
