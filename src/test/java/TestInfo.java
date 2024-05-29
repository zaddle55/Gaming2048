import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TestInfo extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // This is a test class
        GridPane root = new GridPane();
        AnchorPane infoPane = FXMLLoader.load(getClass().getResource("/FXView/error.fxml"));
        primaryStage.setTitle("Test Info");
        root.add(infoPane, 0, 0);
        root.setAlignment(javafx.geometry.Pos.CENTER);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
