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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateGrid();
            updateEndFlag();
            try {
                move(AlphaDuo.findBestMove(grid, 5));
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}