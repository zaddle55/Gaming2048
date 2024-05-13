package ai;

import controller.GameUI;
import model.Grid;
import util.Direction;

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
        // 随机生成方向
        int direction = (int) (Math.random() * 4);
        if (direction == 0) {
            return Direction.UP;
        } else if (direction == 1) {
            return Direction.DOWN;
        } else if (direction == 2) {
            return Direction.LEFT;
        } else if (direction == 3) {
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
                move(getDirection());
                updateGrid();
                updateEndFlag();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
