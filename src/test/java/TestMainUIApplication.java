import controller.MainUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.Executors;

public class TestMainUIApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/FXView/MainUI.fxml"));

//        primaryStage.setTitle("Reach 2048");
        Scene scene = new Scene(root, 700, 500);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new javafx.scene.image.Image("assets/titleIcon/favicon-32x32.png"));

        primaryStage.initStyle(StageStyle.UNIFIED);

        primaryStage.setMaximized(false);

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        MainUI.run();
    }
}
