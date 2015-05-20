
public class NoviceAI implements Player {
	public int getMove(Board state) {
		int move = 0;
		int lastMove[] = state.getLastMove();
		int me = state.getCurrentPlayer();
		int opponent = state.getPreviousPlayer();
		/*if (state.getTurnNumber() > 0) {
			
			boolean block = false;
			for (int i = 0, count = 0; i < Board.NUM_COLS; i++) {
				if (state.getBoard()[lastMove[0]][i] == opponent) {
					count++;
				} else {
					count = 0;
				}
				
				if (count == 2 && i < Board.NUM_COLS - 1 && !state.isColumnFull(i+1)) {
					//return i+1;
					move = i+1;
					block = true;
					//break;
				} else if (count == 2 && i == Board.NUM_COLS - 1 && state.isColumnFull(i-2)) {
					//return i-2;
					move = i-2;
					block = true;
					//break;
				} else if (count == 3 && i < Board.NUM_COLS && !state.isColumnFull(i+1)) {
					
				}
				System.out.println("Move is " + move);
			}
			if (!block) {
				move = -1;
			}*/
			//Check the column where last move was made for 3 in a row
			for (int i = 0, count = 0; i < Board.NUM_ROWS; i++) {
				if (state.isColumnFull(lastMove[1])) break;
				if (state.getBoard()[i][lastMove[1]] == opponent) {
					count++;
				} else {
					count = 0;
				}
				if (count == 3)  {
					move = lastMove[1]; //block off the vertical win
					break;
				} else {
					move = -1;
				}
			}
		//}
		for (int col = 0; col < Board.NUM_COLS; col++) {
			if (!state.isColumnFull(col)) {
				move = col;
				state.simDropToken(col);
				if (state.isGameOver()) {
					move = col; //instantly place the token that will win the game
					state.undoPreviousMove();
					return move;
				} else {				
					for (int i = 0; i < Board.NUM_COLS; i++) {
						Board opponentTurn = state.clone();
						opponentTurn.simDropToken(i);
						if (opponentTurn.isGameOver()) {
							return i;
						}
					}
				}
			}
		}
        return move;
    }
}
