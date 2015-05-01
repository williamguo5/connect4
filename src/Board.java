import java.util.*;

public class Board {

	public final int NUM_ROWS = 6;
	public final int NUM_COLUMNS = 7;
	
	private ArrayList<Column> board;
	
	public Board() {
		board = new ArrayList<Column>();
		for (int i = 0; i < NUM_COLUMNS; i++) {
			board.add(new Column());
		}
	}
	
	public Column getColumn(int colNum) {
		return board.get(colNum);
	}
	
	public boolean isFull() {
		for (Column col : board) {
			if (!col.isFull()) {
				return false;
			}
		}
		return true;
	}

	public void printBoard() {
		for (int i = NUM_ROWS -1 ; i >= 0; i--) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				if (board.get(j).getColumn().size() <= i) {
					System.out.print(" O");
				} else {
					System.out.print(" X");
				}
				
			}
			System.out.println();
		}
	}
}
