package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import model.Tile;
import model.TileList;
import util.Coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description:
 *
 */
public abstract class Animation {

    // 动画默认持续时间
    protected static double duration = 90;
    // 动画默认延迟
    protected static double delay = 0;
    protected Node node;
    protected Tile tile;
    protected List<Node> nodes;
    protected List<Tile> tiles;

    // 坐标转换工具
    protected Coordination coordinationTool;
    // 单动画
    protected Transition monoTransition;
    // 动画组List
    protected List<Transition> transitions;
    // 组合动画
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

    // 动画实现
    public abstract void makeTransition();

    // 动画播放
    public void play(CombineType type) {

        switch (type) {
            case MONO: // 单动画模式
                monoTransition.play();
                break;
            case GROUP: // 组合动画模式
                groupTransition.play();
                break;
        }
    }

    public void setOnFinished(EventHandler<ActionEvent> finishEvent) {

        if (groupTransition != null) {
            groupTransition.setOnFinished(finishEvent);
        }

        if (monoTransition != null) {
            monoTransition.setOnFinished(finishEvent);
        }

    }

    // 静态枚举类，用于区分不同动画组合模式
    static enum CombineType {
        MONO, GROUP
    }

}

