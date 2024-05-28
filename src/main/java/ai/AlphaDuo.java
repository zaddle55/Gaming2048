package ai;

import controller.GameUI;
import util.Direction;

/**
 * AlphaDuo
 * @version: 1.0.0
 * @description: AlphaDuo is a class that represents the AI of the game, "duo" means two, which means that the AI is designed for 2048 which uses "2" grids to make larger binary numbers.
 * @function: Used to play the game automatically or to provide hints to the player.
 * @Implementation: AlphaDuo uses the Minimax and Alpha-Beta algorithms to simulate the game and make decisions.
 */
public class AlphaDuo {
    protected static double evaluationScore = 0;
    protected static final double monotonyWeight = 0.4; // 单调性权重
    protected static final double smoothWeight = 1.0; // 平滑性权重
    protected static final double emptyWeight = 0.7; // 总空格数权重
    protected static final double islandWeight = 0.1; // 孤立空格数权重
    protected static int directionNum = 0;

    protected static double evaluate(Direction d) {
        int monotonyRowL = 0;
        int monotonyRowR = 0;
        int monotonyColumnU = 0;
        int monotonyColumnD = 0;
        for (int j=0; j < GameUI.getGrid().getSize(); j++) {
            for (int i=0; i < GameUI.getGrid().getSize(); i++) {
                for (int a=0; j+a < GameUI.getGrid().getSize()-1; a++) {
                    if ((GameUI.getGrid().getBoard()[i][j+a] < GameUI.getGrid().getBoard()[i][j+a+1]) || (GameUI.getGrid().getBoard()[i][j+a] > GameUI.getGrid().getBoard()[i][j+a+1])) {
                        monotonyRowL += 1;
                    }
                }
                for (int b=0; i+b < GameUI.getGrid().getSize() - 1; b++) {
                    if (GameUI.getGrid().getBoard()[i+b][j] < GameUI.getGrid().getBoard()[i+b+1][j]) {
                        monotonyColumnD += 1;
                    } else if (GameUI.getGrid().getBoard()[i+b][j] > GameUI.getGrid().getBoard()[i+b+1][j]) {
                        monotonyColumnU += 1;
                    }
                }
            }
        }
        evaluationScore += (monotonyRowL * monotonyWeight) + (monotonyRowR * monotonyWeight) + (monotonyColumnU * monotonyWeight) + (monotonyColumnD * monotonyWeight);


        int emptyTile = 0;
        for (int i=0; i < GameUI.getGrid().getSize(); i++) {
            for (int j=0; j < GameUI.getGrid().getSize(); j++) {
                if (GameUI.getGrid().getBoard()[i][j] == 0) {
                    emptyTile += 1;
                }
            }
        }
        evaluationScore += emptyTile * emptyWeight;




        return evaluationScore;
    }
    protected static int setDirection() {
        Direction direction = Direction.UP;
        if (evaluate(Direction.DOWN) > evaluate(direction)) {
            direction = Direction.DOWN;
            directionNum = 1;
        }
        if (evaluate(Direction.LEFT) > evaluate(direction)) {
            direction = Direction.LEFT;
            directionNum = 2;
        }
        if (evaluate(Direction.RIGHT) > evaluate(direction)) {
            directionNum = 3;
        }
        return directionNum;
    }
}