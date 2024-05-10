package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import util.Direction;

import java.util.List;

public class SwitchInterfaceAnimation extends Animation{

    private static double duration = 200;

    private Direction direction;

    public SwitchInterfaceAnimation(List<Node> nodes, Direction direction) {
        super();
        this.nodes = nodes;
        this.direction = direction;
    }

    @Override
    public void makeTransition() {
        for (Node node : nodes) {
            double distance = node.getBoundsInLocal().getWidth() * (direction == Direction.LEFT ? 1 : -1);
            TranslateTransition transition = new TranslateTransition(Duration.millis(duration), node);
            transition.setByX(-distance);
            transitions.add(transition);
        }
        groupTransition = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
    }
}
