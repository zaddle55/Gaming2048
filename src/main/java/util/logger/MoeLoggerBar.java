package util.logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.io.IOException;
import java.util.Objects;

public class MoeLoggerBar extends AnchorPane {

    private static final double PREF_WIDTH = 220;
    private static final double PREF_HEIGHT = 70;
    private static final double PREFIX_SIZE = 13.5;
    private static final double BORDER_RADIUS = 10.0;

    // 组件
    private ImageView moeIcon;
    private ImageView logIcon;
    private Text logPrefix;
    private Text logMessage;
    private Text logSuffix;

    private TextFlow logTextFlow;


    public MoeLoggerBar(String message, LogType logType) {
        try {
            getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(logType.getFxViewPath()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logMessage =  (Text) lookup("#message");

        logMessage.setText(message);
    }

    public MoeLoggerBar(String message, String linkLike, LogType logType) {
        try {
            getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(logType.getFxViewPath()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logMessage =  (Text) lookup("#message");
        logMessage.setText(message);

        Text linkLikeText = new Text(linkLike);
        linkLikeText.setFill(javafx.scene.paint.Color.rgb(0, 157, 255));
        // 设置下划线
        linkLikeText.setUnderline(true);

        logTextFlow = (TextFlow) lookup("#textFlow");
        logTextFlow.getChildren().add(linkLikeText);
    }


}
