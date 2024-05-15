import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestScaleTransition extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scale Transition Example");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300);

        Rectangle rect = new Rectangle(100, 100, 100, 100);
        rect.setFill(Color.RED);

        Circle circle = new Circle(200, 200, 50);
        circle.setFill(Color.BLUE);

        Pane pane = new StackPane();
        pane.getChildren().add(rect);
        pane.getChildren().add(circle);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), pane);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setCycleCount(50);
        scaleTransition.setAutoReverse(true);

        Label label = new Label("Hello");

        Pane pane2 = new StackPane();
        pane2.getChildren().add(label);

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(2000), pane2);
        scaleTransition2.setFromX(0);
        scaleTransition2.setFromY(0);
        scaleTransition2.setToX(1);
        scaleTransition2.setToY(1);
        scaleTransition2.setCycleCount(50);
        scaleTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, scaleTransition2);
        parallelTransition.play();

//        ParallelTransition parallelTransition = new ParallelTransition();
//        parallelTransition.getChildren().addAll(scaleTransition, scaleTransition2);
//        parallelTransition.play();

        root.getChildren().add(pane);
        root.getChildren().add(pane2);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
