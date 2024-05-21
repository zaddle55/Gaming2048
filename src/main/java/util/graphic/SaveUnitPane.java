package util.graphic;

import javafx.scene.layout.AnchorPane;

public class SaveUnitPane extends AnchorPane {

    private boolean isClicked = false;

    private AnchorPane previewPane;

    private AnchorPane infoPane;

    public SaveUnitPane() {
        super();
        setPrefSize(162, 221);
        previewPane = new AnchorPane();
        previewPane.setPrefSize(160, 160);
        previewPane.setLayoutX(1);
        previewPane.setLayoutY(1);
        infoPane = new AnchorPane();
        infoPane.setPrefSize(162, 221);
        infoPane.setLayoutX(0);
        infoPane.setLayoutY(0);
        getChildren().addAll(previewPane, infoPane);
        previewPane.toFront(); // previewPane is in front of infoPane
        setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
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
}
