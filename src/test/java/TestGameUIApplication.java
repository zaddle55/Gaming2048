
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.GameUI;
import util.Board;
import util.Coordination;

public class TestGameUIApplication extends Application {

    public Board board;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/FXView/GameUI.fxml"));
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.TRANSPARENT);

        Node displayPane = scene.lookup("#displayPane");

        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image("/assets/titleIcon/favicon-32x32.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setScene(scene);
        primaryStage.show();

//        GameUI gameUI = new GameUI();
//
//        gameUI.initGamePane((AnchorPane) scene.lookup("#gamePane"));
//
//        board = new Board(0, 4, 0);
//        board.init();
//        gameUI.draw(board, (AnchorPane) scene.lookup("#gamePane"), 4);
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Platform.runLater(() -> {
//                    board.slip(0);
//                    gameUI.draw(board, (AnchorPane) scene.lookup("#gamePane"), 4);
//                });
//            }
//        }).start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
