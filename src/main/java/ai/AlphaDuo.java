package ai;

import model.Grid;
import util.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaDuo {

    /**
     * Player vs Computer enum class
     */
    public enum Player {
        /**
         * Computer
         */
        COMPUTER,

        /**
         * User
         */
        USER
    }

    /**
     * Method that finds the best next move.
     *
     * @param theBoard
     * @param depth
     * @return
     * @throws CloneNotSupportedException
     */
    public static Direction findBestMove(Grid theBoard, int depth) throws CloneNotSupportedException {
        //Map<String, Object> result = minimax(theBoard, depth, Player.USER);

        Map<String, Object> result = alphabeta(theBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);

        System.out.println("Score: "+result.get("Score"));

        return (Direction)result.get("Direction");
    }

    /**
     * Finds the best move by using the Minimax algorithm.
     *
     * @param theBoard
     * @param depth
     * @param player
     * @return
     * @throws CloneNotSupportedException
     */
    private static Map<String, Object> minimax(Grid theBoard, int depth, Player player) throws CloneNotSupportedException {
        Map<String, Object> result = new HashMap<>();

        Direction bestDirection = null;
        int bestScore;

        if(depth==0 || theBoard.isOver()) {
            bestScore=heuristicScore(theBoard.getScore(),theBoard.getNumberOfEmptyCells(),calculateClusteringScore(theBoard.getBoard()));
        }
        else {
            if(player == Player.USER) {
                bestScore = Integer.MIN_VALUE;

                for(Direction direction : Direction.values()) {
                    Grid newBoard = (Grid) theBoard.clone();

                    int points=newBoard.moveGrid(direction);

                    if(points==0 && newBoard.isEqual(theBoard.getBoard(), newBoard.getBoard())) {
                        continue;
                    }

                    Map<String, Object> currentResult = minimax(newBoard, depth-1, Player.COMPUTER);
                    int currentScore=((Number)currentResult.get("Score")).intValue();
                    if(currentScore>bestScore) { //maximize score
                        bestScore=currentScore;
                        bestDirection=direction;
                    }
                }
            }
            else {
                bestScore = Integer.MAX_VALUE;

                List<Integer> moves = theBoard.getEmptyCellIds();
                if(moves.isEmpty()) {
                    bestScore=0;
                }
                int[] possibleValues = {2, 4};

                int i,j;
                int[][] boardArray;
                for(Integer cellId : moves) {
                    i = cellId/theBoard.getSize();
                    j = cellId%theBoard.getSize();

                    for(int value : possibleValues) {
                        Grid newBoard = (Grid) theBoard.clone();
                        newBoard.setEmptyCell(i, j, value);

                        Map<String, Object> currentResult = minimax(newBoard, depth-1, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<bestScore) { //minimize best score
                            bestScore=currentScore;
                        }
                    }
                }
            }
        }

        result.put("Score", bestScore);
        result.put("Direction", bestDirection);

        return result;
    }

    /**
     * Finds the best move bay using the Alpha-Beta pruning algorithm.
     *
     * @param theBoard
     * @param depth
     * @param alpha
     * @param beta
     * @param player
     * @return
     * @throws CloneNotSupportedException
     */
    private static Map<String, Object> alphabeta(Grid theBoard, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException {
        Map<String, Object> result = new HashMap<>();

        Direction bestDirection = null;
        int bestScore;

        if(theBoard.isOver()) {
            if(theBoard.isWin()) {
                bestScore=Integer.MAX_VALUE; //highest possible score
            }
            else {
                bestScore=Math.min(theBoard.getScore(), 1); //lowest possible score
            }
        }
        else if(depth==0) {
            bestScore=heuristicScore(theBoard.getScore(),theBoard.getNumberOfEmptyCells(),calculateClusteringScore(theBoard.getBoard()));
        }
        else {
            if(player == Player.USER) {
                for(Direction direction : Direction.values()) {
                    Grid newBoard = (Grid) theBoard.clone();

                    int points=newBoard.moveGrid(direction);

                    if(points==0 && newBoard.isEqual(theBoard.getBoard(), newBoard.getBoard())) {
                        continue;
                    }

                    Map<String, Object> currentResult = alphabeta(newBoard, depth-1, alpha, beta, Player.COMPUTER);
                    int currentScore=((Number)currentResult.get("Score")).intValue();

                    if(currentScore>alpha) { //maximize score
                        alpha=currentScore;
                        bestDirection=direction;
                    }

                    if(beta<=alpha) {
                        break; //beta cutoff
                    }
                }

                bestScore = alpha;
            }
            else {
                List<Integer> moves = theBoard.getEmptyCellIds();
                int[] possibleValues = {2, 4};

                int i,j;
                abloop: for(Integer cellId : moves) {
                    i = cellId/theBoard.getSize();
                    j = cellId%theBoard.getSize();

                    for(int value : possibleValues) {
                        Grid newBoard = (Grid) theBoard.clone();
                        newBoard.setEmptyCell(i, j, value);

                        Map<String, Object> currentResult = alphabeta(newBoard, depth-1, alpha, beta, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<beta) { //minimize best score
                            beta=currentScore;
                        }

                        if(beta<=alpha) {
                            break abloop; //alpha cutoff
                        }
                    }
                }

                bestScore = beta;

                if(moves.isEmpty()) {
                    bestScore=0;
                }
            }
        }

        result.put("Score", bestScore);
        result.put("Direction", bestDirection);

        return result;
    }

    /**
     * Estimates a heuristic score by taking into account the real score, the
     * number of empty cells and the clustering score of the board.
     *
     * @param actualScore
     * @param numberOfEmptyCells
     * @param clusteringScore
     * @return
     */
    private static int heuristicScore(int actualScore, int numberOfEmptyCells, double clusteringScore) {
        int score = (int) (actualScore*4+Math.log(actualScore)*numberOfEmptyCells*numberOfEmptyCells*20 -clusteringScore*64);
        return Math.max(score, Math.min(actualScore, 1));
    }

    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     *
     * @param boardArray
     * @return
     */
    private static double calculateClusteringScore(int[][] boardArray) {
        double clusteringScore=0;

        int[] neighbors = {-1,0,1};

        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }

                //clusteringScore-=boardArray[i][j];

                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }

                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(calcLog2(boardArray[i][j])-calcLog2(boardArray[x][y]));
                        }

                    }
                }

                clusteringScore+= (double) sum /numOfNeighbors;
            }
        }

        return clusteringScore;
    }

    private static int calcLog2(int value) {
        if (value == 0) {
            return 0;
        }
        return (int) (Math.log(value)/Math.log(2));
    }

}
