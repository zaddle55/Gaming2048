package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.Direction;
import util.GameModeFactory;

import java.util.ArrayList;

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
    private StackPane loadingPane;

    @FXML
    public void startAction() {
//        try {
//            GameUI.init(4, GameModeFactory.CLASSIC);
//            GameUI.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            Stage stage = (Stage) startButton.getScene().getWindow();
//            stage.close();
//        }
        Animation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>(){{
            add(loginInterface);
            add(mainInterface);
            add(optionInterface);
        }}, Direction.LEFT);
        switchAnimation.makeTransition();
        switchAnimation.setOnFinished(event -> {
            loginInterface.setVisible(false);
            optionInterface.setVisible(true);
            loadingPane.setVisible(true);
            LoadingAnimation loadingAnimation = new LoadingAnimation();
            optionInterface.getChildren().add(loadingAnimation.getNode());
            loadingAnimation.makeTransition();

            loadingAnimation.setOnFinished(event1 -> {
                try {
                    GameUI.init(4, GameModeFactory.CLASSIC);
                    GameUI.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Stage stage = (Stage) startButton.getScene().getWindow();
                    stage.close();
                }
            });
            loadingAnimation.play();
        });
        switchAnimation.play(Animation.CombineType.GROUP);
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
        Animation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>(){{
            add(loginInterface);
            add(mainInterface);
            add(optionInterface);
        }}, Direction.LEFT);
        switchAnimation.makeTransition();
        switchAnimation.setOnFinished(event -> {
            loginInterface.setVisible(false);
            mainInterface.setVisible(true);
        });
        switchAnimation.play(Animation.CombineType.GROUP);
    }
}
