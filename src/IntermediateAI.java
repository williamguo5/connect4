
public class IntermediateAI implements Player {
    //static int MAX_DEPTH = 8;

    //Remember to pass in a clone of the state
    public int getMove(Board state) {
        System.out.println("SIMULATION");
        return maxMove(state);
    }

    public int maxMove(Board state) {
        int bestMove = 0;
        int move = 0;
        int bestCol = 0;
        if (state.isGameOver()) {
            return -4;
        } else {
            int[] lastMove = state.getLastMove();
            if (lastMove[1] > 0 && lastMove[1] + 1 < state.getColumnSize(lastMove[1])) {
                if (state.getBoard()[lastMove[0]][lastMove[1]] == state.getPreviousPlayer()) {
                    move += 1;
                }
                if (lastMove[0] > 0 && lastMove[0] < Board.NUM_ROWS - 1) {
                    if (state.getBoard()[lastMove[0] - 1][lastMove[1]] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1]] == state.getPreviousPlayer()) {
                        move += 1;
                    }

                    if (state.getBoard()[lastMove[0] - 1][lastMove[1] + 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1] + 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] - 1][lastMove[1] -1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1] - 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                }

            }
            for (int col = 0; col < Board.NUM_COLS; col++) {
                if (!state.isColumnFull(col)) {
                    state.dropToken(col);
                    move = minMove(state);
                    if (move > bestMove) {
                        bestMove = move;
                        bestCol = col;
                    }
                }
            }
        }
        return bestCol;
    }

    public int minMove(Board state) {
        int bestMove = 0;
        int move = 0;
        int bestCol = 0;
        if (state.isGameOver()) {
            return 4;
        } else {
            int[] lastMove = state.getLastMove();
            if (lastMove[1] > 0 && lastMove[1] + 1 < state.getColumnSize(lastMove[1])) {
                if (state.getBoard()[lastMove[0]][lastMove[1]] == state.getPreviousPlayer()) {
                    move += 1;
                }
                if (lastMove[0] > 0 && lastMove[0] < Board.NUM_ROWS - 1) {
                    if (state.getBoard()[lastMove[0] - 1][lastMove[1]] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1]] == state.getPreviousPlayer()) {
                        move += 1;
                    }

                    if (state.getBoard()[lastMove[0] - 1][lastMove[1] + 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1] + 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] - 1][lastMove[1] -1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                    if (state.getBoard()[lastMove[0] + 1][lastMove[1] - 1] == state.getPreviousPlayer()) {
                        move += 1;
                    }
                }

            }
            for (int col = 0; col < Board.NUM_COLS; col++) {
                if (!state.isColumnFull(col)) {
                    state.dropToken(col);
                    move = maxMove(state);
                    if (move > bestMove) {
                        bestMove = move;
                        bestCol = col;
                    }
                }
            }
        }
        return bestCol;
    }
            //Remember to pass in a clone of the state


    //check if move is valid
    //pass in a board state and possible move
    // return a boolean

}
