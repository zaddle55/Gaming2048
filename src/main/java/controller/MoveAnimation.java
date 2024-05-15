package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import model.Tile;
import model.Grid;
import util.Coordination;
import util.Direction;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MoveAnimation extends Animation {
    private Direction direction;
    private Map<Tile, Double> distanceMap;
    private Set<Tile> tiles;


    public MoveAnimation(Direction direction, Map<Tile, Double> distanceMap) {
        this.direction = direction;
        this.distanceMap = distanceMap;
        this.tiles = distanceMap.keySet();
    }


    // 移动动画具体实现
    public void makeTransition() {

        for (Tile tile : tiles) {
            double distance = distanceMap.get(tile);

            TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
            switch (direction) {
                case DOWN:
                    transition.setByY(distance);
                    break;
                case UP:
                    transition.setByY(-distance);
                    break;
                case RIGHT:
                    transition.setByX(distance);
                    break;
                case LEFT:
                    transition.setByX(-distance);
                    break;
            }
            transitions.add(transition);
        }

        groupTransition = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
    }

    public ParallelTransition getGroupTransition() {
        return groupTransition;
    }

}
