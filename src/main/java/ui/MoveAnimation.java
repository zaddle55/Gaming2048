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
        TileList tileList = toTileList(tiles);
        switch (direction) {
            case UP:

                break;
        }
    }

    private static TileList toTileList(List<Tile> tiles) {
        TileList tileList = new TileList(tiles.size());
        for (Tile tile : tiles) {
            tileList.add(tile);
        }
        return tileList;
    }

    private boolean isMerge(Tile tile, TileList tiles) {
//        for (Tile t : tiles.getTiles()) {
//            if (t.gethIndex() == tile.gethIndex() && t.getvIndex() == tile.getvIndex()) {
//                return true;
//            }
//        }
        return false;
    }
}
