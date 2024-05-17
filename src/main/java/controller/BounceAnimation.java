package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import model.Grid;
import model.Tile;

import java.util.List;

public class BounceAnimation extends Animation{

    private static final double duration = 50;

    private static final double SCALE_FACTOR = 0.18;

    public BounceAnimation(Grid grid) {
        Boolean[][] tar = grid.getIsMerge();
        for (int i = 0; i < tar.length; i++) {
            for (int j = 0; j < tar[i].length; j++) {
                if (tar[i][j]) {
                    Tile tile = grid.getTileGrid()[i][j];
                    tiles.add(tile);
                }
            }
        }
    }

    @Override
    public void makeTransition() {
        for (Tile tile : tiles) {
            // 目标Tile比例放大
            ScaleTransition transition = new ScaleTransition(Duration.millis(duration), tile);
            transition.setByX(SCALE_FACTOR);
            transition.setByY(SCALE_FACTOR);

            ScaleTransition transition2 = new ScaleTransition(Duration.millis(duration), tile);
            transition2.setByX(-SCALE_FACTOR);
            transition2.setByY(-SCALE_FACTOR);
            transition.setCycleCount(1);
            transition2.setCycleCount(1);
            SequentialTransition parallelTransition = new SequentialTransition(transition, transition2);
            transitions.add(parallelTransition);
        }

        groupTransition = new ParallelTransition();
        groupTransition.getChildren().addAll(transitions);
    }

    public ParallelTransition getGroupTransition() {
        return groupTransition;
    }
}
