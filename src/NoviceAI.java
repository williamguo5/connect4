
public class NoviceAI implements Player {
	public int getMove(Board state) {
		int move = 0;

		//This really basic AI will only try to block 4 in a rows across or down, but only tries to find out via scanning the last move made
		//It will not try to block a win via a diagonal 4 in a row
		//If it cannot find any it will simply find the first empty column
		//to place its token in
		int me = state.getCurrentPlayer();
		int opponent = -1;
		if (me == 1) { //one player is 0 and another is 1
			opponent = 0;
		} else {
			opponent = 1;
		}
		if (state.isGameOver()) {
			return 1;
		}
		if (state.getTurnNumber() > 0) {
			int lastMove[] = state.getLastMove();
			//Check the row across where last move was made for 3 in a row
			for (int i = 0, count = 0; i < Board.NUM_COLS; i++) {
				if (state.getBoard()[lastMove[0]][i] == opponent) {
					count++;
					System.out.print("Hi");
				} else {
					count = 0;
					System.out.print("Bye");
				}
				if (count == 3 && i < Board.NUM_COLS && !state.isColumnFull(i+1)) {
					return i+1; //block horizontal win
				} else if (count == 3 && i == Board.NUM_COLS && state.isColumnFull(i-3)) {
					return i-3; //|_|_|_|here|x|x|x|
				}
			}

			//Check the column where last move was made for 3 in a row
			for (int i = 0, count = 0; i < Board.NUM_ROWS; i++) {
				if (state.isColumnFull(lastMove[1])) break;
				if (state.getBoard()[i][lastMove[1]] == opponent) {
					count++;
				} else {
					count = 0;
				}
				if (count == 3) return lastMove[1]; //block off the vertical win
			}
		}
		for (int col = 0; col < Board.NUM_COLS; col++) {
			if (!state.isColumnFull(col)) {
				return col;
			}
		}
        return move;
    }
}
