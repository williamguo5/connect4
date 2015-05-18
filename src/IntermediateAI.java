import java.util.Random;


public class IntermediateAI implements Player {
    static int MAX_DEPTH = 8;

    //Remember to pass in a clone of the state
    public int getMove(Board state) {
        System.out.println("SIMULATION");
//        System.out.println(1f);
//        return 0;
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
//        if (move == 0) {
//            Random randomGenerator = new Random();
//            int randomNumber = randomGenerator.nextInt(3);
//            move = randomNumber;
//        }
        return move;
    }

    public double alphabeta(Board state, int depth, double alpha, double beta, boolean maximisingPlayer) {
        if (depth == 0 || state.isGameOver()) {
            double score = 0;
            if (maximisingPlayer) {
                score = -1;
            } else if (!maximisingPlayer) {
                score = 1;
            }
            return score/(MAX_DEPTH - depth + 1);
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


    //Insert heuristic function
}
