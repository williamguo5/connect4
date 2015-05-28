import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class GUIBoard extends JLayeredPane implements ActionListener{
	
	private Image backgroundImage;
	private String colBlank;
	private String colMouseOver;
	private String colClick;
	private ArrayList<ColumnButton> columnButtons;
	private JButton closeOverlay;
	private Board board;
	private boolean boardFrozen;
	private JPanel overlay;
	private JPanel landingScreen;
	private Image overlayImage;
	private boolean generatedBoard;
	private SideBar sidebar;
	private JFrame overlayFrame;
	private String theme;
	private JLabel display;
	private JLabel icon;
	private int colSelected;
	private boolean spacePressed;
	
	/**
	 * Constructor for a connect4 GUI
	 * @throws IOException 
	 */
	public GUIBoard() throws IOException {
        setBorder(BorderFactory.createLineBorder(Color.black));
        board = new Board(this);
//        this.board = board;
        colBlank = "assets/colBlank.png";
        colMouseOver = "assets/colMouseOver.png";
        colClick = "assets/colClick.png";
        setBackgroundImg("assets/land.png");
        ImageIcon gif = new ImageIcon("assets/giphy.gif");
        icon = new JLabel(gif);
//        add(icon);
        
        theme = "Classic";
        display = new JLabel();
        display.setForeground(new Color(122, 160, 170));
    	display.setBackground(new Color(233,232,207));
    	display.setFont(new Font("Myriad Pro", Font.BOLD, 30));
		display.setPreferredSize(new Dimension(125,110));
		display.setHorizontalAlignment(SwingConstants.CENTER);
		display.setBorder(BorderFactory.createLineBorder(Color.black));
        
        overlayFrame = new JFrame();
    
    	overlayFrame.setUndecorated(true);
//    	overlayFrame.toFront();
    	overlayFrame.setAlwaysOnTop(true);
    	
//    	overlayFrame.setLocation(350, 130);
    	overlayFrame.setPreferredSize(new Dimension(450,360));
    	overlayFrame.setBackground(new Color(233,232,207));
    	overlayFrame.pack();
    	
        closeOverlay = new JButton("Close");
       // closeOverlay.setPreferredSize(new Dimension(50, 50));
//        overlayImage = ImageIO.read(new File("background2.png"));
      
		
		overlay = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
//		        super.paintComponent(g);       
		        
				g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), null);
		    }
		};
		//overlay.setBackground(Color.lightGray);
		//overlay.setBackground(new Color(122, 160, 170));
		overlay.setLayout(new BorderLayout());
    	overlay.add(closeOverlay, BorderLayout.SOUTH);
        closeOverlay.setBorder(BorderFactory.createLineBorder(Color.black));
        closeOverlay.setFont(new Font("Myriad Pro", Font.BOLD, 30));
    	closeOverlay.setForeground(new Color(233,232,207));
    	closeOverlay.addActionListener(this);
    	closeOverlay.setBackground(new Color(122, 160, 170));
    	closeOverlay.setOpaque(true);
    	overlayFrame.add(overlay);

    	
    	setLayout(new GridLayout(1, Board.NUM_COLS));
		columnButtons = new ArrayList<ColumnButton>();
		for(int i = 0; i < Board.NUM_COLS; i++){
			columnButtons.add(new ColumnButton());
		}
		generatedBoard = false;
//		generateBoard();
    }

	/**
	 * Method to generate a new board with mouse listeners for columns

	 * @throws IOException 
	 */
    public void generateBoard() throws IOException { 
    	colSelected = Board.NUM_COLS/2;
    	remove(icon);
    	generatedBoard = true;
    	setBackgroundImg("assets/board3.png");
    	for(int i = 0; i < Board.NUM_COLS; i++){
    		add(columnButtons.get(i));
    		columnButtons.get(i).setBackgroundImg(colBlank);
    		final int colNum = i;
	    	columnButtons.get(i).addMouseListener(new MouseListener() {
	    		@Override
		        public void mouseReleased(MouseEvent e) {
	    			if(boardFrozen) return;
	    			try {
	    				columnButtons.get(colSelected).setBackgroundImg(colBlank);
						columnButtons.get(colSelected).repaint();
						colSelected = colNum;
	    	      		columnButtons.get(colNum).setBackgroundImg(colMouseOver);
	    	      		columnButtons.get(colNum).repaint();
	    	      		board.aiMove();
	    	      		System.out.println(board.isGameOver());
	    	      		if(board.isGameOver()){
	    	      			columnButtons.get(colNum).setBackgroundImg(colBlank);
	    	      			try {
								delayOverlay();
							} catch (InterruptedException e2) {

							}
	    	    	  	}
	    	      		if(board.isAi()){
	    	      			try {
								delayOverlay();
							} catch (InterruptedException e2) {

							}
	    	      		}
//	    	      		System.out.println("return from AI");

	    			} catch (IOException e1){
	    				e1.printStackTrace();
	    			}
		        }
		        @Override
		        public void mousePressed(MouseEvent e) {
		        	if(boardFrozen) return;
		        	board.dropToken(colNum);
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
					if(boardFrozen) return;
					try {
						columnButtons.get(colSelected).setBackgroundImg(colBlank);
						columnButtons.get(colSelected).repaint();
						colSelected = colNum;
						columnButtons.get(colNum).setBackgroundImg(colMouseOver);
						columnButtons.get(colNum).repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
//					if(boardFrozen) return;
//					try {
//						deselectColumns();
////						columnButtons.get(colNum).setBackgroundImg(colBlank);
////						columnButtons.get(colNum).repaint();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
				}
			});
    	}
    	createKeyboardListeners();
	}
    
    public void createKeyboardListeners() throws IOException{
    	setFocusable(true);
		requestFocusInWindow();
		columnButtons.get(colSelected).setBackgroundImg(colMouseOver);
		columnButtons.get(colSelected).repaint();
		spacePressed = false;
    	addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
					spacePressed = false;
					if(boardFrozen){
						sidebar.newGamePressed();
						colSelected = Board.NUM_COLS/2;
						return;
					}
					board.dropToken(colSelected);
					try {
						columnButtons.get(colSelected).setBackgroundImg(colMouseOver);
						columnButtons.get(colSelected).repaint();
					} catch (IOException e3) {
					}
					
					board.aiMove();
    	      		System.out.println(board.isGameOver());
    	      		if(board.isGameOver()){
    	      			try {
							columnButtons.get(colSelected).setBackgroundImg(colBlank);
						} catch (IOException e1) {
						}
    	      			try {
							delayOverlay();
						} catch (InterruptedException e2) {

						}
    	    	  	}
    	      		if(board.isAi()){
    	      			try {
							delayOverlay();
						} catch (InterruptedException e2) {

						}
    	      		}

				}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					if(boardFrozen) return;
					try {
						deselectColumns();
						columnButtons.get(colSelected).setBackgroundImg(colBlank);
						columnButtons.get(colSelected).repaint();
						colSelected--;
						if(colSelected == -1) colSelected = Board.NUM_COLS - 1;
						if(spacePressed){
							columnButtons.get(colSelected).setBackgroundImg(colClick);
							columnButtons.get(colSelected).repaint();
						}else{
							columnButtons.get(colSelected).setBackgroundImg(colMouseOver);
							columnButtons.get(colSelected).repaint();
						}

					} catch (IOException e1) {

					}
				}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(boardFrozen) return;
					try {
						deselectColumns();
						columnButtons.get(colSelected).setBackgroundImg(colBlank);
						columnButtons.get(colSelected).repaint();
						colSelected++;
						if(colSelected == Board.NUM_COLS) colSelected = 0;
						if(spacePressed){
							columnButtons.get(colSelected).setBackgroundImg(colClick);
							columnButtons.get(colSelected).repaint();
						}else{
							columnButtons.get(colSelected).setBackgroundImg(colMouseOver);
							columnButtons.get(colSelected).repaint();
						}

					} catch (IOException e1) {
					}
				}
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
					spacePressed = true;
					if(boardFrozen) return;
					try {
						columnButtons.get(colSelected).setBackgroundImg(colClick);
						columnButtons.get(colSelected).repaint();
					} catch (IOException e1) {
					}
						
				}
			}
		});
		
    }
    
    public void deselectColumns() throws IOException{
    	for(int i = 0; i < Board.NUM_COLS; i++){
			columnButtons.get(i).setBackgroundImg(colBlank);
			columnButtons.get(i).repaint();
		}
    }
    
    /**
     * Displays the given move onto the GUI board
     * @param currentPlayer
     * @param move
     */
    public void displayToken(int currentPlayer, int[] move){
	  	columnButtons.get(move[1]).displayToken(currentPlayer, move[0]);
    }
    
    /**
     * Clears the board
     * @throws IOException 
     */
    public void clearBoard() throws IOException{
    	if(!generatedBoard){
    		generateBoard();
    	}else{
    		
	    	for(int i = 0; i < Board.NUM_COLS; i++){
	    		columnButtons.get(i).resetTokens();
	    	}
	    	freezeBoard(false);
	    	board.resetBoard();
	    	overlayFrame.setVisible(false);
    	}
    }

    /**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(630,550);
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
    
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }
    
    /**
     * Shows popup menu when gameover
     * @param currentPlayer
     * @param isAi
     */
    public void displayWinner(int currentPlayer, boolean isAi, JLabel display){
    	String message = "<html><em><font color = #4e80a6>";
    	int moves = board.getTurnNumber()/2;
    	sidebar.getStatus().hideStatus();
    	freezeBoard(true);
    	if(board.isTie()) {
    		message += "GAME TIED";
    	} else {
    		if(board.isAi()) {
        		if(currentPlayer == 0){
            		message += "YOU WIN";
            		moves += 1;
            	} else {
            		message += "YOU LOSE";
            	}
        	} else if (!board.isAi()) {
        		if(currentPlayer == 0){
        			message += "PLAYER 1";
        			moves += 1;
            	} else {
            		message += "PLAYER 2";
            	}
        		
        		message += " " + "WINS!";
        	}	
    	}
    	
    	
    	message += "<font></em><br><br><font size = 6>";
    	message += "Moves: " + moves ;
    	message += "</font></html>";
    	
//    	System.out.println(message);
    	try {
			deselectColumns();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	closeOverlay.setForeground(sidebar.getBackgroundTheme());
    	overlayFrame.setBackground(sidebar.getBackgroundTheme());
    	
    	display.setText(message);
    	
    	overlay.add(display);
    	overlayFrame.setVisible(true);
    	
    }
    
    public JFrame getOverlayFrame(){
    	return overlayFrame;
    }
    
    /**
     * needs to display TIED result and needs to display number of moves 
     * @throws InterruptedException
     */
    public void delayOverlay() throws InterruptedException{    
    	Timer timer = new Timer(500, new ActionListener() {
	       public void actionPerformed(ActionEvent evt) {
	    	   if(!board.isGameOver()) return;
	    		  displayWinner(board.getPreviousPlayer(), board.isAi(), display); 
	       }
	     });
	     timer.setRepeats(false);
	     timer.start();
    }
    
    public void freezeBoard(boolean canResize){
    	JFrame frame = (JFrame)this.getRootPane().getParent();
    	if(canResize){
//    		frame.setResizable(false);
    		boardFrozen = true;
    	}else{
    		frame.setResizable(true);
    		boardFrozen = false;
    	}
    	System.out.println(this.getWidth() + " " + this.getHeight());
    }
    
    public void setBoardSettings(int aiSetting, String themeChosen){
    	requestFocusInWindow();
    	board.setAI(aiSetting);
    	//theme = themeChosen;
    	
    	for(int i = 0; i < Board.NUM_COLS; i++) {
    		try {
				columnButtons.get(i).setToken(themeChosen);
			} catch (IOException e) {
				
			}
    	}
    	
    }
    
    public void setStatus(int currentPlayer){
    	int type = 0;
    	if(board.isAi()){
    		type = 1;
    	}
    	sidebar.getStatus().setPlayer(currentPlayer, type);
    }
    
    public void setSidebar(SideBar sidebar){
    	this.sidebar = sidebar;
    }
    
    public JFrame getOverlay(){
    	return overlayFrame;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeOverlay){
			overlayFrame.setVisible(false);
			repaint();
		}
	}

//	@Override
//	public void focusGained(FocusEvent e) {
//		System.out.println("focus gained");
//        setBorder(BorderFactory.createLineBorder(Color.red));
//        repaint();
//	}
//
//	@Override
//	public void focusLost(FocusEvent e) {
//		System.out.println("focus lost");
//        setBorder(BorderFactory.createLineBorder(Color.black));
//        repaint();
//	}
}
