package ai;

import controller.GameUI;
import model.Grid;
import org.jetbrains.annotations.Nullable;
import util.Direction;
import java.util.Random;

import static ai.AlphaDuo.*;
import static java.lang.Thread.sleep;

public class AIThread implements Runnable {
    protected Grid grid;
    protected GameUI gameThread;
    public boolean endFlag = false;

    public AIThread(Grid grid, GameUI gameThread) {
        this.grid = grid;
        this.gameThread = gameThread;
    }

    @Nullable
    public static Direction getDirection() {
        if (setDirection() == 0) {
            return Direction.UP;
        } else if (setDirection() == 1) {
            return Direction.DOWN;
        } else if (setDirection() == 2) {
            return Direction.LEFT;
        } else if (setDirection() == 3) {
            return Direction.RIGHT;
        } else {
            return null;
        }
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
        if (!endFlag) {
            Random random = new Random();
            int randomNum = random.nextInt(4);
            if (randomNum == 0) {
                move(Direction.UP);
            } else if (randomNum == 1) {
                move(Direction.DOWN);
            } else if (randomNum == 2) {
                move(Direction.LEFT);
            } else {
                move(Direction.RIGHT);
            }
        }
        while (!endFlag) {
            try {
                sleep(200);
                updateGrid();
                updateEndFlag();
                evaluate();
                move(getDirection());
                upEvaluationScore = 0;
                downEvaluationScore = 0;
                leftEvaluationScore = 0;
                rightEvaluationScore = 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}