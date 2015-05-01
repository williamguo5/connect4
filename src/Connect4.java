import java.util.*;

public class Connect4 {

	private int turnNumber;
	private int player;
	
    private Board board;

    public Connect4() {
    	board = new Board();
    }

    public static void main(String[] args) {
    	
    	Connect4 c4 = new Connect4();
    	int i = 0;
    	while (!c4.gameOver()) {
    		c4.board.printBoard();
    		c4.board.getColumn(0).addToken(1);
    		c4.board.getColumn(1).addToken(1);
    		c4.board.getColumn(2).addToken(1);
    		c4.board.getColumn(3).addToken(1);
    		c4.board.getColumn(4).addToken(1);
    		c4.board.getColumn(5).addToken(1);
    		c4.board.getColumn(6).addToken(1);
    		i++;
    		//if (i == 2) break;
    		c4.board.printBoard();
    		System.out.println(">>>>>>");
    	}
    	
    }
    
    public int getTurn() {
    	return turnNumber;
    }
    
    public int getPlayer() {
    	return player;
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public boolean gameOver() {
    	if (board.isFull()) {
    		
    		return true;
    	}
    	return false;
    }
}
