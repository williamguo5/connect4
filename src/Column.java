import java.util.ArrayList;


public class Column {

	public final int NUM_ROWS = 6;
	
	private ArrayList<Token> column;
	
	public Column() {
		column = new ArrayList<Token>();
	}
	
	public ArrayList<Token> getColumn() {
		return column;
	}
	
	public void addToken(int playerID) {
		Token newToken = new Token(playerID);
		if (!isFull()) {
			System.out.println("<<<<<<<<");
			column.add(newToken);
		}
	}
	
	public boolean isFull() {
		if (column.size() < NUM_ROWS) {
			return false;
		}
		return true;
	}
}
