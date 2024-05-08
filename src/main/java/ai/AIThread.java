package ai;

import controller.GameUI;
import model.Grid;
import util.Direction;

public class AIThread implements Runnable{
    protected Grid grid;
    protected GameUI gameThread;
    protected boolean endFlag = false;

    public AIThread(Grid grid, GameUI gameThread) {
        this.grid = grid;
        this.gameThread = gameThread;
    }

    protected Direction getDirection() {
//      return nextDirection(Grid grid);
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
            move(getDirection());
            updateGrid();
            updateEndFlag();
        }
    }
}
