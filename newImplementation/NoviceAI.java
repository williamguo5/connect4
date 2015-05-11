
public class NoviceAI implements Player {
	int num;
	
	public NoviceAI(int playerNum) { 
		num = playerNum; //give AI it's player number
	}
	
	/**
	 * returns int representing column it wishes to drop a token in
	 */
	public int bestMove(Board b) { //Passed a clone of the board
		int bestMove = 0;
		for (int i = 0; i < 7; i++) { //extremely basic AI finds an empty column and places a token there
			if (!b.isColumnFull(i)) {
				bestMove = i;
				break;
			}
		}
		return bestMove;
	}
	
}
