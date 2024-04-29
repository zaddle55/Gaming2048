package ui;

import util.Board;
import util.Direction;

import java.util.List;

public class MoveAnimation extends Animation{
    private Direction direction;
    private Board stageBoard;

    public MoveAnimation(List<Tile> tiles, Direction direction, Board board) {
        super(tiles);
        this.direction = direction;
        this.stageBoard = board;
    }

    public void makeTransition() {
        // TODO
    }



    private boolean isMerge(Tile tile) {
        return false;
    }
}
