import java.util.Random;

/**
 * Intermediate AI class which is a simplified version of the Expert AI
 *
 */
public class IntermediateAI implements Player {
    static int MAX_DEPTH = 6;

    /**
     * Calls a search function giving costs to all available moves and returns the best move based on cost found
     */
    public int getMove(Board state) {
        System.out.println("SIMULATION");
        if (state.getTurnNumber() == 1) {
            Random randomGenerator = new Random();
            int randomNumber = randomGenerator.nextInt(2);
            if (randomNumber == 0) {
                return 2;
            } else if (randomNumber == 1){
                return 4;
            } 
        }
        return calculateMove(state.clone());
    }

    /**
     * Calls the alpha-beta function on consecutive future turns
     * originating from a single move and returns a score of that
     * move
     * @param state
     * @return cost found from scanning given board 
     */
    public int calculateMove(Board state) {
        double maxScore = Integer.MIN_VALUE;
        int move = 0;
        for (int col = 0; col < Board.NUM_COLS; col++) {
            if (!state.isColumnFull(col)) {
                Board clone = state.clone();
                clone.simDropToken(col);
                double score = alphabeta(clone, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                System.out.println("SCORE " + score);
                if (score >= maxScore) {
                    maxScore = score;
                    move = col;
                    if (score == 1) break;
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
                score = -100000;
            } else if (!maximisingPlayer) {
                score = 100000;
            }
            return score/(MAX_DEPTH - depth + 1);
        }

        if (depth <= 3) {
            return calculateScore(state);
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
     * Scans the board and adds or takes away score based on tokens located in given columns and the player which owns the token
     * Takes away score for opponent turns and adds score for its own tokens
     * @param state
     * @return score found scanning the board
     */
    public double calculateScore(Board state) {
        int score = 0;

        for (int col = 0; col <= 3; col++) {
            for (int row = 0; row < Board.NUM_ROWS; row++) {

                //Previous player single horizontal counter checks

               if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 200;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 200;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 200;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 200;

                //Current player(player has to yet to make a move) single horizontal counter checks

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 50;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 50;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score += 50;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 50;
            }

        }
        return score;

    }
}