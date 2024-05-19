package controller;

import com.google.gson.annotations.Expose;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Save;
import util.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchiveUI extends Application {

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

//    private static User currentUser;

    private List<GridPane> saveInterfaceList;
    private List<Save> saveList;

    /* ****** Button Action ****** */

    /* ****** Methods ****** */
    private void loadArchive() {
        // saveInterfaceList = getSaveList(currentUser);
        GridPane saveInterface = new GridPane();
        saveInterfaceList.add(saveInterface);
        saveInterface.setPrefSize(480, 480);
        saveInterface.setLayoutX(60);
        saveInterface.setLayoutY(120);
        for (Save save : saveList) {
            saveInterface.add(createSaveUnit(save), (saveList.indexOf(save) % 6) % 3, (saveList.indexOf(save) % 6) % 2);

            // 每六个存档为一页
            if (saveList.indexOf(save) % 6 == 5) {
                saveInterface = new GridPane();
                saveInterfaceList.add(saveInterface);
                saveInterface.setPrefSize(480, 480);
                saveInterface.setLayoutX(1060);
                saveInterface.setLayoutY(120);
            }
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
    }

    // 读取用户信息
    private void loadUserInfo() {
    }

    private AnchorPane createSaveUnit(Save save) {
        AnchorPane saveUnit = new AnchorPane();
        saveUnit.setPrefSize(160, 220);
        // 设置存档背景为dropshadow
        saveUnit.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        // 读取存档预览

//        // 缩放预览 从700*700缩放至200*200
//        preview.setScaleX(0.2857);
//        preview.setScaleY(0.2857);
//        preview.setLayoutX(0);
//        preview.setLayoutY(0);
//        saveUnit.getChildren().add(preview);
        // 读取存档信息
        // 存档日期
        Label date = new Label(save.getDate());
        // 存档时间
        Label time = new Label(save.getTime());
        // 存档分数
        Label score = new Label("Score: " + save.grid.getScore());
        // 存档步数
        Label step = new Label("Step: " + save.grid.getStep());
        // 存档游戏模式
        Label mode = new Label("Mode: " + save.grid.getMode());
        // 存档游戏状态
        Label state = new Label("State: " + save.getState());
        return saveUnit;
    }

    // 读取存档

    // 删除存档


    /* ****** Button Action ****** */
    public void leftSwitchAction() {
        if (curIndex > 0) {
            SwitchInterfaceAnimation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>() {
                {
                    add(saveInterfaceList.get(curIndex));
                    add(saveInterfaceList.get(curIndex - 1));
                }
            }, Direction.RIGHT, 100, 10, 1000);
            switchAnimation.makeTransition();
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

    /* ****** Starting ****** */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXView/ArchiveUI.fxml"));
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.TRANSPARENT);
        leftArrow = (Label) scene.lookup("#leftArrow");
        rightArrow = (Label) scene.lookup("#rightArrow");
        upperPane = (AnchorPane) scene.lookup("#upperPane");

//        rightArrow.setVisible(true);

//        loadArchive();
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

}
