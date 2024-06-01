package controller;

import com.google.gson.annotations.Expose;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;
import model.*;
import util.Direction;
import util.comparator.CompareByScore;
import util.comparator.CompareByTime;
import util.graphic.Paint;
import util.graphic.SaveUnitPane;
import util.Saver;
import util.logger.Logger;
import util.logger.LogType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.Save.State.*;

public class ArchiveUI extends Application {

    public AnchorPane archivePane;
    public Label userName;
    public Label userScore;
    public Label userTotalGameCount;
    public Label userTotalWinCount;
    public Label userTotalLoseCount;
    public Hyperlink archivePath;
    @FXML
    private AnchorPane upperPane;
    @FXML
    private Label leftArrow;
    @FXML
    private Label rightArrow;
    /* ****** Attributes ****** */
    private int curIndex;
    private int totalSave;
    @Expose
    private String savePath; // 存档路径, 可被序列化

    private static User currentUser;
    private static UserManager userManager;

    private static List<GridPane> saveInterfaceList;
    private List<Save> saveList;

    /* ****** Button Action ****** */

    /* ****** Methods ****** */
    private void loadArchive() {
        // 初始化存档路径提示
        archivePath.setText(getProjectPath() + "\\" + currentUser.getPath().replace("/", "\\") + "\\");
        archivePath.setUnderline(true);
        archivePath.setOnAction(event -> {
            // 点击路径提示时，打开存档文件夹
            try {
//                Runtime.getRuntime().exec("explorer.exe /select," + archivePath.getText());
                Desktop.getDesktop().open(new File(archivePath.getText()));
            } catch (IOException e) {
                new Logger(archivePane, "Failed to open archive path! " + e, LogType.error).show();
            }
        });
        try {
            saveList = Saver.getSaveList(currentUser);
        } catch (Exception e) {
            new Logger(archivePane, "Failed to load archive! " + e, LogType.error).show();
            return;
        } finally {
            if (saveList.contains(Save.ERROR_SAVE)) {
                new Logger(archivePane, "Some saves are corrupted! Please check it.", LogType.error).show();
            }
        }
//        try {
//            saveList = RandomSave.randomSave(150);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return;
//        }
        saveList.sort(new CompareByTime());
        saveInterfaceList = new ArrayList<>();


        // 生成存档界面
        for (int i = 0; i < Math.ceil(saveList.size() / 6.0); i++) {
            GridPane saveInterface = new GridPane();

            saveInterface.setPrefSize(540, 540);
            saveInterface.setLayoutX((i == 0) ? 230 : 1230);
            saveInterface.setLayoutY(30);
            saveInterface.setHgap(27);
            saveInterface.setVgap(38);
            saveInterface.setPadding(new Insets(30, 0, 30, 0));
            saveInterfaceList.add(saveInterface);
        }
        // 存档单元放入
        for (int i = 0; i < saveList.size(); i++) {
            saveInterfaceList.get(i / 6).add(createSaveUnit(saveList.get(i)), i % 3, (i % 6) / 3);
        }
        // 初始化参数
        totalSave = saveList.size();
        curIndex = 0;

        leftArrow.setVisible(false);
        rightArrow.setVisible(false);

        // 若存档界面不止一页，则右移按钮可见
        if (saveInterfaceList.size() > 1) {
            rightArrow.setVisible(true);
        }

        // 初始化用户信息
        loadUserInfo();

        upperPane.getChildren().addAll(saveInterfaceList);
    }

    // 读取用户信息
    private void loadUserInfo() {

        userName.setText(currentUser.getName());
        // 自适应字体大小
        userName.setPrefWidth(userName.getText().length() * 14.0 + 20.0);

        if (saveList.isEmpty()) {
            return;
        }
        try {
            currentUser.setBestScore(saveList.stream().max(new CompareByScore()).get().grid.getScore());
            currentUser.setTotalGames(saveList.size());
            currentUser.setTotalWins((int) saveList.stream().filter(save -> save.state == WIN).count());
            currentUser.setTotalLoses((int) saveList.stream().filter(save -> save.state == LOSE).count());
            Saver.saveToJson(Saver.buildGson(userManager), "general/userInfo.json");
        } catch (Exception e) {
            new Logger(archivePane, "Failed to load user info! " + e, LogType.error).show();
        }

        userScore.setText(currentUser.getBestScore() + "");
        userTotalGameCount.setText(currentUser.getTotalGames() + "");
        userTotalWinCount.setText(currentUser.getTotalWins() + "");
        userTotalLoseCount.setText(currentUser.getTotalLoses() + "");
    }

    private AnchorPane createSaveUnit(Save save) {
        if (save.isEquals(Save.ERROR_SAVE)) {
            return new SaveUnitPane(SaveUnitPane.SAVE_ERROR);
        }
        SaveUnitPane saveUnit = new SaveUnitPane(save);

        Grid grid = save.getGrid();

        grid.setTileGrid(new Tile[grid.getSize()][grid.getSize()]);
        grid.setIsMerged(new boolean[grid.getSize()][grid.getSize()]);
        grid.setIsNew(new boolean[grid.getSize()][grid.getSize()]);

        // 计算线宽
        double lineWidth = 5.0;
        grid.load(saveUnit.getPreviewPane(), 5.0);
        // 绘制预览
        Paint.draw(grid, saveUnit.getPreviewPane(), grid.getSize(), lineWidth, lineWidth);


        // 读取存档信息
        final String customCSS = "-fx-font-size: 14px; " +
                "-fx-text-fill: #727272;" +
                "-fx-font-family: 'Kanit';" ;
        final Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResourceAsStream("/font/Kanit/Kanit-SemiBold.ttf")), 14);
        Text saveName = new Text(save.saveName);
        saveName.setStyle(customCSS);
        saveName.setFont(customFont);

        // 存档时间
        Label time = new Label(save.getDate() + " " + save.getTime());
        time.setStyle(customCSS);
        time.setFont(customFont);
        // 存档游戏模式
        Label name = new Label(save.saveName);
        name.setStyle(customCSS);
        name.setFont(customFont);
        // 存档游戏状态
        Label state = new Label(save.getState());
        state.setStyle(save.state.getStyle());
        state.setPrefWidth(save.state.width);
        state.setPrefHeight(15.0);
        state.setAlignment(javafx.geometry.Pos.CENTER);

        VBox stateBox = new VBox();
        stateBox.getChildren().addAll(state, time, name);
        stateBox.setSpacing(2.0);
        stateBox.setLayoutX(5.0);
        stateBox.setLayoutY(164.0);

        saveUnit.getInfoPane().getChildren().add(stateBox);


        // 设置存档点击事件
        saveUnit.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // 点击存档时，存档界面变暗
                if (saveUnit.isClicked()) {

                    // 再次点击时恢复
                    saveUnit.setStyle("-fx-background-color: #f5f5f5;" +
                            " -fx-background-radius: 5px; " +
                            "-fx-background-size: cover; " +
                            "-fx-background-position: center; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
                    saveUnit.reverseClicked();
                    // 移除previewPane上的选项屏
                    saveUnit.hideOptionPane();

                    System.out.println("Clicked");
                } else { // 点击存档时，存档边界变亮
                    saveUnit.setStyle("-fx-background-color: #f5f5f5;" +
                            " -fx-background-radius: 5px; " +
                            "-fx-background-size: cover; " +
                            "-fx-background-position: center; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(17, 184, 244, 0.8), 10, 0, 0, 0);");
                    // 在previewPane上覆盖选项屏
                    saveUnit.showOptionPane();

                    saveUnit.reverseClicked();
                }
            }
        });

        // 设置删除按钮点击事件
        saveUnit.getDeleteButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // 删除存档
                deleteSave(saveUnit.getSave(), saveUnit);
            }
        });
        // 设置读档按钮点击事件
        saveUnit.getPlayButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // 读取存档
                loadSave(saveUnit.getSave());
            }
        });
        return saveUnit;
    }



    // 读取存档
    private void loadSave(Save save) {

        // archivePane中原有组件全不可见
        archivePane.getChildren().forEach(node -> node.setVisible(false));
        LoadingAnimation load = new LoadingAnimation();
        AnchorPane loadArea = new AnchorPane();
        loadArea.getChildren().add(load.getNode());
        loadArea.setPrefHeight(400);
        loadArea.setPrefWidth(700);
        loadArea.setLayoutX(150);
        loadArea.setLayoutY(300);
        archivePane.getChildren().add(loadArea);
        load.makeTransition();
        load.setOnFinished(event -> {
            // 加载完成后进入游戏
            GameUI.init(save.grid, save.playTime, save);
            try {
                GameUI.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // 关闭存档界面
                Platform.runLater(() -> {
                    // 移除加载动画
                    Stage stage = (Stage) upperPane.getScene().getWindow();
                    stage.close();
                });
            }
            ;
        });

        load.play();
        // 加载各类资源
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 在这里执行你的耗时任务
                Platform.runLater(PublicResource::loadSoundResource);
                Platform.runLater(PublicResource::loadMusicResource);
                return null;
            }
        };

        // 启动新的线程来执行任务
        new Thread(task).start();

    }

    // 删除存档
    private void deleteSave(Save save, SaveUnitPane tarPane) {
        saveList.remove(save);
        // 移除监听
        tarPane.setOnMousePressed(null);
        tarPane.setStyle("-fx-background-color: #f5f5f5;" +
                " -fx-background-radius: 5px; " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        tarPane.setSave(null);
        tarPane.getChildren().clear();
        // 添加垃圾桶图标
        Label trash = new Label();
        trash.setGraphic(new ImageView(new Image("/assets/archiveIcon/trash.png", 100, 100, true, true)));
        tarPane.getChildren().add(trash);
        trash.setLayoutX(31.0);
        trash.setLayoutY(61.0);
        try {
            Saver.deleteSave(currentUser, save);
            new Logger(archivePane, "Delete successfully!", LogType.info).show();
        } catch (Exception e) {
            new Logger(archivePane, "Delete failed! " + e, LogType.error).show();
        }
        loadUserInfo();
    }


    /* ****** Button Action ****** */
    public void leftSwitchAction() {
        if (curIndex > 0) {
            SwitchInterfaceAnimation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>() {
                {
                    add(saveInterfaceList.get(curIndex));
                    add(saveInterfaceList.get(curIndex - 1));
                }
            }, Direction.LEFT, 100, 10, -1000);
            switchAnimation.makeTransition();
            switchAnimation.play(Animation.CombineType.GROUP);
        }
        curIndex--;
        // 若当前存档界面为第一页，则左移按钮不可见
        if (curIndex == 0) {
            leftArrow.setVisible(false);
        }
        // 若当前存档界面不为最后一页，则右移按钮可见
        if (curIndex < saveInterfaceList.size() - 1) {
            rightArrow.setVisible(true);
        }

    }

    public void rightSwitchAction() {
        if (curIndex < saveInterfaceList.size() - 1) {
            SwitchInterfaceAnimation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>() {
                {
                    add(saveInterfaceList.get(curIndex));
                    add(saveInterfaceList.get(curIndex + 1));
                }
            }, Direction.LEFT, 100, 10, 1000);
            switchAnimation.makeTransition();
            switchAnimation.play(Animation.CombineType.GROUP);
        }
        curIndex++;
        // 若当前存档界面不为第一页，则左移按钮可见
        if (curIndex > 0) {
            leftArrow.setVisible(true);
        }
        // 若当前存档界面为最后一页，则右移按钮不可见
        if (curIndex == saveInterfaceList.size() - 1) {
            rightArrow.setVisible(false);
        }
    }

    public void hover(MouseEvent mouseEvent) {
        // 悬浮时显示背景
        // 如果触发时鼠标位于左箭头上
        if (mouseEvent.getSource().equals(leftArrow)) {
            leftArrow.setStyle("-fx-background-color: #c5c5c5; -fx-background-radius: 3px; -fx-background-size: cover; -fx-background-position: center;");
        }
        // 如果触发时鼠标位于右箭头上
        if (mouseEvent.getSource().equals(rightArrow)) {
            rightArrow.setStyle("-fx-background-color: #c5c5c5; -fx-background-radius: 3px; -fx-background-size: cover; -fx-background-position: center;");
        }
    }

    public void clearHover(MouseEvent mouseEvent) {
        // 移出时清除背景
        // 如果触发时鼠标位于左箭头上
        if (mouseEvent.getSource().equals(leftArrow)) {
            leftArrow.setStyle("-fx-background-color: transparent;");
        }
        // 如果触发时鼠标位于右箭头上
        if (mouseEvent.getSource().equals(rightArrow)) {
            rightArrow.setStyle("-fx-background-color: transparent;");
        }
    }

    @FXML
    public void returnToMain() {
        MainUI.init(null);
        MainUI.run();
        ((Stage) upperPane.getScene().getWindow()).close();
    }

    /* ****** Starting ****** */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXView/ArchiveUI.fxml")));
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.TRANSPARENT);
        leftArrow = (Label) scene.lookup("#leftArrow");
        rightArrow = (Label) scene.lookup("#rightArrow");
        upperPane = (AnchorPane) scene.lookup("#upperPane");
        archivePane = (AnchorPane) scene.lookup("#archivePane");
        userName = (Label) scene.lookup("#userName");
        userScore = (Label) scene.lookup("#userScore");
        userTotalGameCount = (Label) scene.lookup("#userTotalGameCount");
        userTotalWinCount = (Label) scene.lookup("#userTotalWinCount");
        userTotalLoseCount = (Label) scene.lookup("#userTotalLoseCount");
        archivePath = (Hyperlink) scene.lookup("#archivePath");

//        rightArrow.setVisible(true);

        loadArchive();
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new javafx.scene.image.Image("assets/titleIcon/favicon-32x32.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Archive");

        primaryStage.show();
    }

    // 运行ArchiveUI
    public static void run() {
        Platform.runLater(() -> {
            ArchiveUI archiveUI = new ArchiveUI();
            Stage primaryStage = new Stage();
            try {
                archiveUI.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void init(User u) {
        if (PublicResource.isEmpty()) {
            throw new RuntimeException("PublicResource is empty");
        } else {
            currentUser = PublicResource.getLoginUser();
            userManager = PublicResource.getUserManager();
        }
    }

    // 获取项目所在路径
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

}
