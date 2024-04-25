import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.GameUI;

public class TestGameUIApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/FXView/GameUI.fxml"));
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.getIcons().add(new javafx.scene.image.Image("assets/titleIcon/favicon-32x32.png"));
        stage.initStyle(StageStyle.UNIFIED);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
