public class Board {
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 7;
	public static final int EMPTY = -1;
	
	private int[][] board;
	
	// initialise board to be EMPTY
	public Board() {
		board = new int[NUM_ROWS][NUM_COLS];
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				board[i][j] = EMPTY;
			}
		}
	}
	
	/**
	 * Checks if a column is full. If the last element in the column (top of the column) 
	 * is not empty, then it is full.
	 * 
	 * @param colNum
	 * @return
	 */
	public boolean isColumnFull(int colNum) {
		if (board[NUM_ROWS - 1][colNum] == EMPTY) {
			return false;
		}
		return true;
	}

	
	/**
	 * Returns the size of a column, i.e. the number of tokens it contains
	 * 
	 * @param colNum
	 * @return
	 */
	public int getColumnSize(int colNum) {
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
		int pos = getColumnSize(colNum);
		board[pos][colNum] = id;
	}
	
	/**
	 * Checks if the board is full
	 * 
	 * @return
	 */
	public boolean isFull() {
		for (int i= 0; i < NUM_COLS; i++) {
			if (!isColumnFull(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks for if a player has won the game
	 * 
	 * @param lastMove
	 * @param player
	 * @return
	 */
	public boolean checkFour(int[] lastMove, int player) {
		int currentRow = lastMove[0];
		int currentColumn = lastMove[1];
		
		int startRow = currentRow - 3;
		if (startRow < 0) startRow = 0;
		int endRow = currentRow + 3;
		if (endRow > NUM_ROWS - 1) endRow = NUM_ROWS - 1;
		
		int startColumn = currentColumn - 3;
		if (startColumn < 0) startColumn = 0;
		int endColumn = currentColumn + 3;
		if (endColumn > NUM_COLS - 1) endColumn = NUM_COLS - 1;

		//Check horizontal
		for (int i = startColumn, count = 0; i <= endColumn; i++) {
			if (board[currentRow][i] == player) {
				count++;
			} else {
				count = 0;
			}
			if (count >= 4) return true;
		}
		
		//Check vertical
		for (int i = startRow, count = 0; i <= currentRow; i++) {
			if (board[i][currentColumn] == player) {
				count++;
			} else {
				count = 0;
			}
			if (count >= 4) return true;
		}
		
		//Check diagonal with positive gradient
		for (int i = startRow, j = startColumn, count = 0; i <= endRow; i++, j++) {
			if (board[i][j] == player) {
				count++;
			} else {
				count = 0;
			}
			if (count >= 4) return true;
		}
		
		//Check diagonal with negative gradient
		for (int i = endRow, j = startColumn, count = 0; i >= startRow; i--, j++) {
			if (board[i][j] == player) {
				count++;
			} else {
				count = 0;
			}
			if (count >= 4) return true;
		}
		return false;
	}
	
	/**
	 * Prints the contents of the board
	 */
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