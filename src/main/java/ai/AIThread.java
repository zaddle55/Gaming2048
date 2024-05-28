package ai;

import controller.GameUI;
import model.Grid;
import util.Direction;
import static java.lang.Thread.sleep;

public class AIThread implements Runnable {
    protected Grid grid;
    protected GameUI gameThread;
    public boolean endFlag = false;
    private double evaluationScore = 0;
    private final double monoWeight = 0.4; // 单调性权重
    private final double smoothWeight = 1.0; // 平滑性权重
    private final double emptyWeight = 0.7; // 总空格数权重
    private final double islandWeight = 0.1; // 孤立空格数权重

    public AIThread(Grid grid, GameUI gameThread) {
        this.grid = grid;
        this.gameThread = gameThread;
    }

    protected double evaluate(Direction d) {
        int emptyTile = 0;
        for (int i=0; i < grid.getSize(); i++) {
            for (int j=0; j < grid.getSize(); j++) {
                if (grid.getTileGrid()[i][j] == null) {
                    emptyTile += 1;
                }
            }
        }
        evaluationScore += emptyTile * emptyWeight;
        return evaluationScore;
    }
    protected Direction getDirection() {
        int directionNum = 0;
        if (evaluate(Direction.UP) < evaluate(Direction.DOWN)) {
            directionNum = 1;
        }
        if (evaluate(Direction.DOWN) < evaluate(Direction.LEFT)) {
            directionNum = 2;
        }
        if (evaluate(Direction.LEFT) < evaluate(Direction.RIGHT)) {
            directionNum = 3;
        }
        if (directionNum == 0) {
            return Direction.UP;
        } else if (directionNum == 1) {
            return Direction.DOWN;
        } else if (directionNum == 2) {
            return Direction.LEFT;
        } else if (directionNum == 3) {
            return Direction.RIGHT;
        }
        return null;
    }
    protected void move(Direction direction) {
        gameThread.simulateMove(direction);
    }

    protected void updateGrid() {
        grid = gameThread.getGrid();
    }

    protected void updateEndFlag() {
        endFlag = !GameUI.isAuto || GameUI.isWin || GameUI.isLose;
    }

    @Override
    public void run() {

        while (!endFlag) {

            try {
                sleep(100);
                updateGrid();
                updateEndFlag();
                move(getDirection());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
