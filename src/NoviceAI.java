
public class NoviceAI implements Player {
	public int getMove(Board state) {
		int move = 0;
		int lastMove[] = state.getLastMove();
		int opponent = state.getPreviousPlayer();
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
			}
		}
		for (int col = 0; col < Board.NUM_COLS; col++) {
			if (!state.isColumnFull(col)) {
				Board myMove = state.clone();
				move = col;
				myMove.simDropToken(col);
				if (myMove.isGameOver()) {
					move = col; //instantly place the token that will win the game
					return move;
				} else {				
					for (int i = 0; i < Board.NUM_COLS; i++) {
						Board opponentTurn = myMove.clone();
						opponentTurn.simDropToken(i);
						if (opponentTurn.isGameOver() && col != i) {
							return i;
						}
					}
				}
			}
		}
		int start = 0;
		for (int j = 0; j < 4; j++) {
			for (int i = start, count = 0; i < start + 4; i++) {
				for (int k = 0; k < Board.NUM_ROWS; k++) {
					if (state.getBoard()[k][i] == opponent) {
						count++;
					}
					if (count == 2 && i != Board.NUM_COLS - 1 && !state.isColumnFull(i)) {
						if (!state.isColumnFull(i+1)) return i+1;
					} else if (count == 2 && i == Board.NUM_COLS -1 && state.isColumnFull(i)){
						if (!state.isColumnFull(i-2)) return i-2;
					}
				}
				
			}
			start++;
		}
		return move;
    }
}
