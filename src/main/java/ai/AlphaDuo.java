package ai;

import controller.GameUI;
import util.*;

/**
 * AlphaDuo
 * @version: 1.0.0
 * @description: AlphaDuo is a class that represents the AI of the game, "duo" means two, which means that the AI is designed for 2048 which uses "2" grids to make larger binary numbers.
 * @function: Used to play the game automatically or to provide hints to the player.
 * @Implementation: AlphaDuo uses the Minimax and Alpha-Beta algorithms to simulate the game and make decisions.
 */
public class AlphaDuo {
    // 为每种移动方向“评分”，哪种方向评分最高，采取哪种方向移动
    protected static double upEvaluationScore = 0;
    protected static double downEvaluationScore = 0;
    protected static double leftEvaluationScore = 0;
    protected static double rightEvaluationScore = 0;
    protected static final double monotonyWeight = 0.5; // 单调性权重
    protected static final double smoothWeight = -0.2; // 平滑性权重
    protected static final double emptyWeight = 0.5; // 总空格数权重
    protected static int directionNum = 0;

    protected static void evaluate() {
        upEvaluationScore = 0;
        downEvaluationScore = 0;
        leftEvaluationScore = 0;
        rightEvaluationScore = 0;
        // 1. 单调性评估
        boolean isIncrease = false;
        int numOfMonotonyTiles;
        int monotonyL = 0;
        int monotonyR = 0;
        int monotonyU = 0;
        int monotonyD = 0;
        for (int i = 0; i < GameUI.getGrid().getSize(); i++) {
            for (int j = 0; j+1 < GameUI.getGrid().getSize(); j += numOfMonotonyTiles) {
                numOfMonotonyTiles = 0;
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i][j] < GameUI.getGrid().getBoard()[i][j+1])) {
                    isIncrease = true;
                } else if ((GameUI.getGrid().getBoard()[i][j+1] != 0) && (GameUI.getGrid().getBoard()[i][j] > GameUI.getGrid().getBoard()[i][j+1])) {
                    isIncrease = false;
                }
                if (isIncrease) {
                    for (int a = 1; j+a+1 < GameUI.getGrid().getSize(); a++) {
                        if ((GameUI.getGrid().getBoard()[i][j+a] != 0) && (GameUI.getGrid().getBoard()[i][j+a] < GameUI.getGrid().getBoard()[i][j+a+1])) {
                            numOfMonotonyTiles += 1;
                            monotonyR += 1;
                        } else {
                            isIncrease = false;
                            break;
                        }
                    }
                } else if (!isIncrease) {
                    for (int a = 1; j+a+1 < GameUI.getGrid().getSize(); a++) {
                        if ((GameUI.getGrid().getBoard()[i][j+a+1] != 0) && (GameUI.getGrid().getBoard()[i][j+a] > GameUI.getGrid().getBoard()[i][j+a+1])) {
                            numOfMonotonyTiles += 1;
                            monotonyL += 1;
                        } else {
                            isIncrease = true;
                            break;
                        }
                    }
                }
                if (numOfMonotonyTiles == 0) {
                    numOfMonotonyTiles += 1;
                }
            }
        }
        leftEvaluationScore += (monotonyL * monotonyWeight);
        rightEvaluationScore += (monotonyR * monotonyWeight);
        for (int j = 0; j < GameUI.getGrid().getSize(); j++) {
            for (int i = 0; i+1 < GameUI.getGrid().getSize(); i += numOfMonotonyTiles) {
                numOfMonotonyTiles = 0;
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i][j] < GameUI.getGrid().getBoard()[i+1][j])) {
                    isIncrease = true;
                } else if ((GameUI.getGrid().getBoard()[i+1][j] != 0) && (GameUI.getGrid().getBoard()[i][j] > GameUI.getGrid().getBoard()[i+1][j])) {
                    isIncrease = false;
                }
                if (isIncrease) {
                    for (int a = 1; i+a+1 < GameUI.getGrid().getSize(); a++) {
                        if ((GameUI.getGrid().getBoard()[i+a][j] != 0) && (GameUI.getGrid().getBoard()[i+a][j] < GameUI.getGrid().getBoard()[i+a+1][j])) {
                            numOfMonotonyTiles += 1;
                            monotonyD += 1;
                        } else {
                            isIncrease = false;
                            break;
                        }
                    }
                } else if (!isIncrease) {
                    for (int a = 1; i+a+1 < GameUI.getGrid().getSize(); a++) {
                        if ((GameUI.getGrid().getBoard()[i+a+1][j] != 0) && (GameUI.getGrid().getBoard()[i+a][j] > GameUI.getGrid().getBoard()[i+a+1][j])) {
                            numOfMonotonyTiles += 1;
                            monotonyU += 1;
                        } else {
                            isIncrease = true;
                            break;
                        }
                    }
                }
                if (numOfMonotonyTiles == 0) {
                    numOfMonotonyTiles += 1;
                }
            }
        }
        upEvaluationScore += (monotonyU * monotonyWeight);
        downEvaluationScore += (monotonyD * monotonyWeight);

        // 2. 平滑性评估
        int smoothU = 0;
        int smoothD = 0;
        int smoothL = 0;
        int smoothR = 0;
        for (int i=1; i < GameUI.getGrid().getSize(); i++) {
            for (int j=0; j < GameUI.getGrid().getSize(); j++) {
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i-1][j] != 0)) {
                    smoothU += (Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i-1][j]) * Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i-1][j]) / GameUI.getGrid().getSize());
                }
            }
        }
        for (int i=0; i < GameUI.getGrid().getSize() - 1; i++) {
            for (int j=0; j < GameUI.getGrid().getSize(); j++) {
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i+1][j] != 0)) {
                    smoothD += (Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i+1][j]) * Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i+1][j]) / GameUI.getGrid().getSize());
                }
            }
        }
        for (int i=0; i < GameUI.getGrid().getSize(); i++) {
            for (int j=1; j < GameUI.getGrid().getSize(); j++) {
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i][j-1] != 0)) {
                    smoothL += (Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i][j-1]) * Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i][j-1]) / GameUI.getGrid().getSize());
                }
            }
        }
        for (int i=0; i < GameUI.getGrid().getSize(); i++) {
            for (int j=0; j < GameUI.getGrid().getSize() - 1; j++) {
                if ((GameUI.getGrid().getBoard()[i][j] != 0) && (GameUI.getGrid().getBoard()[i][j+1] != 0)) {
                    smoothR += (Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i][j+1]) * Math.abs(GameUI.getGrid().getBoard()[i][j] - GameUI.getGrid().getBoard()[i][j+1]) / GameUI.getGrid().getSize());
                }
            }
        }
        upEvaluationScore += smoothU * smoothWeight;
        downEvaluationScore += smoothD * smoothWeight;
        leftEvaluationScore += smoothL * smoothWeight;
        rightEvaluationScore += smoothR * smoothWeight;
        int equalU = 0;
        int equalD = 0;
        int equalL = 0;
        int equalR = 0;


        // 3. 总空格数评估
        int numOfEmptyTiles;
        int emptyRow = 0;
        int emptyColumn = 0;
        for (int i = 0; i < GameUI.getGrid().getSize(); i++) {
            for (int j = 0; j < GameUI.getGrid().getSize(); j += numOfEmptyTiles) {
                numOfEmptyTiles = 0;
                for (int a = 0; j+a < GameUI.getGrid().getSize(); a++) {
                    if (GameUI.getGrid().getBoard()[i][j+a] == 0) {
                        numOfEmptyTiles += 1;
                        emptyRow += 1;
                    } else {
                        break;
                    }
                }
                if (numOfEmptyTiles == 0) {
                    numOfEmptyTiles += 1;
                }
            }
        }
        leftEvaluationScore += (emptyRow * emptyWeight);
        rightEvaluationScore += (emptyRow * emptyWeight);
        for (int j = 0; j < GameUI.getGrid().getSize(); j++) {
            for (int i = 0; i < GameUI.getGrid().getSize(); i += numOfEmptyTiles) {
                numOfEmptyTiles = 0;
                for (int a = 0; i+a < GameUI.getGrid().getSize(); a++) {
                    if (GameUI.getGrid().getBoard()[i+a][j] == 0) {
                        numOfEmptyTiles += 1;
                        emptyColumn += 1;
                    } else {
                        break;
                    }
                }
                if (numOfEmptyTiles == 0) {
                    numOfEmptyTiles += 1;
                }
            }
        }
        upEvaluationScore += (emptyColumn * emptyWeight);
        downEvaluationScore += (emptyColumn * emptyWeight);
    }
    protected static double evaluateDirection(Direction d) {
        upEvaluationScore = 0;
        downEvaluationScore = 0;
        leftEvaluationScore = 0;
        rightEvaluationScore = 0;
        evaluate();
        if (d == Direction.UP) {
            return upEvaluationScore;
        } else if (d == Direction.DOWN) {
            return downEvaluationScore;
        } else if (d == Direction.LEFT) {
            return leftEvaluationScore;
        } else if (d == Direction.RIGHT) {
            return rightEvaluationScore;
        } else {
            return 0;
        }
    }
    protected static int setDirection() {
        Direction direction = Direction.UP;
        if (evaluateDirection(Direction.DOWN) >= evaluateDirection(direction)) {
            direction = Direction.DOWN;
            directionNum = 1;
        }
        if (evaluateDirection(Direction.LEFT) >= evaluateDirection(direction)) {
            direction = Direction.LEFT;
            directionNum = 2;
        }
        if (evaluateDirection(Direction.RIGHT) >= evaluateDirection(direction)) {
            directionNum = 3;
        }
        return directionNum;
    }
}