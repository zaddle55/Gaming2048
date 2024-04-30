package ui;

import java.util.ArrayList;
import java.util.List;

public class TileList {
    private List<Tile> tiles;
    private int size;
    public TileList(int size) {
        this.size = size;
        tiles = new ArrayList<>();
    }

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void remove(Tile tile) {
        tiles.remove(tile);
    }

    public Tile get(int index) {
        return tiles.get(index);
    }

    public Tile get(int hIndex, int vIndex) {
        for (Tile tile : tiles) {
            if (tile.gethIndex() == hIndex && tile.getvIndex() == vIndex) {
                return tile;
            }
        }
        return null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int size() {
        return tiles.size();
    }
}
