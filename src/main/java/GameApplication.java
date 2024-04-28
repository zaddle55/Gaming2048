import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.GameUI;
import util.Board;

public class GameApplication extends Application {
    private int size;
    private int mode;
    private Board board;

    public GameApplication(int size, int mode) {
        super();
        this.size = size;
        this.mode = mode;;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXView/GameUI.fxml"));
        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image("/assets/titleIcon/favicon-32x32.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setScene(scene);
        primaryStage.show();

        GameUI gameUI = new GameUI();

        GameUI.initGamePane((AnchorPane) scene.lookup("#gamePane"));

        board = new Board(0, size, mode);
        board.init();
        GameUI.draw(board, (AnchorPane) scene.lookup("#gamePane"));

    }
}
