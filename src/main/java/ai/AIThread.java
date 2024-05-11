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
        switch (direction) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
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
