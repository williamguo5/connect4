import java.util.*;

public class Board {

	private ArrayList<Column> board;
	
	public Column getColumn(int colNum) {
		return board.get(colNum);
	}
	
	public boolean isFull() {
		return false;
	}

	public void printBoard() {
		
	}
}
