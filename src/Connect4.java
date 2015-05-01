import java.util.*;

public class Connect4 {

	private int turnNumber;
	private int player;
	
    private Board board;

    public Connect4() {
    	board = new Board();
    }

    public static void main(String[] args) {
    	
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
    	return false;
    }
}
