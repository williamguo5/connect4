import java.io.IOException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Connect4 {
	private int lastMove[];
	private int currentPlayer;
	private int previousPlayer;
	private int turnNumber;
	private Board board;
	
	public Connect4() {
		board = new Board();
		turnNumber = 0;
		currentPlayer = 0;
		previousPlayer = 1;
		lastMove = new int[2];
		lastMove[0] = Board.EMPTY;
		lastMove[1] = Board.EMPTY;
	}
	
    public static void main(String[] args) throws IOException {
		final MainWindow mw = new MainWindow();
		
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	mw.display();
            }
        });
    	Connect4 c4 = new Connect4();

		c4.board.printBoard();
    	while (!c4.gameOver()) {
    		
    		Scanner reader = new Scanner(System.in);
    		int currPlayer = c4.getPlayer();		
    		System.out.println(">>>>>>>>>>>>");
    		
    		boolean success = false;
    		
    		while (!success) {
    			System.out.println("Player " + currPlayer + "'s turn. Choose a column: ");
        		int columnNumber = reader.nextInt();
    			success = c4.makeMove(columnNumber, currPlayer);
    			c4.board.printBoard();
    		}
    		c4.nextTurn();
    	}
    	System.out.println("GAME OVER.");
    	System.out.println("Player " + c4.previousPlayer + " wins.");
    }
    
    /**
     * Drops the token in given row for the current player
     * If it's an invalid input it returns false
     * @param columnNumber
     * @param currPlayer
     * @return
     */
    public boolean makeMove (int columnNumber, int currPlayer) {
    	if(columnNumber >= 0 && columnNumber < 7 && !board.isColumnFull(columnNumber)) {
	    	board.dropToken(columnNumber, currPlayer);
	    	
	    	int index = board.getColumnSize(columnNumber) - 1; // CHECK THIS
	    	updateLast(index, columnNumber);
	    	return true;
    	} else {
    		System.out.println("Invalid move. Choose another column: ");
    		return false;
    	}
    }
    
    /**
     * Increments the turn number and updates the previous and current player
     */
    public void nextTurn() {
    	turnNumber++;
    	previousPlayer = currentPlayer;
    	currentPlayer = turnNumber % 2;
    }
    
    /**
     * Checks is board is full or if the previous turn won the game
     * @return
     */
    public boolean gameOver() {
    	if(board.isFull()) {
    		return true;
    	} 
    	if (turnNumber!= 0) {
    		return board.checkFour(lastMove, previousPlayer);
    	}
    	return false;
    }
	
    public int getPlayer() {
    	return currentPlayer;
    }
 
	// SETS POSITION OF LAST TOKEN [ROW_INDEX, COL_INDEX]
	private void updateLast (int rowNum, int colNum) {
		lastMove[0] = rowNum;
		lastMove[1] = colNum;
	}
}
