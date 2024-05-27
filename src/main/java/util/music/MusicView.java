package util.music;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MusicView extends AnchorPane {

    private ImageView playButton;

    public MusicView() {
        // 读取FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXView/MusicPlayer.fxml"));

        // 获取节点
        try {
            getChildren().add(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        playButton = (ImageView) lookup("#playButton");
    }

    public ImageView getPlayButton() {
        return playButton;
    }
}
