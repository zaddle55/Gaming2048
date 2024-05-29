import controller.ArchiveUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Save;

import java.util.concurrent.Executors;

import static model.Save.State.WIN;

public class TestArchiveUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {}

    public static void main(String[] args) {
        Executors.newSingleThreadExecutor().submit(ArchiveUI::run);
    }
}
