import java.util.Random;


//attempt to lower difficulty of AI to intermediate level
//depth of future state computations decreased and simplified cost calculations of boards in future states
//changed costs to make AI less aggressive to win and block slightly more often
public class testAI implements Player {
    static int MAX_DEPTH = 6;

    //Remember to pass in a clone of the state
    public int getMove(Board state) {
        System.out.println("SIMULATION");
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

    public int calculateMove(Board state) {
        double maxScore = Integer.MIN_VALUE;
        int move = 0;
        for (int col = 0; col < Board.NUM_COLS; col++) {
            if (!state.isColumnFull(col)) {
                Board clone = state.clone();
                clone.simDropToken(col);
                double score = alphabeta(clone, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                System.out.println("SCORE " + score);
                if (score > maxScore) {
                    maxScore = score;
                    move = col;
                    if (score == 1) break;
                }
            }
        }
        return move;
    }

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

    public double calculateScore(Board state) {
        int score = 0;

        for (int col = 0; col <= 3; col++) {
            for (int row = 0; row < Board.NUM_ROWS; row++) {

                //Previous player single horizontal counter checks

                if (state.getBoard()[row][col] == state.getCurrentPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 150;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getCurrentPlayer() &&
                    state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 150;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getCurrentPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score -= 150;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getCurrentPlayer()) score -= 150;

                //Current player(player has to yet to make a move) single horizontal counter checks

                if (state.getBoard()[row][col] == state.getPreviousPlayer() && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 50;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == state.getPreviousPlayer() &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == Board.EMPTY) score += 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == state.getPreviousPlayer() && state.getBoard()[row][col + 3] == Board.EMPTY) score += 100;

                if (state.getBoard()[row][col] == Board.EMPTY && state.getBoard()[row][col + 1] == Board.EMPTY &&
                        state.getBoard()[row][col + 2] == Board.EMPTY && state.getBoard()[row][col + 3] == state.getPreviousPlayer()) score += 50;
            }

        }
        return score;

    }


    //Insert heuristic function
}