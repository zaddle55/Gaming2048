package util.graphic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Save;

public class SaveUnitPane extends AnchorPane {

    private boolean isClicked = false;

    private AnchorPane previewPane;

    private AnchorPane infoPane;

    private Button playButton;
    private Button deleteButton;

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }

    private Save save;

    public Button getPlayButton() {
        return playButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    private AnchorPane optionPane = new AnchorPane();


    public SaveUnitPane(Save save) {
        super();
        this.save = save;
        setPrefSize(162, 221);
        previewPane = new AnchorPane();
        previewPane.setPrefSize(160, 160);
        previewPane.setLayoutX(1.0);
        previewPane.setLayoutY(1.0);
        infoPane = new AnchorPane();
        infoPane.setPrefSize(162, 221);
        infoPane.setLayoutX(0);
        infoPane.setLayoutY(0);
        getChildren().addAll(previewPane, infoPane);
        previewPane.toFront(); // previewPane is in front of infoPane
        setStyle("-fx-background-color: #f5f5f5;" +
                " -fx-background-radius: 5px; " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        // 设置选项屏
        optionPane.setPrefSize(160, 160);
        optionPane.setStyle("-fx-background-color: rgba(255,224,147,0.6);");
        optionPane.getStylesheets().add("/css/optionPane.css");

        // 设置选项按钮
        VBox optionBox = new VBox();
        optionBox.setSpacing(15.0);
        playButton = new Button();
        playButton.setGraphic(new ImageView(new Image("/assets/archiveIcon/play.png", 25, 25, true, true)));
        playButton.setAlignment(javafx.geometry.Pos.CENTER);
        playButton.setPrefSize(30, 30);
        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(new Image("/assets/archiveIcon/delete.png", 25, 25, true, true)));
        deleteButton.setAlignment(javafx.geometry.Pos.CENTER);
        deleteButton.setPrefSize(30, 30);
        optionBox.setPrefHeight(75.0);
        optionBox.setPrefWidth(30);
        optionBox.setLayoutX(57);
        optionBox.setLayoutY(36.5);

//        optionBox.setPadding(new javafx.geometry.Insets(44.5, 50, 50, 30));

        optionBox.getChildren().addAll(playButton, deleteButton);
        optionPane.getChildren().add(optionBox);

    }

    public boolean isClicked() {
        return isClicked;
    }

    public void reverseClicked() {
        isClicked = !isClicked;
    }

    public AnchorPane getPreviewPane() {
        return previewPane;
    }

    public AnchorPane getInfoPane() {
        return infoPane;
    }

    public void showOptionPane() {
        previewPane.getChildren().add(optionPane);
    }

    public void hideOptionPane() {
        previewPane.getChildren().remove(optionPane);
    }
}
