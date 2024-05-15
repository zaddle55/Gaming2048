package controller;


import ai.AIThread;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Tile;
import util.Coordination;
import model.Grid;
import util.Direction;
import util.Time;
import util.Timer;

import java.util.Map;
import java.util.Objects;


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
    @FXML
    private Label timeLabel;
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
    // 是否加载
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
    private static Timer timer;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXView/GameUI.fxml")));
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
        timeLabel = (Label) scene.lookup("#timeLabel");
        autoButton = (Button) scene.lookup("#autoButton");

        GameUI.initGamePane(gamePane, size);

        // 游戏板初始化
        if (!isLoad) {
            grid = new Grid(size, mode);
            grid.init(gamePane);
            grid.fillTileGrid();
        } else {
            grid.load(gamePane);
        }

        GameUI.draw(grid, gamePane, size);
        // 计时器
        timer = new Timer(Time.ZERO, Time.INFINITE);
        timer.begin();
//        timer.setEndEvent(() -> {
//            isAuto = false;
//            autoButton.setText("Auto");
//            winAction();
//        });
        timeLabel.textProperty().bind(timer.messageProperty());

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

        grid = new Grid(size, mode);
        grid.init(gamePane);
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);
        step = 0;
        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();

        isEnd = false;
        isWin = false;
        isLose = false;
        timeLabel.textProperty().bind(timer.messageProperty());
        timer.reset();

    }

    // undo按钮事件
    @FXML
    public void undoAction() {

        if (isAuto) {
            return;
        }

        grid.undo();
        GameUI.draw(grid, gamePane, size);
        upDateScore(scoreLabel, grid);

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

        Map<Tile, Double> distanceMap = grid.move(Direction.UP);

        if (distanceMap == null) {
            return;
        }
        Animation slide = new MoveAnimation(Direction.UP, distanceMap);
        slide.makeTransition();
        slide.setOnFinished(event -> {

            GameUI.draw(grid, gamePane, size);
            updateState();
        });

        slide.play(CombineType.GROUP);

    }

    @FXML
    public void downAction() {

        if (isEnd) {
            return;
        }

        Map<Tile, Double> distanceMap = grid.move(Direction.DOWN);

        if (distanceMap == null) {
            return;
        }

        Animation slide = new MoveAnimation(Direction.DOWN, distanceMap);
        slide.makeTransition();
        slide.setOnFinished(event -> {

            GameUI.draw(grid, gamePane, size);
            updateState();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void leftAction() {

        if (isEnd) {
            return;
        }

        Map<Tile, Double> distanceMap = grid.move(Direction.LEFT);

        if (distanceMap == null) {
            return;
        }

        Animation slide = new MoveAnimation(Direction.LEFT, distanceMap);

        slide.makeTransition();
        slide.setOnFinished(event -> {

            GameUI.draw(grid, gamePane, size);
            updateState();
        });
        slide.play(CombineType.GROUP);

    }

    @FXML
    public void rightAction() {

        if (isEnd) {
            return;
        }

        Map<Tile, Double> distanceMap = grid.move(Direction.RIGHT);

        if (distanceMap == null) {
            return;
        }

        Animation slide = new MoveAnimation(Direction.RIGHT, distanceMap);
        slide.makeTransition();
        slide.setOnFinished(event -> {

            GameUI.draw(grid, gamePane, size);
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

        drawBackground(gamePane);
        drawGrid(gamePane, size, 11.0, 11.0);
    }

    public void updateState() {
        upDateScore(scoreLabel, grid);

        upDateStep(stepLabel, grid);

        if (grid.isWin()) {
            isWin = true;

            isAuto = false;
            autoButton.setText("Auto");
            timer.stop();
            winAction();
        } else if (grid.isOver()) {
            isLose = true;

            isAuto = false;
            autoButton.setText("Auto");
            timer.stop();
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
    public static void draw(Grid board, AnchorPane gamePane, int size) {

        gamePane.getChildren().clear();

        drawBackground(gamePane);
        AnchorPane blockPane = new AnchorPane();

        for (Tile[] line : grid.getTileGrid()) {
            for (Tile tile : line) {
                if (tile != null) {
                    blockPane.getChildren().add(tile);
                }
            }
        }

        blockPane.setLayoutX(0);
        blockPane.setLayoutY(0);

        gamePane.getChildren().add(blockPane);

        drawGrid(gamePane, size, 11.0, 11.0);


    }


    /**
     * @description: 绘制游戏板背景填充方法
     * @param gamePane 游戏板
     * @return void
     */
    static void drawBackground(AnchorPane gamePane) {

        Pane backgroundPane = new Pane();
        backgroundPane.setLayoutX(0);
        backgroundPane.setLayoutY(0);
        backgroundPane.setPrefSize(gamePane.getPrefWidth(), gamePane.getPrefHeight());
        backgroundPane.setStyle("""
                -fx-background-color: #cbbfb3;
                -fx-background-radius: 3px;
                -fx-background-size: cover;
                -fx-background-position: center;
                """);
        gamePane.getChildren().add(backgroundPane);

    }

    /**
     * @description: 绘制游戏板网格方法
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return void
     */
    static void drawGrid(AnchorPane gamePane, int size, double space, double strokeWidth) {

        double blockWidth = (gamePane.getPrefWidth() - space * (size + 1)) / size;

        for (int i = 1; i < size; i++) {
            Line hLine = new Line();
            hLine.setStartX(space);
            hLine.setEndX(gamePane.getPrefWidth() - space);
            hLine.setStartY(space + i * (blockWidth + space));
            hLine.setEndY(space + i * (blockWidth + space));
            hLine.setStrokeWidth(strokeWidth);
            hLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(hLine);

            Line vLine = new Line();
            vLine.setStartX(space + i * (blockWidth + space));
            vLine.setEndX(space + i * (blockWidth + space));
            vLine.setStartY(space);
            vLine.setEndY(gamePane.getPrefHeight() - space);
            vLine.setStrokeWidth(strokeWidth);
            vLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(vLine);

        }

        Pane borderPane = new Pane();
        borderPane.setLayoutX(0);
        borderPane.setLayoutY(0);
        borderPane.setPrefSize(gamePane.getPrefWidth(), gamePane.getPrefHeight());
        borderPane.setStyle("-fx-border-color: #baac9f;\n" +
                String.format("-fx-border-width: %fpx;\n", strokeWidth) +
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
        // int incScore = grid.getScore() - score;
        score = grid.getScore();
        scoreLabel.setText("" + score);
    }

    // 更新步数
    private static void upDateStep(Label stepLabel, Grid grid) {
        stepLabel.setText("" + grid.getStep());
    }

    private void winAction() {

        isEnd = true;
        // 绘制胜利界面
        StackPane winPane = new StackPane();
        winPane.setLayoutX(0);
        winPane.setLayoutY(0);
        winPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        winPane.setStyle("""
                -fx-background-color: rgba(255,220,80,0.73);
                -fx-background-radius: 3px;
                -fx-background-size: cover;
                -fx-background-position: center;
                """);
        winPane.toFront();
        winPane.setOpacity(0.6);

        VBox winBox = new VBox();
        winBox.setAlignment(javafx.geometry.Pos.CENTER);
        // 显示“YOU WIN”标签
        Label winLabel = new Label("YOU WIN");
        winLabel.setStyle("""
                -fx-font-size: 50px;
                -fx-font-weight: bold;
                -fx-text-fill: #ffffff;
                -fx-effect: dropshadow(three-pass-box, #776e65, 10, 0, 0, 0);""");
        // 显示分数
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("""
                -fx-font-size: 30px;
                -fx-font-weight: bold;
                -fx-text-fill: #776e65;""");
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
        losePane.setStyle("""
                -fx-background-color: rgba(113,113,113,0.94);
                -fx-background-radius: 3px;
                -fx-background-size: cover;
                -fx-background-position: center;
                """);
        losePane.toFront();
        losePane.setOpacity(0.6);

        VBox loseBox = new VBox();
        loseBox.setAlignment(javafx.geometry.Pos.CENTER);
        // 显示“YOU LOSE”标签
        Label loseLabel = new Label("YOU LOSE");
        loseLabel.setStyle("""
                -fx-font-size: 50px;
                -fx-font-weight: bold;
                -fx-text-fill: #1e1d1d;
                -fx-effect: dropshadow(three-pass-box, #776e65, 10, 0, 0, 0);""");
        // 显示分数
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("""
                -fx-font-size: 30px;
                -fx-font-weight: bold;
                -fx-text-fill: #3a3a3a;
                """);
        // 添加到父节点
        loseBox.getChildren().addAll(loseLabel, scoreLabel);
        losePane.getChildren().add(loseBox);

        gamePane.getChildren().add(losePane);
    }

    // 初始化GameUI
    public static void init(int size, int mode) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(size, mode));
    }

    public static void init(int mode, int[][] board) {
        GameUI.setSize(board.length);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(board));
        isLoad = true;

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

    public void autoAction() {
        if (isEnd) return;
        if (isAuto) {
            isAuto = false;
            aiThread.endFlag = true;
            autoButton.setText("Auto");
            scene.getRoot().requestFocus();
            timer.stop();
        } else {
            isAuto = true;
            if (aiThread == null) {
                aiThread = new AIThread(grid, this);
            } else {
                aiThread.endFlag = false;
            }
            autoButton.setText("Stop");
            new Thread(aiThread).start();
            timer.reset();
        }
    }

    public void exitAction() {
        if (isAuto) return;
        timer.stop();
    }
}
