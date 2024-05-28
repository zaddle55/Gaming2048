package controller;


import ai.AIThread;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Save;
import model.Tile;
import model.Grid;
import util.*;
import util.graphic.Paint;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.io.File;
import java.time.LocalDate;


public class GameUI extends Application {


    public TextField saveName;
    public AnchorPane sidebarPane;
    public GridPane savePane;
    public GridPane exitPane;
    public Text saveText;
    public Button saveConfirm;
    public Text exitText;
    public Button rtmConfirm;
    public Button exitConfirm;
    // 节点域
    @FXML
    private AnchorPane gamePane;
    public AnchorPane mainPane;
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
    // 当前存档
    private static Save currentSave;
    // AI线程
    private static AIThread aiThread;
    private static Timer timer;
    private static Time startTime;

    // 游戏资源
    private static User currentUser;
    private static MediaPlayer moveSound;

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
        saveName = (TextField) scene.lookup("#saveName");
        saveText = (Text) scene.lookup("#saveText");
        saveConfirm = (Button) scene.lookup("#saveConfirm");
        exitText = (Text) scene.lookup("#exitText");
        rtmConfirm = (Button) scene.lookup("#rtmConfirm");
        exitConfirm = (Button) scene.lookup("#exitConfirm");
        sidebarPane = (AnchorPane) scene.lookup("#sidebarPane");

        // 资源初始化
        // 音效初始化
        URL audioResource = getClass().getResource("/assets/sound/moveSound.mp3");
        if (audioResource != null) {
            moveSound = new MediaPlayer(new Media(audioResource.toString()));
        }
        // 字体初始化
        final Font LILITA_18 = Font.loadFont(getClass().getResourceAsStream("/font/Lilita_One/LilitaOne-Regular.ttf"), 18);
        final Font LILITA_16 = Font.loadFont(getClass().getResourceAsStream("/font/Lilita_One/LilitaOne-Regular.ttf"), 16);
        saveText.setFont(LILITA_18);
        saveName.setFont(LILITA_16);
        saveConfirm.setFont(LILITA_18);
        exitText.setFont(LILITA_18);
        rtmConfirm.setFont(LILITA_18);
        exitConfirm.setFont(LILITA_18);

        // 游戏板初始化
        GameUI.initGamePane(gamePane, size);
        if (!isLoad) {
            grid = new Grid(size, mode);
            grid.init(gamePane);
            Paint.draw(grid, gamePane, size, 11, 11);
            PopUpAnimation appear = new PopUpAnimation(grid);
            appear.makeTransition();
            appear.play(Animation.CombineType.GROUP);
        } else {
            grid.load(gamePane);
            Paint.draw(grid, gamePane, size, 11, 11);

        }

        // 计时器
        timer = new Timer(startTime, Time.INFINITE);
        timer.begin();
        // 设置定时器结束事件
//        timer.setEndEvent(() -> {
//            isAuto = false;
//            autoButton.setText("Auto");
//            winAction();
//        });
        // 若有用户登录，开启定时自动保存任务
        if (currentUser != null) {
            timer.setTimingSession(() -> {
                autoSave();
                System.out.println("Auto save");
            }, Duration.seconds(10));
        }
        timeLabel.textProperty().bind(timer.messageProperty());
        updateState();

        // 初始化输入框
        if (currentSave != null) {
            saveName.setText(currentSave.saveName);
        }

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
        Paint.draw(grid, gamePane, size, 11, 11);
        PopUpAnimation appear = new PopUpAnimation(grid);
        appear.makeTransition();

        appear.play(Animation.CombineType.GROUP);
        upDateScore(scoreLabel, grid);

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
        Paint.draw(grid, gamePane, size, 11, 11);
        upDateScore(scoreLabel, grid);

        upDateStep(stepLabel, grid);
        scene.getRoot().requestFocus();

        isEnd = false;
        isWin = false;
        isLose = false;
        // 计时器继续
        if (timer != null) {
            timer.continueTimer();
        }
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
        makeAnimation(Direction.UP, distanceMap);
//        slide.setOnFinished(event -> {
//
//            GameUI.draw(grid, gamePane, size);
//            updateState();
//        });
//
//        slide.play(CombineType.GROUP);

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

        makeAnimation(Direction.DOWN, distanceMap);

    }

    private void makeAnimation(Direction down, Map<Tile, Double> distanceMap) {
        // 移除键盘焦点
        isEnd = true;

        // 播放音效
        moveSound.stop();
        moveSound.play();

        MoveAnimation slide = new MoveAnimation(down, distanceMap);
        slide.makeTransition();
        slide.setOnFinished(event1 -> {

            Paint.draw(grid, gamePane, size, 11, 11);
            PopUpAnimation appear = new PopUpAnimation(grid);
            appear.makeTransition();
            BounceAnimation bounce = new BounceAnimation(grid);
            bounce.makeTransition();
            ParallelTransition group1 = new ParallelTransition(bounce.getGroupTransition(), appear.getGroupTransition());
            group1.play();

            // 恢复键盘焦点
            isEnd = false;

            updateState();
            scene.getRoot().requestFocus();


        });
        slide.play(Animation.CombineType.GROUP);

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

        makeAnimation(Direction.LEFT, distanceMap);

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

        makeAnimation(Direction.RIGHT, distanceMap);

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

        Paint.drawBackground(gamePane);
        Paint.drawGrid(gamePane, size, 11.0, 11.0);
    }

    public void updateState() {
        upDateScore(scoreLabel, grid);

        upDateStep(stepLabel, grid);

        if (grid.isWin()) {
            isWin = true;

            isAuto = false;
            autoButton.setGraphic(new ImageView(new Image("/assets/buttonIcon/auto.png", 22.0, 22.0, false, false)));
            timer.stop();
            winAction();
        } else if (grid.isOver()) {
            isLose = true;

            isAuto = false;
            autoButton.setGraphic(new ImageView(new Image("/assets/buttonIcon/auto.png", 22.0, 22.0, false, false)));
            timer.stop();
            loseAction();
        }
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

    @FXML
    private void manualSave() {

        if (saveName.getText().isEmpty()) {
            Tooltip tooltip = new Tooltip("Please enter a save name!");
            tooltip.setFont(Font.font("Arial", 12));
            tooltip.setShowDuration(javafx.util.Duration.millis(2000));
            tooltip.setAutoHide(true);
            tooltip.show(saveName, 1620, 500);
            return;
        }

        // 保存存档
        // 保存用户信息
        Save.State state;
        if (isWin) {
            state = Save.State.WIN;
        } else if (isLose) {
            state = Save.State.LOSE;
        } else {
            state = Save.State.IN_PROGRESS;
        }
        
        // 保存到User对应存档路径
        currentSave = new Save(saveName.getText(), grid, state, new Time(timeLabel.getText()), new Date(), new Time());

        try {
            Saver.saveToJson(Saver.buildGson(currentSave), currentUser.getPath() + "/" + currentSave.saveName + ".json");
        } catch (IOException e) {
            throw new RuntimeException("Save failed!");
        }
    }

    private void autoSave() {
        // 保存存档
        // 保存用户信息
        Save.State state;
        if (isWin) {
            state = Save.State.WIN;
        } else if (isLose) {
            state = Save.State.LOSE;
        } else {
            state = Save.State.IN_PROGRESS;
        }
        if (currentSave == null) {
            String saveName = "Auto " + LocalDate.now();
            currentSave = new Save(saveName, grid, state, startTime);
            // 保存到User对应存档路径
            try {
                Saver.saveToJson(Saver.buildGson(currentSave), currentUser.getPath() + "/" + currentSave.saveName + ".json");
            } catch (IOException e) {
                throw new RuntimeException("Save failed!"); // 后改
            }
            
        } else {
            currentSave = new Save(currentSave.saveName, grid, state, new Time(timeLabel.getText()));
            // 保存到User对应存档路径
            try {
                Saver.saveToJson(Saver.buildGson(currentSave), currentUser.getPath() + "/" + currentSave.saveName + ".json");
            } catch (IOException e) {
                throw new RuntimeException("Save failed!"); // 后改
            }
        }
    }

    // 初始化GameUI
    public static void init(int size, int mode) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(size, mode));
        GameUI.setStartTime(Time.ZERO);
        isEnd = false;
        isWin = false;
        isLose = false;
    }

    public static void init(int size, int mode, User user) {
        GameUI.setSize(size);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(size, mode));
        GameUI.setStartTime(Time.ZERO);
        currentUser = user;
        currentSave = null;
        isLoad = false;
        isEnd = false;
        isWin = false;
        isLose = false;
    }

    private static void setStartTime(Time startTime) {
        GameUI.startTime = startTime;
    }


    public static void init(int mode, int[][] board, Time startTime) {
        GameUI.setSize(board.length);
        GameUI.setMode(mode);
        GameUI.setBoard(new Grid(board, mode));
        GameUI.setStartTime(startTime);
        isLoad = true;
        isEnd = false;


    }

    public static void init(Grid grid, Time startTime) {
        GameUI.setBoard(grid);
        GameUI.setSize(grid.getSize());
        GameUI.setMode(grid.getMode());
        GameUI.setStartTime(startTime);
        isLoad = true;
    }

    // 读取存档时
    public static void init(Grid grid, Time startTime, Save save) {
        GameUI.setBoard(grid);
        GameUI.setSize(grid.getSize());
        GameUI.setMode(grid.getMode());
        GameUI.setStartTime(startTime);
        if (!PublicResource.isEmpty()) {
            currentUser = PublicResource.getLoginUser();
        }
        GameUI.currentSave = save;
        isLoad = true;
        isEnd = false;

        isWin = false;
        isLose = false;
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


    public static Grid getGrid() {
        return grid;
    }

    public void autoAction() {

        if (isWin || isLose) {
            return;
        }

        if (isAuto) {
            isAuto = false;
            aiThread.endFlag = true;
            autoButton.setGraphic(new ImageView(new Image("/assets/buttonIcon/auto.png", 22.0, 22.0, false, false)));
            scene.getRoot().requestFocus();

        } else {
            isAuto = true;
            aiThread = new AIThread(grid, this);
            autoButton.setGraphic(new ImageView(new Image("/assets/buttonIcon/pause.png", 22.0, 22.0, false, false)));
            new Thread(aiThread).start();

        }
    }

    public void exitAction() {
        if (isAuto) return;
        timer.stop();
        isEnd = true;
        SlipToSidebarAnimation slip = new SlipToSidebarAnimation(mainPane,sidebarPane);
        slip.makeTransition();
        slip.play(Animation.CombineType.GROUP);
        // 为mainPane添加毛玻璃效果
        BoxBlur blur = new BoxBlur(10, 10, 3);
        mainPane.setEffect(blur);

        exitPane.setVisible(true);
        try {
            AnchorPane mask = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXView/MaskPane.fxml")));
            gameInterface.getChildren().add(mask);
            scene.lookup("#arrow").setOnMousePressed(event -> {
                slipReform();
                gameInterface.getChildren().remove(mask);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToMain() {
        MainUI.init(null);
        MainUI.run();
        exitGame();
    }

    public void exitGame() {
        // 弹出确认窗口 TODO
        Stage stage = (Stage) gamePane.getScene().getWindow();
        stage.close();
    }

    public void slipReform() {
        SlipToSidebarAnimation slip = new SlipToSidebarAnimation(mainPane,sidebarPane,true);
        slip.makeTransition();
        slip.play(Animation.CombineType.GROUP);
        mainPane.setEffect(null);
        exitPane.setVisible(false);
        savePane.setVisible(false);
        isEnd = false;
        if (!isWin && !isLose) timer.continueTimer();
    }

    public void saveAction() {
        if (isAuto || currentUser == null) return;
        timer.stop();
        isEnd = true;
        SlipToSidebarAnimation slip = new SlipToSidebarAnimation(mainPane,sidebarPane);
        slip.makeTransition();
        slip.play(Animation.CombineType.GROUP);
        // 为mainPane添加毛玻璃效果
        BoxBlur blur = new BoxBlur(10, 10, 3);
        mainPane.setEffect(blur);

        savePane.setVisible(true);
        try {
            AnchorPane mask = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXView/MaskPane.fxml")));
            gameInterface.getChildren().add(mask);
            if (currentSave != null) {
                saveName.setText(currentSave.saveName);
            }
            scene.lookup("#arrow").setOnMousePressed(event -> {
                slipReform();
                gameInterface.getChildren().remove(mask);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
