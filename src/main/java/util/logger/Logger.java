package util.logger;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

public class Logger {

    private Exception exception;

//    private LogType logType;

    private MoeLoggerBar displayBar;

    private AnchorPane parentPane;

    private double offsetX = 10.0;
    private double offsetY = 0.0;

    private String logMessage;

    public Logger(AnchorPane parentPane, String logMessage, Exception exception) {
        this.parentPane = parentPane;
        this.logMessage = logMessage;
        this.exception = exception;
        displayBar = new MoeLoggerBar(parentPane, logMessage, ShowUpOption.SHOW_UP);
    }

    public void show() {
        // 如果父组件中含有MoeLoggerBar，则稍后再显示
        for (int i = 0; i < parentPane.getChildren().size(); i++) {
            if (parentPane.getChildren().get(i) instanceof MoeLoggerBar) {
                int finalI = i;
                Platform.runLater(() -> {
                    parentPane.getChildren().remove(finalI);
                    parentPane.getChildren().add(displayBar);
                    displayBar.playShowUpAnimation();
                });
            }
        }
    }
}
