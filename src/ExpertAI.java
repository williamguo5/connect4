import java.util.Random;



public class ExpertAI implements Player {
    static int MAX_DEPTH = 6;

    /**
     * Gets the optimal move when calling calculateMove functions
     * @param state Sends in a clone of the board state to perform simulations in calculateMove
     * @return Returns the a valid column
     */
    public int getMove(Board state) {
        System.out.println("SIMULATION");
        //Initially the the calculateMove evaluates all possible moves as the same score,
        // and the AI selects the first column
        if (state.getTurnNumber() == 1) {
            Random randomGenerator = new Random();
            int randomNumber = randomGenerator.nextInt(3);
            if (randomNumber == 0) {
                return 2;
            } else if (randomNumber == 1){
                return 3;
            } else{
                return 4;
            }
        }
        return calculateMove(state.clone());
    }

    /**
     * Calculates the optimal move for the board based on the opponents move
     * @param state It is the current state of the cloned board.
     * @return Returns the optimal column based on the minimax algorithm using alpha-beta pruning
     */
    public int calculateMove(Board state) {
        double maxScore = Integer.MIN_VALUE;
        int move = 0;
        for (int col = 0; col < Board.NUM_COLS; col++) {
            if (!state.isColumnFull(col)) { //Checks that the column is valid move
                Board clone = state.clone(); //Clones the board state to simulate a move
                clone.simDropToken(col);
                double score = alphabeta(clone, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                System.out.println("SCORE " + score);
                if (score > maxScore) { //Always gets the max score
                    maxScore = score;
                    move = col;
                }
            }
        }
        return move;
    }

    /**
     * Uses the minimax algorithm with alpha-beta pruning to simulate a tree depth of 6 moves. Then,
     * it calls a heuristic function which evaluates the board state
     * @param state Current state of the board
     * @param depth The current move depth of the tree
     * @param alpha The max value of two scores for the maximising player
     * @param beta The min value of two scores for the minimising player
     * @param maximisingPlayer A boolean which states if it's the AI's turn
     * @return Returns a score if the depth reaches 0 or gameOver,
     * otherwise it tries to prune non-optimal moves
     */
    public double alphabeta(Board state, int depth, double alpha, double beta, boolean maximisingPlayer) {
        if (depth == 0 || state.isGameOver()) {
            double score = 0;
            if (maximisingPlayer) {
                if (state.isGameOver()) return -100000/(MAX_DEPTH - depth + 1);
                score = -calculateScore(state);

            } else if (!maximisingPlayer) {
                if (state.isGameOver()) return 100000/(MAX_DEPTH - depth + 1);
                score = calculateScore(state);

            }
            return score/(MAX_DEPTH - depth + 1); // The deeper you traverse, the lower your score is
        }

        if (maximisingPlayer) { //AI's turn
            for (int col = 0; col < Board.NUM_COLS; col++) {
                if (!state.isColumnFull(col)) {
                    Board clone = state.clone();
                    clone.simDropToken(col);
                    alpha = Math.max(alpha,
                                    alphabeta(clone, depth - 1, alpha, beta, false));
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return alpha;
        } else { //The human player's turn
            for (int col = 0; col < Board.NUM_COLS; col++) {
                if (!state.isColumnFull(col)) {
                    Board clone = state.clone();
                    clone.simDropToken(col);
                    beta = Math.min(beta,
                                    alphabeta(clone, depth - 1, alpha, beta, true));
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return beta;
        }
    }

    /**
     * Evaluates the current state of the board
     * @param state Current state of the board
     * @return An evaluated score of the curren state of the board
     */
    public double calculateScore(Board state) {
        int score = 0;

        for (int col = 0; col <= 3; col++) {
            for (int row = 0; row < Board.NUM_ROWS; row++) {

                //Previous player single horizontal counter checks

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 100;

                //Current player(player has to yet to make a move) single horizontal counter checks

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score += 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 100;

                //Previous player 2 horizontal counter check

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 250;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 250;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 250;

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 250;

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 250;

                //Current player(new has yet to made) 2 horizontal counter check

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 250;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score += 250;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 250;

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 250;

                //Checking three in a row for previous player

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 500;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 500;

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 500;

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 500;

                //Checking 3 in horizontal row current player

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score += 500;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 500;

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 500;

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 500;

            }

        }
        return score;

    }

}
