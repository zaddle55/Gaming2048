package ui.Controller;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class MainUI {

    @FXML
    private Label startButton;
    @FXML
    private Label loadButton;
    @FXML
    private Label settingButton;
    @FXML
    private Label achieveButton;
    @FXML
    private Label exitButton;

    @FXML
    public void startAction(MouseEvent mouseEvent) {
    }

    public void loadAction(MouseEvent mouseEvent) {
    }


    public void settingAction(MouseEvent mouseEvent) {
    }


    public void exitAction(MouseEvent mouseEvent) {
    }


    public void achieveAction(MouseEvent mouseEvent) {
    }

    public void enterStartAction(MouseEvent mouseEvent) {
    }

    public void exitStartAction(MouseEvent mouseEvent) {
        // 创建一个从左上角到右下角的线性渐变，从红色渐变到蓝色
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.RED), new Stop(1, Color.BLUE));

        // 创建一个BackgroundFill，使用刚刚创建的线性渐变
        BackgroundFill backgroundFill = new BackgroundFill(linearGradient, new CornerRadii(15), javafx.geometry.Insets.EMPTY);

        // 创建一个Background，并将BackgroundFill添加到其中
        Background background = new Background(backgroundFill);

        // 将Label的背景设置为刚刚创建的Background
        startButton.setBackground(background);
    }

    public void enterLoadAction(MouseEvent mouseEvent) {
    }

    public void exitLoadAction(MouseEvent mouseEvent) {
    }

    public void enterAchieveAction(MouseEvent mouseEvent) {
    }

    public void exitAchieveAction(MouseEvent mouseEvent) {
    }

    public void enterSettingAction(MouseEvent mouseEvent) {
    }

    public void exitSettingAction(MouseEvent mouseEvent) {
    }

    public void enterExitAction(MouseEvent mouseEvent) {
    }

    public void exitExitAction(MouseEvent mouseEvent) {
    }

    private void slideInAnimation() {
        Timeline timeline = new Timeline();

    }
}
