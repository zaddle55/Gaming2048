package ai;

import controller.GameUI;
import model.Grid;
import util.Direction;
import java.util.Random;

import static ai.AlphaDuo.setDirection;
import static java.lang.Thread.sleep;

public class AIThread implements Runnable {
    protected Grid grid;
    protected GameUI gameThread;
    public boolean endFlag = false;

    public AIThread(Grid grid, GameUI gameThread) {
        this.grid = grid;
        this.gameThread = gameThread;
    }

    protected Direction getDirection() {
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
        while (!endFlag) {
            try {
                sleep(200);
                updateGrid();
                updateEndFlag();
                move(getDirection());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}