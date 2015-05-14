
public class IntermediateAI implements Player {
    //static int MAX_DEPTH = 8;

    //Remember to pass in a clone of the state
    public int getMove(Board state) {
        int move = 0;

        for (int col = 0; col < Board.NUM_COLS; col++) {
            if (!state.isColumnFull(col)) {
                //Board clone = (Board) state.clone();
                if(state.dropToken(col, state.getCurrentPlayer())) {
                    move = AI(state); //Calls the AI to determine whether or not it is game over
                    //Otherwise it will recurse further into the state tree
                    return col;
                }
                //move = AI(new Board(state));
            }
            //if (state.isGameOver()) return col;
        }
        return move;
    }

    /**
     * Takes in a state and checks if it reaches gameOver
     * Otherwise it calls getMove again and creates more states
     * @param state
     * @return
     */
    public int AI(Board state) {
        int move = 0;
        if (state.isGameOver()) {
            return state.getLastMove()[1];
        } else {
            move = getMove(state);
        }
        return move;


    }


    //Remember to pass in a clone of the state


    //check if move is valid
    //pass in a board state and possible move
    // return a boolean

}
