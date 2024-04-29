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

public class GameApplication{

        public static void main(String[] args) {
            GameUI.init(4,0);
            GameUI.run();
        }
}
