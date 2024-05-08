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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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


public class GameUI extends Application {

    // 节点域
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
        grid = new Grid(0, size, mode);
        grid.init();
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

        grid = new Grid(0, size, mode);
        grid.init();
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);
        step = 0;
        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();

    }

    // undo按钮事件
    @FXML
    public void undoAction() {
        grid.undo();
        grid.undo();
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);
        step -= (step > 0) ? 1 : 0;
        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();
    }

    // 按键事件
    @FXML
    public void upAction() {

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.UP, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.UP);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            upDateScore(scoreLabel, grid);
            step++;
            upDateStep(stepLabel, grid);
            grid.addToHistory();
        });

        slide.play(CombineType.GROUP);

    }

    @FXML
    public void downAction() {

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.DOWN, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.DOWN);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            upDateScore(scoreLabel, grid);
            step++;
            upDateStep(stepLabel, grid);
            grid.addToHistory();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void leftAction() {

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.LEFT, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.LEFT);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            upDateScore(scoreLabel, grid);
            step++;
            upDateStep(stepLabel, grid);
            grid.addToHistory();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void rightAction() {

        Animation slide = new MoveAnimation(curBlockPane.getChildren(), Direction.RIGHT, grid, new Coordination(size, gamePane));
        slide.makeTransition();
        slide.setOnFinished(event -> {
            grid.slip(Direction.RIGHT);
            curBlockPane = GameUI.draw(grid, gamePane, size);
            upDateScore(scoreLabel, grid);
            step++;
            upDateStep(stepLabel, grid);
            grid.addToHistory();
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
        // TODO
    }

    private void loseAction() {
        // TODO
    }

    // 初始化GameUI
    public static void init(int size, int mode) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(0, size, mode));
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
}
