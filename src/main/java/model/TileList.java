package model;

import model.Tile;

import java.util.ArrayList;
import java.util.List;

public class TileList {
    private List<Tile> tiles;

    public TileList() {

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


    public List<Tile> getTiles() {
        return tiles;
    }

    public int size() {
        return tiles.size();
    }
}
