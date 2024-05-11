package controller;


import ai.AIThread;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Tile;
import util.Coordination;
import model.Grid;
import util.Direction;
import controller.Animation.CombineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class GameUI extends Application {

    // 节点域
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button restartButton;
    @FXML
    private Button autoButton;
    @FXML
    private AnchorPane gameInterface;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label stepLabel;
    private static AnchorPane curBlockPane;
    private static Scene scene;

    // 游戏参数
    private static int size;
    private static int mode;
    private static Grid grid;

    /** 运行时参数 **/
    // 分数
    private static int score = 0;
    // 步数
    private static int step = 0;
    //
    private static boolean isLoad = false;
    // 胜利标志
    public static boolean isWin = false;
    // 失败标志
    public static boolean isLose = false;
    // 游戏结束标志
    private static boolean isEnd = false;
    // AI运行标志
    public static boolean isAuto = false;
    // AI线程
    private static AIThread aiThread;

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

    public static Grid getBoard() {
        return grid;
    }

    public static void setBoard(Grid grid) {
        GameUI.grid = grid;
    }


    // 游戏界面初始化
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXView/GameUI.fxml"));
        scene = new Scene(root, 1000, 1000);

        // stage设置
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image("/assets/titleIcon/favicon-32x32.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 设置焦点
        scene.getRoot().requestFocus();

        // 获取节点
        gamePane = (AnchorPane) scene.lookup("#gamePane");
        scoreLabel = (Label) scene.lookup("#scoreLabel");
        stepLabel = (Label) scene.lookup("#stepLabel");

        GameUI.initGamePane(gamePane, size);

        // 游戏板初始化
        if (!isLoad) {
            grid = new Grid(0, size, mode);
            grid.init();
        }
        curBlockPane = GameUI.draw(grid, gamePane, size);

        // 设置键盘监听
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

    // restart按钮事件
    @FXML
    public void restartAction() {

        if (isAuto) {
            return;
        }

        grid = new Grid(0, size, mode);
        grid.init();
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);
        step = 0;
        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();

        isEnd = false;
        isWin = false;
        isLose = false;

    }

    // undo按钮事件
    @FXML
    public void undoAction() {

        if (isAuto) {
            return;
        }

        grid.undo();
        grid.undo();
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);
        step -= (step > 0) ? 1 : 0;
        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();

        isEnd = false;
        isWin = false;
        isLose = false;
    }

    // 按键事件
    @FXML
    public void upAction() {

        if (isEnd) {
            return;
        }

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.UP, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.UP);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            updateState();
        });

        slide.play(CombineType.GROUP);

    }

    @FXML
    public void downAction() {

        if (isEnd) {
            return;
        }

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.DOWN, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.DOWN);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            updateState();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void leftAction() {

        if (isEnd) {
            return;
        }

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.LEFT, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.LEFT);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            updateState();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void rightAction() {

        if (isEnd) {
            return;
        }

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.RIGHT, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.RIGHT);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            updateState();
        });
        slide.play(CombineType.GROUP);

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

    /**
     * @description: 初始化游戏板方法
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return void
     */
    public static void initGamePane(AnchorPane gamePane, int size) {

        gamePane.getChildren().clear();

        drawBackground(gamePane, size);
        drawGrid(gamePane, size);
    }

    public void updateState() {
        upDateScore(scoreLabel, grid);
        step++;
        upDateStep(stepLabel, grid);
        grid.addToHistory();
        if (grid.isWin()) {
            isWin = true;

            isAuto = false;
            autoButton.setText("Auto");
            winAction();
        } else if (grid.isOver()) {
            isLose = true;

            isAuto = false;
            autoButton.setText("Auto");
            loseAction();
        }
    }

    /**
     * @description: 绘制游戏板方法
     * @param board 游戏板
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return javafx.scene.layout.AnchorPane
     */
    public static AnchorPane draw(Grid board, AnchorPane gamePane, int size) {

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


    /**
     * @description: 绘制游戏板背景填充方法
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return void
     */
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

    /**
     * @description: 绘制游戏板网格方法
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return void
     */
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

    //
    public void simulateMove(Direction direction) {
        switch (direction) {
            case UP:
                upAction();
                break;
            case DOWN:
                downAction();
                break;
            case LEFT:
                leftAction();
                break;
            case RIGHT:
                rightAction();
                break;
        }
    }

    // 更新分数
    private static void upDateScore(Label scoreLabel, Grid grid) {
        score = grid.getScore();
        scoreLabel.setText("" + score);
    }

    // 更新步数
    private static void upDateStep(Label stepLabel, Grid grid) {
        stepLabel.setText("" + step);
    }

    private void winAction() {

        isEnd = true;
        // 绘制胜利界面
        StackPane winPane = new StackPane();
        winPane.setLayoutX(0);
        winPane.setLayoutY(0);
        winPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        winPane.setStyle("-fx-background-color: rgba(255,220,80,0.73);\n" +
                "-fx-background-radius: 3px;\n" +
                "-fx-background-size: cover;\n" +
                "-fx-background-position: center;\n");
        winPane.toFront();
        winPane.setOpacity(0.6);

        VBox winBox = new VBox();
        winBox.setAlignment(javafx.geometry.Pos.CENTER);
        // 显示“YOU WIN”标签
        Label winLabel = new Label("YOU WIN");
        winLabel.setStyle("-fx-font-size: 50px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: #ffffff;\n" +
                "-fx-effect: dropshadow(three-pass-box, #776e65, 10, 0, 0, 0);");
        // 显示分数
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-font-size: 30px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: #776e65;");
        // 添加到父节点
        winBox.getChildren().addAll(winLabel, scoreLabel);
        winPane.getChildren().add(winBox);

        gamePane.getChildren().add(winPane);
    }

    private void loseAction() {

        isEnd = true;
        // 绘制失败界面
        StackPane losePane = new StackPane();
        losePane.setLayoutX(0);
        losePane.setLayoutY(0);
        losePane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        losePane.setStyle("-fx-background-color: rgba(113,113,113,0.94);\n" +
                "-fx-background-radius: 3px;\n" +
                "-fx-background-size: cover;\n" +
                "-fx-background-position: center;\n");
        losePane.toFront();
        losePane.setOpacity(0.6);

        VBox loseBox = new VBox();
        loseBox.setAlignment(javafx.geometry.Pos.CENTER);
        // 显示“YOU LOSE”标签
        Label loseLabel = new Label("YOU LOSE");
        loseLabel.setStyle("-fx-font-size: 50px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: #1e1d1d;\n" +
                "-fx-effect: dropshadow(three-pass-box, #776e65, 10, 0, 0, 0);");
        // 显示分数
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-font-size: 30px;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: #3a3a3a;");
        // 添加到父节点
        loseBox.getChildren().addAll(loseLabel, scoreLabel);
        losePane.getChildren().add(loseBox);

        gamePane.getChildren().add(losePane);
    }

    // 初始化GameUI
    public static void init(int size, int mode) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(0, size, mode));
    }

    public static void init(int mode, int[][] board) {
        GameUI.setSize(board.length);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(0, board));
        GameUI.isLoad = true;
    }

    // 运行GameUI
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


    public Grid getGrid() {
        return grid;
    }

    public void autoAction(MouseEvent mouseEvent) {
        if (isAuto) {
            isAuto = false;
            aiThread.endFlag = true;
            autoButton.setText("Auto");
        } else {
            isAuto = true;
            if (aiThread == null) {
                aiThread = new AIThread(grid, this);
            } else {
                aiThread.endFlag = false;
            }
            autoButton.setText("Stop");
            new Thread(aiThread).start();
        }
    }
}
