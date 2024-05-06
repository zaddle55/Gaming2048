package ui;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import util.Coordination;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    protected static double duration = 100;
    protected static double delay = 0;
    protected Node node;
    protected Tile tile;
    protected List<Node> nodes;
    protected List<Tile> tiles;
    protected Coordination coordinationTool;
    protected Transition monoTransition;
    protected List<Transition> transitions;
    protected ParallelTransition groupTransition;

    public Animation(List<Node> nodes, List<Tile> tiles, Coordination coordinationTool) {
        this.nodes = nodes;
        this.tiles = tiles;
        this.coordinationTool = coordinationTool;
        this.transitions = new ArrayList<>();
    }


    public Animation(List<Node> nodes, Coordination coordinationTool) {
        this.nodes = nodes;
        this.tiles = nodesToTiles(nodes);
        this.coordinationTool = coordinationTool;
        this.transitions = new ArrayList<>();
    }

    public Animation(Node node, Coordination coordinationTool) {
        this.node = node;
        this.coordinationTool = coordinationTool;
        this.transitions = new ArrayList<>();
    }

    public Animation(Tile tile, Coordination coordinationTool) {
        this.tile = tile;
        this.coordinationTool = coordinationTool;
        this.transitions = new ArrayList<>();
    }

    public Animation() {
        this.nodes = new ArrayList<>();
        this.tiles = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    public static double getDelay() {
        return delay;
    }

    public static void setDelay(double delay) {
        Animation.delay = delay;
    }

    public static double getDuration() {
        return duration;
    }

    public static void setDuration(double duration) {
        Animation.duration = duration;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    private static Node tileToNode(Tile tile) {
        return tile;
    }

    protected static List<Node> tilesToNodes(List<Tile> tiles) {
        List<Node> nodes = new ArrayList<>();
        for (Tile tile : tiles) {
            nodes.add(tileToNode(tile));
        }
        return nodes;
    }

    protected static List<Tile> nodesToTiles(List<Node> nodes) {
        List<Tile> tiles = new ArrayList<>();
        for (Node node : nodes) {
            tiles.add((Tile) node);
        }
        return tiles;
    }

    protected static List<Node> tilesToNodes(TileList tiles) {
        List<Node> nodes = new ArrayList<>();
        for (Tile tile : tiles.getTiles()) {
            nodes.add(tileToNode(tile));
        }
        return nodes;
    }

    public abstract void makeTransition();

    public void play(CombineType type) {
        makeTransition();
        switch (type) {
            case MONO:
                monoTransition.play();
                break;
            case GROUP:
                groupTransition.play();
                break;
        }
    }

}

