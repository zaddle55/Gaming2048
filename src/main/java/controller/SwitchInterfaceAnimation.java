package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import util.Direction;

import java.util.List;

public class SwitchInterfaceAnimation extends Animation{

    private static double duration = 200;
    private static double delay = 70;
    private static double distance = 0;

    private Direction direction;

    public SwitchInterfaceAnimation(List<Node> nodes, Direction direction) {
        super();
        this.nodes = nodes;
        this.direction = direction;
    }

    public SwitchInterfaceAnimation(List<Node> nodes, Direction direction, double duration, double delay, double distance) {
        super();
        this.nodes = nodes;
        this.direction = direction;
        this.duration = duration;
        this.delay = delay;
        this.distance = distance;
    }

    @Override
    public void makeTransition() {
        for (Node node : nodes) {
            if (distance == 0) distance = node.getBoundsInLocal().getWidth() * (direction == Direction.LEFT ? 1 : -1);
            TranslateTransition transition = new TranslateTransition(Duration.millis(duration), node);
            transition.setByX(-distance);
            transition.setDelay(Duration.millis(delay));
            transitions.add(transition);
        }
        groupTransition = new ParallelTransition(transitions.toArray(new javafx.animation.Transition[0]));
    }
}
