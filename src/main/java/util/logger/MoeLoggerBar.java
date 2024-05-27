package util.logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Grid;

public class MoeLoggerBar extends AnchorPane {

    private static final double PREF_WIDTH = 200;
    private static final double PREF_HEIGHT = 60;
    private static final double FONT_SIZE = 12;
    private static final double PREFIX_SIZE = 13.5;
    private static final double MOE_SIZE = 40;
    private static final double STATE_SIZE = 11;
    private static final double BORDER_RADIUS = 5.0;

    private static final Duration SHOW_UP_DELAY = Duration.millis(0.0);
    private static final Duration SHOW_UP_DURATION = Duration.millis(200);
    private static final Duration HIDE_DELAY = Duration.millis(800);
    private static final Duration HIDE_DURATION = Duration.millis(200);

    // 出现动画
    private Timeline showUpAnimation;

    // 组件
    private ImageView moeIcon;
    private ImageView logIcon;
    private Text logPrefix;
    private Text logMessage;
    private Text logSuffix;
    private double offsetX;
    private double offsetY;

    public MoeLoggerBar(AnchorPane parent, String message, ShowUpOption showUp){

        // MoeIcon
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(PREF_HEIGHT);
        gridPane.setPrefWidth(PREF_HEIGHT);
        gridPane.add(moeIcon, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        moeIcon.setFitHeight(MOE_SIZE);
        moeIcon.setFitWidth(MOE_SIZE);

        HBox hBox = new HBox();
        // LogIcon
        logIcon.setFitHeight(STATE_SIZE);
        logIcon.setFitWidth(STATE_SIZE);
        // LogPrefix
        logPrefix.setFont(Font.font("Arial", PREFIX_SIZE));
        hBox.getChildren().addAll(logIcon, logPrefix);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setLayoutX(PREF_HEIGHT);

        GridPane gridPane1 = new GridPane();
        // LogMessage设置为居中于gridPane1
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.add(logMessage, 0, 0);
        logMessage.setWrappingWidth(PREF_WIDTH - PREF_HEIGHT - 10);
        logMessage.setFont(Font.font("Arial", FONT_SIZE));
        logMessage.setLineSpacing(5);
        logMessage.setText(message);
        gridPane1.setLayoutX(PREF_HEIGHT);
        gridPane1.setLayoutY(15.0);

        getChildren().addAll(gridPane, hBox, gridPane1);

    }

    public void createShowUpAnimation(AnchorPane parent) {
        showUpAnimation = new Timeline(
                // X初值为offsetX, Y初值为offsetY + parent.getPREF_HEIGHT
                new KeyFrame(Duration.millis(0.0), new KeyValue(layoutXProperty(), offsetX), new KeyValue(layoutYProperty(), offsetY + parent.getPrefHeight())),
                // X终值不变,Y终值为parent.getPREF_HEIGHT - PREF_HEIGHT - offsetY
                new KeyFrame(SHOW_UP_DURATION, new KeyValue(layoutXProperty(), offsetX), new KeyValue(layoutYProperty(), parent.getPrefHeight() - PREF_HEIGHT - offsetY)),
                // X终值不变,Y终值为parent.getPREF_HEIGHT - PREF_HEIGHT - offsetY
                new KeyFrame(SHOW_UP_DURATION.add(HIDE_DELAY), new KeyValue(layoutXProperty(), offsetX), new KeyValue(layoutYProperty(), parent.getPrefHeight() - PREF_HEIGHT - offsetY)),
                // X终值不变,Y终值为offsetY + parent.getPREF_HEIGHT
                new KeyFrame(SHOW_UP_DURATION.add(HIDE_DELAY).add(HIDE_DURATION), new KeyValue(layoutXProperty(), offsetX), new KeyValue(layoutYProperty(), offsetY + parent.getPrefHeight()))
        );
    }

    public void playShowUpAnimation() {
        showUpAnimation.play();
    }
}
