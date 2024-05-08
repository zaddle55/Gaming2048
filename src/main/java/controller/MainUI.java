package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.GameModeFactory;

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
    private AnchorPane loginInterface;
    @FXML
    private AnchorPane mainInterface;
    @FXML
    private AnchorPane optionInterface;

    @FXML
    public void startAction(MouseEvent mouseEvent) {
        try {
            GameUI.init(4, GameModeFactory.CLASSIC);
            GameUI.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.close();
        }
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


    public void enterAction(MouseEvent mouseEvent) {
        TranslateTransition transition1 = new TranslateTransition();
        transition1.setNode(loginInterface);
        transition1.setByX(-700.0);
        transition1.setDuration(javafx.util.Duration.millis(400));
        TranslateTransition transition2 = new TranslateTransition();
        transition2.setNode(mainInterface);
        transition2.setByX(-700.0);
        transition2.setDuration(javafx.util.Duration.millis(400));
        ParallelTransition groupTransition = new ParallelTransition(transition1, transition2);
        groupTransition.play();
    }
}
