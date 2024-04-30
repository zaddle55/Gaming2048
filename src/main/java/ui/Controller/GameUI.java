package ui.Controller;


import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.Tile;
import util.Coordination;
import util.Board;
import util.Direction;


import java.util.ArrayList;
import java.util.List;


public class GameUI extends Application {

    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button restartButton;
    @FXML
    private AnchorPane gameInterface;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label stepLabel;

    private static int size;
    private static int mode;
    private static Board board;
    private static AnchorPane curBlockPane;
    private static int score = 0;
    private static int step = 0;
    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        GameUI.size = size;
    }

    public static int getMode() {
        return mode;
    }

    public static void setMode(int mode) {
        GameUI.mode = mode;
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        GameUI.board = board;
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

        scene.getRoot().requestFocus();

        gamePane = (AnchorPane) scene.lookup("#gamePane");

        GameUI.initGamePane(gamePane, size);

        board = new Board(0, size, mode);
        board.init();

        curBlockPane = GameUI.draw(board, gamePane, size);

        scene.setOnKeyPressed(event -> {
            if        (event.getCode() == KeyCode.UP    || event.getCode() == KeyCode.W) {
                upAction();
            } else if (event.getCode() == KeyCode.DOWN  || event.getCode() == KeyCode.S) {
                downAction();
            } else if (event.getCode() == KeyCode.LEFT  || event.getCode() == KeyCode.A) {
                leftAction();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                rightAction();
            }
        });

    }


    @FXML
    public void restartAction() {
        board = new Board(0, size, mode);
        board.init();
        GameUI.draw(board, gamePane, size);
        upDateScore(scoreLabel, board);
        step = 0;
        upDateStep(stepLabel, board);
    }

    @FXML
    public void undoAction() {
        board.undo();
        GameUI.draw(board, gamePane, size);
        upDateScore(scoreLabel, board);
        step--;
        upDateStep(stepLabel, board);
    }

    @FXML
    public void upAction() {

        List<TranslateTransition> transitions = new ArrayList();

        for (Node tile : curBlockPane.getChildren()){
            Tile t = (Tile) tile;
            Coordination coordination = new Coordination(t.gethIndex(), t.getvIndex(), gamePane, size);
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), tile);
            tt.setByY(-coordination.getBlockWidth() * (size - t.getvIndex() - 1));
            transitions.add(tt);
        }
        // Animation here


        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();

        new Thread(() -> {
            try {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    board.slip(Direction.UP);
                    curBlockPane = GameUI.draw(board, gamePane, size);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        upDateScore(scoreLabel, board);
        step++;
        upDateStep(stepLabel, board);

    }

    @FXML
    public void downAction() {

        // Animation here
        List<TranslateTransition> transitions = new ArrayList();

        for (Node tile : curBlockPane.getChildren()){
            Tile t = (Tile) tile;
            Coordination coordination = new Coordination(t.gethIndex(), t.getvIndex(), gamePane, size);
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), tile);
            tt.setByY(coordination.getBlockWidth() * (size - t.getvIndex() - 1));
            transitions.add(tt);
        }

        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();

        new Thread(() -> {
            try {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    board.slip(Direction.DOWN);
                    curBlockPane = GameUI.draw(board, gamePane, size);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        upDateScore(scoreLabel, board);
        step++;
        upDateStep(stepLabel, board);
    }

    @FXML
    public void leftAction() {

        // Animation here
        List<TranslateTransition> transitions = new ArrayList();

        for (Node tile : curBlockPane.getChildren()){
            Tile t = (Tile) tile;
            Coordination coordination = new Coordination(t.gethIndex(), t.getvIndex(), gamePane, size);
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), tile);
            tt.setByX(-coordination.getBlockWidth() * (size - t.gethIndex() - 1));
            transitions.add(tt);
        }

        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();

        new Thread(() -> {
            try {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    board.slip(Direction.LEFT);
                    curBlockPane = GameUI.draw(board, gamePane, size);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        upDateScore(scoreLabel, board);
        step++;
        upDateStep(stepLabel, board);
    }

    @FXML
    public void rightAction() {

        // Animation here
        List<TranslateTransition> transitions = new ArrayList();

        for (Node tile : curBlockPane.getChildren()){
            Tile t = (Tile) tile;
            Coordination coordination = new Coordination(t.gethIndex(), t.getvIndex(), gamePane, size);
            TranslateTransition tt = new TranslateTransition(Duration.millis(100), tile);
            tt.setByX(coordination.getBlockWidth() * (size - t.gethIndex() - 1));
            transitions.add(tt);
        }

        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();

        new Thread(() -> {
            try {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    board.slip(Direction.RIGHT);
                    curBlockPane = GameUI.draw(board, gamePane, size);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        upDateScore(scoreLabel, board);
        step++;
        upDateStep(stepLabel, board);
    }

    // 获取gamePane参数
    public double getGamePaneWidth() {
        return gamePane.getWidth();
    }

    public double getLayoutX() {
        return gamePane.getLayoutX();
    }

    public double getLayoutY() {
        return gamePane.getLayoutY();
    }

    // 初始化gamePane网格
    public static void initGamePane(AnchorPane gamePane, int size) {

        gamePane.getChildren().clear();

        drawBackground(gamePane, size);
        drawGrid(gamePane, size);
    }

    public static AnchorPane draw(Board board, AnchorPane gamePane, int size) {

        gamePane.getChildren().clear();

        drawBackground(gamePane, size);
        AnchorPane blockPane = new AnchorPane();

        int[][] grid = board.getBoard();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    blockPane.getChildren().add(new Tile(grid[i][j], j, i, gamePane, size));
                }
            }
        }

        blockPane.setLayoutX(0);
        blockPane.setLayoutY(0);

        gamePane.getChildren().add(blockPane);

        drawGrid(gamePane, size);

        return blockPane;

    }

    private static StackPane createEntity(int val, int x, int y, AnchorPane gamePane, int size) {

        return null;
    }

    private static void drawBackground(AnchorPane gamePane, int size) {

        Pane backgroundPane = new Pane();
        backgroundPane.setLayoutX(0);
        backgroundPane.setLayoutY(0);
        backgroundPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        backgroundPane.setStyle("-fx-background-color: #cbbfb3;\n" +
                "-fx-background-radius: 3px;\n" +
                "-fx-background-size: cover;\n" +
                "-fx-background-position: center;\n");
        gamePane.getChildren().add(backgroundPane);

    }

    private static void drawGrid(AnchorPane gamePane, int size) {

        double space = 11.0;
        double strokeWidth = 11.0;

        double blockWidth = (gamePane.getWidth() - space * (size + 1)) / size;

        for (int i = 1; i < size; i++) {
            Line hLine = new Line();
            hLine.setStartX(space);
            hLine.setEndX(gamePane.getWidth() - space);
            hLine.setStartY(space + i * (blockWidth + space));
            hLine.setEndY(space + i * (blockWidth + space));
            hLine.setStrokeWidth(strokeWidth);
            hLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(hLine);

            Line vLine = new Line();
            vLine.setStartX(space + i * (blockWidth + space));
            vLine.setEndX(space + i * (blockWidth + space));
            vLine.setStartY(space);
            vLine.setEndY(gamePane.getHeight() - space);
            vLine.setStrokeWidth(strokeWidth);
            vLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(vLine);

        }

        Pane borderPane = new Pane();
        borderPane.setLayoutX(0);
        borderPane.setLayoutY(0);
        borderPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        borderPane.setStyle("-fx-border-color: #baac9f;\n" +
                "-fx-border-width: 11px;\n" +
                "-fx-border-radius: 3px;");
        gamePane.getChildren().add(borderPane);

    }

    private static void upDateScore(Label scoreLabel, Board board) {
        score = board.getScore();
        scoreLabel.setText("" + score);
    }

    private static void upDateStep(Label stepLabel, Board board) {
        stepLabel.setText("" + step);
    }



    public static void init(int size, int mode) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
    }

    public static void run() {
        Platform.runLater(() -> {
            GameUI gameUI = new GameUI();
            Stage primaryStage = new Stage();
            try {
                gameUI.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void start() {
        launch();
    }


}
