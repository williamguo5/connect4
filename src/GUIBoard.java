import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


/**
 * Class holds all GUI objects and handles main GUI function
 * Notifies backend of changes to game state
 */
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
	private Image overlayImage;
	private boolean generatedBoard;
	private SideBar sidebar;
	private JFrame overlayFrame;
	private JLabel display;
	private String theme;
	private int colSelected;
	private boolean spacePressed;
	
	/**
	 * Constructor for a connect4 GUI
	 * @throws IOException 
	 */
	public GUIBoard() throws IOException {
        setBorder(BorderFactory.createLineBorder(Color.black));
        board = new Board(this);
        colBlank = "assets/colBlank.png";
        colMouseOver = "assets/colMouseOver.png";
        colClick = "assets/colClick.png";
        setBackgroundImg("assets/land.png");
        
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
    	overlayFrame.setAlwaysOnTop(true);
    	overlayFrame.setPreferredSize(new Dimension(450,360));
    	overlayFrame.setBackground(new Color(233,232,207));
    	overlayFrame.pack();
    	
        closeOverlay = new JButton("Close");

		overlay = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {

				g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), null);
		    }
		};
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
    }

	/**
	 * Method to generate a new board with mouse listeners for columns

	 * @throws IOException 
	 */
    public void generateBoard() throws IOException { 
    	colSelected = Board.NUM_COLS/2;
    	generatedBoard = true;
    	setBackgroundImg("assets/board3.png");
    	for(int i = 0; i < Board.NUM_COLS; i++){
    		add(columnButtons.get(i));
    		columnButtons.get(i).setBackgroundImg(colBlank);
    		final int colNum = i;
	    	columnButtons.get(i).addMouseListener(new MouseAdapter() {
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
	    	      		if(board.isGameOver()){
	    	      			columnButtons.get(colNum).setBackgroundImg(colBlank);
	    	      			try {
								delayOverlay();
							} catch (InterruptedException e2) {}
	    	    	  	}
	    	      		if(board.isAi()){
	    	      			try {
								delayOverlay();
							} catch (InterruptedException e2) {}
	    	      		}
	    			} catch (IOException e1){}
		        }
		        @Override
		        public void mousePressed(MouseEvent e) {
		        	if(boardFrozen) return;
		        	board.dropToken(colNum);
		    		try {
		    			columnButtons.get(colNum).setBackgroundImg(colClick);
		    			columnButtons.get(colNum).repaint();
		    			
		    		} catch (IOException e1) {}
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
					} catch (IOException e1) {}
				}
			});
    	}
    	createKeyboardListeners();
	}
    
    /**
     * Listens to keyboard presses when user wishes to use keyboard input to play
     * @throws IOException
     */
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
					} catch (IOException e3) {}
					board.aiMove();
    	      		if(board.isGameOver()){
    	      			try {
							columnButtons.get(colSelected).setBackgroundImg(colBlank);
						} catch (IOException e1) {
						}
    	      			try {
							delayOverlay();
						} catch (InterruptedException e2) {}
    	    	  	}
    	      		if(board.isAi()){
    	      			try {
							delayOverlay();
						} catch (InterruptedException e2) {}
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
					} catch (IOException e1) {}
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

					} catch (IOException e1) {}
				}
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
					spacePressed = true;
					if(boardFrozen) return;
					try {
						columnButtons.get(colSelected).setBackgroundImg(colClick);
						columnButtons.get(colSelected).repaint();
					} catch (IOException e1) {}	
				}
			}
		});
		
    }
    
    /**
     * Removes highlight on column
     * @throws IOException
     */
    public void deselectColumns() throws IOException{
    	for(int i = 0; i < Board.NUM_COLS; i++){
			columnButtons.get(i).setBackgroundImg(colBlank);
			columnButtons.get(i).repaint();
		}
    }
    
    /**
     * Displays the given move onto the GUI board
     * @param currentPlayer the current player
     * @param move Move to show on board
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
    
    /**
     * Sets board background
     * @param fileName name of file
     * @throws IOException
     */
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }
    
    /**
     * Shows popup menu when gameover
     * @param currentPlayer the current player
     * @param isAi boolean for whether the AI won or human won
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

    	try {
			deselectColumns();
		} catch (IOException e) {}
    	
    	closeOverlay.setForeground(sidebar.getBackgroundTheme());
    	overlayFrame.setBackground(sidebar.getBackgroundTheme());
    	
    	display.setText(message);
    	
    	overlay.add(display);
    	overlayFrame.setVisible(true);
    	
    	if(board.isTie()) return;
    	int [][] winningMove = board.getWinSeq();
    	
    	for(int i = 0; i < 4; i++){
    		columnButtons.get(winningMove[i][1]).highlightToken(currentPlayer, winningMove[i][0]);
    	}
    }
    
    /**
     * 
     * @return JFrame overlay
     */
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
	    	   if(board.isFull()){
	    		   displayWinner(board.getCurrentPlayer(), board.isAi(), display); 
	    	   }else{
	    		   displayWinner(board.getPreviousPlayer(), board.isAi(), display);
	    	   }
	       }
	     });
	     timer.setRepeats(false);
	     timer.start();
    }
    
    /**
     * Prevents inputs on the GUI when called
     * @param canResize whether the board can be resized at this moment
     */
    public void freezeBoard(boolean canResize){
    	JFrame frame = (JFrame)this.getRootPane().getParent();
    	if(canResize){
    		boardFrozen = true;
    	}else{
    		frame.setResizable(true);
    		boardFrozen = false;
    	}
    }
    
    /**
     * Sets the theme and AI settings chosen by the user
     * @param aiSetting the AI difficulty setting
     * @param themeChosen current theme chosen
     */
    public void setBoardSettings(int aiSetting, String themeChosen){
    	requestFocusInWindow();
    	board.setAI(aiSetting);
    	
    	for(int i = 0; i < Board.NUM_COLS; i++) {
    		try {
				columnButtons.get(i).setToken(themeChosen);
			} catch (IOException e) {}
    	}
    }
    
    /**
     * Changes setting bar to show current turn
     * @param currentPlayer the current player
     */
    public void setStatus(int currentPlayer){
    	int type = 0;
    	if(board.isAi()){
    		type = 1;
    	}
    	sidebar.getStatus().setPlayer(currentPlayer, type);
    }
    
    /**
     * Sets sidebar object
     * @param sidebar sidebar of the GUI
     */
    public void setSidebar(SideBar sidebar){
    	this.sidebar = sidebar;
    }
    
    /**
     * 
     * @return JFrame overlay
     */
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
}
