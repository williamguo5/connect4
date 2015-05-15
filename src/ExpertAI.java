
public class ExpertAI implements Player {
	public int getMove(Board state) {
		int move = 0;
		if (state.isGameOver()) {
			return 1;
		}
        if (state.getTurnNumber() == 0 || state.getTurnNumber() == 1) {
        	if (state.getColumnSize(3) != 0) {
        		return 4;
        	}
        	return 3;
        }
        move = testBestMove(state);
        return move;
    }
	
	//TODO decision making beyond if/else
	private int testBestMove(Board state) {
		int move = 0;
		int me = state.getCurrentPlayer();
		for (int i = 0; i < Board.NUM_COLS; i++) {
			if (!state.isColumnFull(i)) {
				Board test = state.clone();
				test.setAI(null); //prevents an ai move from being called when simulating game states
				test.simDropToken(i);
				if (test.isGameOver()) {
					move = i; //instantly place the token that will win the game
					break;
				} else {
					move = opponentWin(test, i); //this code not yet working
					if (move == -1) {
						move = 0;
					} else {
						break;
					}
					int[][] tempBoard = state.getBoard();
					int size = state.getColumnSize(i);
					if (i == Board.NUM_COLS - 1) {			
						if (size != 0) {
							if (state.getColumnSize(i-1) == size || state.getColumnSize(i-1) == size + 1) { 
								//check diagonally down if columns are same size, otherwise checks across
								if (tempBoard[state.getColumnSize(i-1)-1][i-1] == me) {
									move = i;
									break;
								}						
							} else if (state.getColumnSize(i-1) == size + 2) {
								//check diagonally upwards
								if (tempBoard[state.getColumnSize(i-1)-1][i-1] == me) {
									move = i;
									break;
								}	
							}
							if (tempBoard[size - 1][i] == me) { //check below
								move = i;
								break;
							}
						} else {
							if (tempBoard[0][i-1] == me) {
								move = i;
								break;
							} else if (tempBoard[1][i-1] == me) {
								move = i;
								break;
							}
						}
					} else if (i == 0) {
						if (size != 0) {
							if (state.getColumnSize(i+1) == size || state.getColumnSize(i+1) == size + 1) { 
								//check diagonally down if columns are same size, otherwise checks across
								if (tempBoard[state.getColumnSize(i+1)-1][i+1] == me) {
									move = i;
									break;
								}						
							} else if (state.getColumnSize(i+1) == size + 2) {
								//check diagonally upwards
								if (tempBoard[state.getColumnSize(i+1)-1][i+1] == me) {
									move = i;
									break;
								}	
							}
							if (tempBoard[size - 1][i] == me) { //check below
								move = i;
								break;
							}
						} else {
							if (tempBoard[0][i+1] == me) {
								move = i;
								break;
							} else if (tempBoard[1][i+1] == me) {
								move = i;
								break;
							}
						}
					} else {
						if (size != 0) {
							if (state.getColumnSize(i-1) == size || state.getColumnSize(i-1) == size + 1) { 
								//left side
								//check diagonally down if columns are same size, otherwise checks across
								if (tempBoard[state.getColumnSize(i-1)-1][i-1] == me) {
									move = i;
									break;
								}						
							} else if (state.getColumnSize(i-1) == size + 2) {
								//check diagonally upwards
								if (tempBoard[state.getColumnSize(i-1)-1][i-1] == me) {
									move = i;
									break;
								}	
							} else if (state.getColumnSize(i+1) == size || state.getColumnSize(i+1) == size + 1) { 
								//right side
								//check diagonally down if columns are same size, otherwise checks across
								if (tempBoard[state.getColumnSize(i+1)-1][i+1] == me) {
									move = i;
									break;
								}						
							} else if (state.getColumnSize(i+1) == size + 2) {
								//check diagonally upwards
								if (tempBoard[state.getColumnSize(i+1)-1][i+1] == me) {
									move = i;
									break;
								}	
							}
							if (tempBoard[size - 1][i] == me) { //check below
								move = i;
								break;
							}
						} else {
							if (tempBoard[0][i-1] == me) {
								move = i;
								break;
							} else if (tempBoard[1][i-1] == me) {
								move = i;
								break;
							} else if (tempBoard[0][i+1] == me) {
								move = i;
								break;
							} else if (tempBoard[1][i+1] == me) {
								move = i;
								break;
							}
						}
					}
				}
			}
		}
		return move;
	}
	
	private int opponentWin(Board state, int aiMove) {
		int preventWin = -1; //if it is possible to block a win this move will be updated to that block
		if (state.getTurnNumber() < 6) { //win not possible yet
			return preventWin; 
		}
		for (int j = 0; j < Board.NUM_COLS; j++) {
			Board opponentMove = state.clone();
			opponentMove.setAI(null);
			if (!opponentMove.isColumnFull(j)) {
				opponentMove.simDropToken(j);
				//only give a blocking move if the move passed in does not allow the winning move in the first place
				if (opponentMove.isGameOver() && j != aiMove) { 
					System.out.println("Winning move found here");
					preventWin = j;
					return preventWin;
				}
			}
		}
		System.out.println("end");
		return preventWin;
	}
}
