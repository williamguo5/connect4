
public class Board {
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 7;
	public static final int EMPTY = -1;
	
	private int[][] board;
	
	// initialize board to -1
	public Board() {
		board = new int[NUM_ROWS][NUM_COLS];
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				board[i][j] = EMPTY;
			}
		}
	}
	
	// IS COLUMN FULL
	public boolean isColumnFull(int colNum) {
		if (board[NUM_ROWS - 1][colNum] == EMPTY) {
			return false;
		}
		return true;
	}
	
	// GET TOP OF COLUMN
	public int topOfColumn(int colNum) {
		int top = columnSize(colNum);
		int player = board[top][colNum];
		return player;
	}
	
	
	// GET COLUMN SIZE
	public int columnSize(int colNum) {
		int size = 0;
		for (int i = 0; i < NUM_ROWS; i++) {
			if (board[i][colNum] == EMPTY) {
				break;
			} else {
				size++;
			}
		}
		return size;
	}
	
	// DROP COUNTER. NEED TO UPDATE LAST MOVE
	// Doesn't account for if column is full
	public void dropToken(int colNum, int id) {
		int pos = columnSize(colNum);
		board[pos][colNum] = id;
	}
	
	// CHECK IF BOARD IS FULL
	public boolean isFull() {
		for (int i= 0; i < NUM_COLS; i++) {
			if (!isColumnFull(i)) {
				return false;
			}
		}
		return true;
	}
	
	// check token for 4 in row
	public boolean checkFour(int[] lastMove) {
		boolean fourInRow = false;
		
		// check across
		
		// check up
		
		// check diagonal
		return false;
	}
	
	// SHOW BOARD
	public void printBoard() {
		for(int i = NUM_ROWS - 1; i >= 0; i--) {
			for(int j = 0; j < NUM_COLS; j++) {
				 if (board[i][j] == EMPTY) {
					 System.out.print("|_");
					 //Replace 0 with PlayerID constant field?
				 } else if (board[i][j] == 0){
					 System.out.print("|O");
				 } else {
					 System.out.print("|X");
				 }
			}
			System.out.println("|");
		}
		System.out.println();
	}
}