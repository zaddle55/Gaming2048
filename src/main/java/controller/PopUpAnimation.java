package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import model.Grid;
import model.Tile;

import java.util.ArrayList;
import java.util.List;

public class PopUpAnimation extends Animation{

    private static final double duration = 160;
    public PopUpAnimation(Grid grid) {
        Boolean[][] isNew = grid.getIsNew();
        for (int i = 0; i < isNew.length; i++) {
            for (int j = 0; j < isNew[i].length; j++) {
                if (isNew[i][j]) {
                    Tile tile = grid.getTileGrid()[i][j];
                    tiles.add(tile);
                }
            }
        }
        transitions = new ArrayList<>();
    }

    @Override
    public void makeTransition() {
        for (Tile tile : tiles) {
            ScaleTransition transition = new ScaleTransition(Duration.millis(duration), tile);
            transition.setFromX(0.5);
            transition.setFromY(0.5);
            transition.setToX(1);
            transition.setToY(1);
            transitions.add(transition);
        }

        groupTransition = new ParallelTransition();
        groupTransition.getChildren().addAll(transitions);
        // 为我解释上面代码的作用 ：
    }

    public ParallelTransition getGroupTransition() {
        return groupTransition;
    }
}
