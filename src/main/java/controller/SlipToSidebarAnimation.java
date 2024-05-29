package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SlipToSidebarAnimation extends Animation{

    private static double SLIP_DISTANCE;

    public SlipToSidebarAnimation(AnchorPane main, AnchorPane sidebar) {
        super(main, sidebar);
        SLIP_DISTANCE = sidebar.getPrefWidth();
    }

    public SlipToSidebarAnimation(AnchorPane main, AnchorPane sidebar, boolean isReverse) {
        super(main, sidebar);
        SLIP_DISTANCE = sidebar.getPrefWidth() * (isReverse ? -1 : 1);
    }

    @Override
    public void makeTransition() {
        for (int i = 0; i < nodes.size(); i++) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(duration), nodes.get(i));
            transition.setByX(-SLIP_DISTANCE);
            transitions.add(transition);
        }
        groupTransition = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
    }


}
