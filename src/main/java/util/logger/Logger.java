package util.logger;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class Logger {

    private Exception exception;

//    private LogType logType;

    private MoeLoggerBar displayBar;

    private AnchorPane parentPane;

    private double offsetX = 10.0;
    private double offsetY = 9.0;

    private String logMessage;

    private static final Duration SHOW_UP_DELAY = Duration.millis(0.0);
    private static final Duration SHOW_UP_DURATION = Duration.millis(200);
    private static final Duration HIDE_DELAY = Duration.millis(2000);
    private static final Duration HIDE_DURATION = Duration.millis(200);

    // 出现动画
    private Timeline showUpAnimation;

    // 结束事件
    private Runnable endAction;

    public Logger(AnchorPane parentPane, Exception exception, LogType logType) {
        this.parentPane = parentPane;
        this.exception = exception;
        this.logMessage = exception.getMessage();
        displayBar = new MoeLoggerBar(logMessage, logType);
        // 默认displayBar处于父组件左下角
        displayBar.setLayoutX(offsetX);
        displayBar.setLayoutY(parentPane.getPrefHeight() + offsetY);

        // 出现动画
        showUpAnimation = new Timeline(
                new javafx.animation.KeyFrame(SHOW_UP_DELAY, new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY)),
                new javafx.animation.KeyFrame(SHOW_UP_DURATION.add(SHOW_UP_DELAY), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 -displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY)), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 - displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DURATION.add(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY))), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY))
        );
    }

    // 通过指定的message和logType构造Logger
    public Logger(AnchorPane parentPane, String message, LogType logType) {
        this.parentPane = parentPane;
        this.logMessage = message;
        displayBar = new MoeLoggerBar(logMessage, logType);
        // 默认displayBar处于父组件左下角
        displayBar.setLayoutX(offsetX);
        displayBar.setLayoutY(parentPane.getPrefHeight() + offsetY);

        // 出现动画
        showUpAnimation = new Timeline(
                new javafx.animation.KeyFrame(SHOW_UP_DELAY, new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY)),
                new javafx.animation.KeyFrame(SHOW_UP_DURATION.add(SHOW_UP_DELAY), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 -displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY)), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 - displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DURATION.add(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY))), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY))
        );

    }

    // 通过Message和linkLike构造Logger
    public Logger(AnchorPane parentPane, String message, String linkLike, LogType logType) {
        this.parentPane = parentPane;
        this.logMessage = message;
        displayBar = new MoeLoggerBar(logMessage, linkLike, logType);
        // 默认displayBar处于父组件左下角
        displayBar.setLayoutX(offsetX);
        displayBar.setLayoutY(parentPane.getPrefHeight() + offsetY);

        // 出现动画
        showUpAnimation = new Timeline(
                new javafx.animation.KeyFrame(SHOW_UP_DELAY, new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY)),
                new javafx.animation.KeyFrame(SHOW_UP_DURATION.add(SHOW_UP_DELAY), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 -displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY)), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 - displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DURATION.add(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY))), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY))
        );
    }

    // 通过Message和linkLike, x, y构造Logger
    public Logger(AnchorPane parentPane, String message, String linkLike, double x, double y, LogType logType) {
        this.parentPane = parentPane;
        this.logMessage = message;
        displayBar = new MoeLoggerBar(logMessage, linkLike, logType);
        offsetX = x;
        offsetY = y;
        // 默认displayBar处于父组件左下角
        displayBar.setLayoutX(offsetX);
        displayBar.setLayoutY(parentPane.getPrefHeight() + offsetY);

        // 出现动画
        showUpAnimation = new Timeline(
                new javafx.animation.KeyFrame(SHOW_UP_DELAY, new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY)),
                new javafx.animation.KeyFrame(SHOW_UP_DURATION.add(SHOW_UP_DELAY), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 -displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY)), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 - displayBar.getPrefHeight() - offsetY)),
                new javafx.animation.KeyFrame(HIDE_DURATION.add(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY))), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY))
        );
    }
    // 通过Message x, y构造Logger
    public Logger(AnchorPane parentPane, String message, double x, double y, LogType logType) {
        this.parentPane = parentPane;
        this.logMessage = message;
        displayBar = new MoeLoggerBar(logMessage, logType);
        offsetX = x;
        offsetY = 9.0;
        // 默认displayBar处于父组件左下角
        displayBar.setLayoutX(offsetX);
        displayBar.setLayoutY(parentPane.getPrefHeight() + offsetY + y);

        // 出现动画
        showUpAnimation = new Timeline(
                new javafx.animation.KeyFrame(SHOW_UP_DELAY, new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY + y)),
                new javafx.animation.KeyFrame(SHOW_UP_DURATION.add(SHOW_UP_DELAY), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 -displayBar.getPrefHeight() - offsetY + y)),
                new javafx.animation.KeyFrame(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY)), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() - 70 - displayBar.getPrefHeight() - offsetY + y)),
                new javafx.animation.KeyFrame(HIDE_DURATION.add(HIDE_DELAY.add(SHOW_UP_DURATION.add(SHOW_UP_DELAY))), new javafx.animation.KeyValue(displayBar.layoutYProperty(), parentPane.getPrefHeight() + offsetY + y))
        );
    }

    public void show() {
        // 如果父组件中含有MoeLoggerBar，则稍后再显示
        for (int i = 0; i < parentPane.getChildren().size(); i++) {
            if (parentPane.getChildren().get(i) instanceof MoeLoggerBar) {
                    parentPane.getChildren().remove(i);
                    parentPane.getChildren().add(displayBar);
                    showUpAnimation.play();
                    return;
            }
        }
        parentPane.getChildren().add(displayBar);
        showUpAnimation.play();
    }

    public void setOnEnd(Runnable endAction) {
        this.endAction = endAction;
        showUpAnimation.setOnFinished(event -> {
            if (endAction != null) {
                Platform.runLater(endAction);
            }
        });
    }
}
