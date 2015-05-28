import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Backend that contains the board representation, handles turns while telling GUI to update based on the changed board
 * Also handles AI moves
 */
public class Board{
	public static final int NUM_TO_WIN = 4;
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 7;
	public static final int EMPTY = -1;
	
	private int[][] board;
	private int lastMove[];
	private int currentPlayer;
	private int previousPlayer;
	private int turnNumber;
	private boolean gameOver;
	private GUIBoard guiBoard;
	private Player ai;
    private boolean isSimulation;
    private int[][] winSeq;
	
	/**
	 * Creates a new empty board
	 * @param guiBoard GUI board class
	 */
	public Board(GUIBoard guiBoard){
		this.guiBoard = guiBoard;
		
		ai = null;
		resetBoard();
	}
	
	/**
	 * 
	 * @return AI player or null
	 */
	public boolean isAi(){
		if(ai == null) return false;
		else return true;
	}
	
	/**
	 * Resets the board to initial state
	 */
	public void resetBoard(){
		gameOver = false;
		turnNumber = 0;
		currentPlayer = 0;
		previousPlayer = 1;
		lastMove = new int[2];
		lastMove[0] = Board.EMPTY;
		lastMove[1] = Board.EMPTY;
        isSimulation = false;

        board = new int[NUM_ROWS][NUM_COLS];
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				board[i][j] = EMPTY;
			}
		}
		winSeq = new int[NUM_TO_WIN][2];
		for (int i = 0; i < NUM_TO_WIN; i++) {
			for (int j = 0; j < 2; j++) {
				winSeq[i][j] = EMPTY;
			}
		}
	}
	
	
	/**
	 * Set the AI difficulty
	 * 
	 * @param index index of AI 
	 */
	public void setAI(int index) {
		if(index == 0){
			ai = null;
		}else if(index == 1){
			ai = new NoviceAI();
		}else if(index == 2){
			ai = new IntermediateAI();
		}else if(index == 3){
			ai = new ExpertAI();
		}
	}
	
	/**
	 * 
	 * @return boolean for if game is tied or not
	 */
	public boolean isTie() {
		if(isFull()) {
			if(!checkFour(lastMove, currentPlayer)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a column is full. If the last element in the column (top of the column) 
	 * is not empty, then it is full.
	 * 
	 * @param colNum 
	 * @return true for column full and false otherwise
	 */
	public boolean isColumnFull(int colNum) {
		if (board[NUM_ROWS - 1][colNum] == EMPTY) {
			return false;
		}
		return true;
	}
	
	/**
     * Drops the token in the given column for the given player
     * <p>
     * Breaks early and does not add token to board if the column is full, or when the game is over.
     * 
     * @param colNum column number
     */
	public void dropToken(int colNum) {
		if(gameOver) return;
		if(isColumnFull(colNum)){
			System.out.println("COL FULL");
			return;
		}
		int pos = getColumnSize(colNum);
		board[pos][colNum] = currentPlayer;
		updateLast(pos, colNum);
		if(checkFour(lastMove, currentPlayer)){
			gameOver = true;
			System.out.println("GAME OVER, " + currentPlayer + " wins");
		}else if(isFull()){
			gameOver = true;
			System.out.println("GAME OVER, DRAW");
		}

		guiBoard.displayToken(currentPlayer, lastMove);
		printBoard();
		nextTurn();
		guiBoard.setStatus(currentPlayer);
	}
	
	/**
	 * Drops token in the given column for the current player but does not update GUI
	 * <p>
	 * Used only by AIs in analysis
	 * 
	 * @param colNum columnm number
	 */
	public void simDropToken(int colNum) { 
		if(gameOver) return;
		if(isColumnFull(colNum)){
			return;
		}
		int pos = getColumnSize(colNum);
		board[pos][colNum] = currentPlayer;
		updateLast(pos, colNum);
		if(checkFour(lastMove, currentPlayer)){
			gameOver = true;
		}
		nextTurn();
	}
	
	/**
	 * 
	 * @return whether board is full
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
	 * @param lastMove the most recent move made by a player
	 * @param player the player who made that move
	 * @return whether a player has won or not
	 */	
	public boolean checkFour(int[] lastMove, int player) {
		int currentRow = lastMove[0];
		int currentColumn = lastMove[1];
		
		//The range of columns to check
		int startColumn = currentColumn - 3;
		if (startColumn < 0) startColumn = 0;
		int endColumn = currentColumn + 3;
		if (endColumn > NUM_COLS - 1) endColumn = NUM_COLS - 1;

		//Check horizontal
		for (int i = startColumn, count = 0; i <= endColumn; i++) {
			if (board[currentRow][i] == player) {
				winSeq[count][0] = currentRow;
				winSeq[count][1] = i;
				count++;
			} else {
				count = 0;
				winSeq[count][0] = EMPTY;
				winSeq[count][1] = EMPTY;
			}
			if (count >= NUM_TO_WIN) return true;
		}
		
		//The range of rows to check
		int startRow = currentRow - 3;
		if (startRow < 0) startRow = 0;
		int endRow = currentRow + 3;
		if (endRow > NUM_ROWS - 1) endRow = NUM_ROWS - 1;
		
		//Check vertical
		for (int i = startRow, count = 0; i <= endRow; i++) {
			if (board[i][currentColumn] == player) {
				winSeq[count][0] = i;
				winSeq[count][1] = currentColumn;
				count++;
			} else {
				count = 0;
				winSeq[count][0] = EMPTY;
				winSeq[count][1] = EMPTY;
			}
			if (count >= NUM_TO_WIN) return true;
		}
		
		//Check diagonal with positive gradient
		startColumn = currentColumn;
		endColumn = currentColumn;
		startRow = currentRow;
		endRow = currentRow;
		
		//Get the starting coordinate of the diagonal
		int displacement = 0;
		while (startRow != 0 && startColumn != 0 && displacement < 3) {
			startRow--;
			startColumn--;
			displacement++;
		}
		//Get the ending coordinate of the diagonal
		displacement = 0;
		while (endRow != NUM_ROWS - 1 && endColumn != NUM_COLS - 1 && displacement < 3) {
			endRow++;
			endColumn++;
			displacement++;
		}
		
		//Check along this diagonal for 4 in a row
		for (int i = startRow, j = startColumn, count = 0; i <= endRow; i++, j++) {
			if (board[i][j] == player) {
				winSeq[count][0] = i;
				winSeq[count][1] = j;
				count++;
			} else {
				count = 0;
				winSeq[count][0] = EMPTY;
				winSeq[count][1] = EMPTY;
			}
			if (count >= NUM_TO_WIN) return true;
		}
		
		//Check diagonal with negative gradient
		startColumn = currentColumn;
		endColumn = currentColumn;
		startRow = currentRow;
		endRow = currentRow;
		
		//Starting coordinate
		displacement = 0;
		while (startColumn != 0 && endRow != NUM_ROWS - 1 && displacement < 3) {
			startColumn--;
			endRow++;
			displacement++;
		}	
		//Ending coordinate
		displacement = 0;
		while (startRow != 0 && endColumn != NUM_COLS - 1 && displacement < 3) {
			startRow--;
			endColumn++;
			displacement++;
		}
		
		//Check along the diagonal
		for (int i = startRow, j = endColumn, count = 0; i <= endRow; i++, j--) {
			if (board[i][j] == player) {
				winSeq[count][0] = i;
				winSeq[count][1] = j;
				count++;
			} else {
				count = 0;
				winSeq[count][0] = EMPTY;
				winSeq[count][1] = EMPTY;
			}
			if (count >= NUM_TO_WIN) return true;
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
	
	/**
     * Increments the turn number and updates the previous and current player
     */
	public void nextTurn() {
		turnNumber++;
		if(isFull()){
			gameOver = true;
			return;
		}
		previousPlayer = currentPlayer;
		currentPlayer = turnNumber % 2;
	}
	
	/**
	 * Asks AI for a move
	 * 
	 */
	public void aiMove(){
		if(currentPlayer == 1 && ai != null){
			
			System.out.println(turnNumber);
            isSimulation = true;
            final int col = ai.getMove(this.clone());
            isSimulation = false;
            System.out.println("AI MOVE " + col);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	try {
						delayDisplay();
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                dropToken(col);
                }
            });	
		}
		
	}
	
	/**
	 * Updates the last move made
	 * @param rowNum row number of new move
	 * @param colNum column number of new move
	 */
	private void updateLast (int rowNum, int colNum) {
		lastMove[0] = rowNum;
		lastMove[1] = colNum;
	}
	
	/**
	 * 
	 * @return true if either player wins, or is a draw
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * Creates a clone of the board
	 */
	public Board clone(){
		Board boardClone = new Board(guiBoard);
		int newBoard[][] = new int[NUM_ROWS][NUM_COLS];
		for(int j = 0; j < NUM_COLS; j++){
			for(int i = 0; i < NUM_ROWS; i++){
				newBoard[i][j] = board[i][j];
			}
		}
		boardClone.board = newBoard;
		boardClone.lastMove = this.lastMove;
		boardClone.currentPlayer = this.currentPlayer;
		boardClone.previousPlayer = this.previousPlayer;
		boardClone.turnNumber = this.turnNumber;
		boardClone.gameOver = this.gameOver;
        boardClone.isSimulation = this.isSimulation;
		return boardClone;
	}
	
	/**
	 * Delay for displaying AI player's move
	 */
	public void delayDisplay() throws InterruptedException{      

		Thread.sleep(300);
	}
	
	/**
	 * 
	 * @return the most recent move
	 */
	public int[] getLastMove(){
		return lastMove;
	}
	
	/**
	 * @return the id of the current player
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}

	/**
	 * 
	 * @return the id of the previous player
	 */
    public int getPreviousPlayer() { return previousPlayer; }
    
	/**
	 * Allows AIs to see more of the board, ensure this is called on a cloned Board class, not the original
	 * @return the board with player tokens on it
	 */
	public int[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Returns the size of a column
	 * 
	 * @param colNum column number
	 * @return the number of tokens in a column
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
	
	/**
	 * 
	 * @return integer array containing the sequence (coordinates) that won the game
	 */
	public int[][] getWinSeq() {
		return winSeq;
	}
	
	/**
	 * 
	 * @return the turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}
}
