import java.util.Scanner;

public class Game {
	private int lastMove[];
	private int currentPlayer;
	private int previousPlayer;
	private int turnNumber;
	private Board board;
	
	public Game() {
		board = new Board();
		turnNumber = 0;
		currentPlayer = 0;
		previousPlayer = 1;
		lastMove = new int[2];
		lastMove[0] = -1;
		lastMove[1] = -1;
	}
	
    public static void main(String[] args) {
    	
    	Game c4 = new Game();
    	//Player AI = new NoviceAI(1);
		c4.board.printBoard();
    	while (!c4.gameOver()) {
    		
    		Scanner reader = new Scanner(System.in);
    		int currPlayer = c4.getPlayer();		
    		System.out.println(">>>>>>>>>>>>");
    		boolean success = false;
    		
    		//uncomment if statement and bracket below to test AI instead of another human player
    		/*if (currPlayer == 1) {
    			int move = AI.bestMove(c4.board);
    			while (!success) {
    				System.out.println("AI Player " + currPlayer + "'s turn. Chose column: " + move);
    				success = c4.makeMove(move, currPlayer);
    				c4.board.printBoard();
    			}    			
    		} else {*/
    		//if (columnNumber < NUM_COLUMNS);
    		
	    		while(!success) {
	    			System.out.println("Player " + currPlayer + "'s turn. Choose a column: ");
	        		int columnNumber = reader.nextInt();
	    			success = c4.makeMove(columnNumber, currPlayer);
	    			c4.board.printBoard();
	    		}
    		//}
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
	    	
	    	int index = board.columnSize(columnNumber) - 1; // CHECK THIS
	    	updateLast(index, columnNumber);
	    	return true;
    	} else {
    		System.out.println("Choose another column");
    		return false;
    	}
    }
    
    /**
     * Updates Information for next turn
     */
    public void nextTurn() {
    	turnNumber++;
    	previousPlayer = currentPlayer;
    	currentPlayer = turnNumber % 2;
    }
    
    /**
     * Checks is board is full or if last turn won the game
     * @return
     */
    public boolean gameOver() {
    	if(board.isFull()) {
    		return true;
    	} 
    	return board.checkFour(lastMove);
    }
	
    public int getPlayer() {
    	return currentPlayer;
    }
 
	// SETS POSITION OF LAST TOKEN [ROW_INDEX, COL_INDEX]
	private void updateLast (int pos, int colNum) {
		lastMove[0] = pos;
		lastMove[1] = colNum;
	}
}

