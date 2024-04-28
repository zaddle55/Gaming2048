import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TestGridPanel extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        int gridSize = 10;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(50, 50);

                // Draw horizontal grid line
                Line hLine = new Line();
                hLine.setStartX(0);
                hLine.setEndX(50);
                hLine.setStartY(50);
                hLine.setEndY(50);

                // Draw vertical grid line
                Line vLine = new Line();
                vLine.setStartX(50);
                vLine.setEndX(50);
                vLine.setStartY(0);
                vLine.setEndY(50);

                pane.getChildren().addAll(hLine, vLine);
                gridPane.add(pane, i, j);
            }
        }

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
