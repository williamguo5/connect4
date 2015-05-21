import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




//import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class Connect4Board extends JLayeredPane implements ActionListener{
	
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
	
	/**
	 * Constructor for a connect4 GUI
	 * @throws IOException 
	 */
	public Connect4Board() throws IOException {
        setBorder(BorderFactory.createLineBorder(Color.black));
        board = new Board(this);
//        this.board = board;
        colBlank = "colBlank.png";
        colMouseOver = "colMouseOver.png";
        colClick = "colClick.png";
        setBackgroundImg("board3.png");
        
        
        closeOverlay = new JButton("Close");
//        overlayImage = ImageIO.read(new File("background2.png"));
        landingScreen = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
//		        super.paintComponent(g);       
		        
				g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), null);
		    }
		};
		landingScreen.setBackground(Color.red);
		
		overlay = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
//		        super.paintComponent(g);       
		        
				g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), null);
		    }
		};
		overlay.setBackground(Color.lightGray);
		
		overlay.setLayout(new BorderLayout());
    	overlay.add(closeOverlay, BorderLayout.SOUTH);
    	closeOverlay.addActionListener(this);

//    	overlay.setSize(this.getWidth(), this.getHeight());
//    	closeOverlay.setLocation(this.getWidth()/2, this.getHeight()*9/10);
    	closeOverlay.setLocation(325, 500);
//    	overlay.add(closeOverlay, BorderLayout.SOUTH);
    	
    	
    	
    	
    	
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
    	generatedBoard = true;
    	setBackgroundImg("board3.png");
    	for(int i = 0; i < Board.NUM_COLS; i++){
    		add(columnButtons.get(i));
    		columnButtons.get(i).setBackgroundImg(colBlank);
    		final int colNum = i;
	    	columnButtons.get(i).addMouseListener(new MouseListener() {
	    		@Override
		        public void mouseReleased(MouseEvent e) {
	    			if(boardFrozen) return;
	    			try {
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
	    	      		System.out.println("return from AI");

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
						columnButtons.get(colNum).setBackgroundImg(colMouseOver);
						columnButtons.get(colNum).repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if(boardFrozen) return;
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
    		if(board.isGameOver()){
    			remove(overlay);
				repaint();
    		}
	    	for(int i = 0; i < Board.NUM_COLS; i++){
	    		columnButtons.get(i).resetTokens();
	    	}
	    	freezeBoard(false);
	    	board.resetBoard();
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
        
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
    
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
      }
    
    public void displayLandingScreen(){
    	landingScreen.setSize(this.getWidth(), this.getHeight());
    	landingScreen.setLocation(0, 0);

    	add(landingScreen, 0);
    }
    
    public void displayWinner(int currentPlayer){
    	
    	freezeBoard(true);
    	System.out.print("Winner is: ");
    	if(currentPlayer == 0){
    		System.out.print("Yellow");
    	}else{
    		System.out.print("Red");
    	}
    	System.out.println();
    	
    	for(int i = 0; i < Board.NUM_COLS; i++){
    		try {
				columnButtons.get(i).setBackgroundImg(colBlank);
			} catch (IOException e) {

			}
    	}
    	
    	overlay.setSize(this.getWidth(), this.getHeight()/2);
    	overlay.setLocation(0, this.getHeight()/4);
    	closeOverlay.setLocation(this.getWidth()/2, this.getHeight()*9/10);
    	closeOverlay.setLocation(0, 0);
    	overlay.add(closeOverlay, BorderLayout.SOUTH);
    	add(overlay, 0);
    }
    
    public void delayOverlay() throws InterruptedException{    
    	Timer timer = new Timer(500, new ActionListener() {
	       public void actionPerformed(ActionEvent evt) {
	    	   if(!board.isGameOver()) return;
	    	   displayWinner(board.getPreviousPlayer());
	       }
	     });
	     timer.setRepeats(false);
	     timer.start();
    }
    
    public void freezeBoard(boolean canResize){
    	JFrame frame = (JFrame)this.getRootPane().getParent();
    	if(canResize){
    		frame.setResizable(false);
    		boardFrozen = true;
    	}else{
    		frame.setResizable(true);
    		boardFrozen = false;
    	}
    	System.out.println(this.getWidth() + " " + this.getHeight());
    }
    
    public void setBoardSettings(int aiSetting, String theme){
    	board.setAI(aiSetting);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeOverlay){
			remove(overlay);
			repaint();
		}
	}
}
