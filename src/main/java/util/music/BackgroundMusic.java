package util.music;

import controller.PublicResource;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.awt.event.MouseEvent;

public class BackgroundMusic {

    private static BackgroundMusic instance;

    private MusicList musicList;

    private MusicView musicView;

    private MediaPlayer currentPlayer;

    private boolean isPlaying = false;

    private RotateTransition rotateTransition;

    private BackgroundMusic() {
    }

    public static BackgroundMusic getInstance() {
        if (instance == null) {
            instance = new BackgroundMusic();
        }
        return instance;
    }

    public static void initMusicList() {
        instance = getInstance();
        if (PublicResource.getResource("MusicList") == null) {
            System.out.println("MusicList is null");
            instance.isPlaying = true;
        } else {
            instance.musicList = (MusicList) PublicResource.getResource("MusicList");
        }
    }

    public static void initMusicView(){
        instance.musicView = new MusicView();
        instance.rotateTransition = new RotateTransition(Duration.seconds(5.4), instance.musicView.getPlayButton());
        instance.rotateTransition.setByAngle(360);
        instance.rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        instance.rotateTransition.setAutoReverse(false);
        instance.rotateTransition.setInterpolator(Interpolator.LINEAR); // 设置插值器为线性插值器
        instance.musicView.getPlayButton().setOnMousePressed(mouseEvent -> {
            // 若为左键单击
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (instance.isPlaying) {
                    pause();
                } else {
                    play();
                }
            }
            // 若为右键单击
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                // 弹出工具栏
                MenuItem play = new MenuItem("Play");
                MenuItem pause = new MenuItem("Pause");
                MenuItem next = new MenuItem("Next");
                ContextMenu contextMenu = new ContextMenu();
                play.setOnAction(actionEvent -> play());
                pause.setOnAction(actionEvent -> pause());
                next.setOnAction(actionEvent -> {
                    instance.currentPlayer.stop();
                    instance.currentPlayer = null;
                    instance.isPlaying = false;
                    play();
                });
                contextMenu.getItems().addAll(play, pause, next);
                contextMenu.show(instance.musicView.getPlayButton(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });
    }

    public static AnchorPane getMusicView(){
        return instance.musicView;
    }

    public static void play() {
        if (instance.isPlaying) {
            return;
        }
        instance.isPlaying = true;
        if (instance.currentPlayer == null) {
            instance.currentPlayer = instance.musicList.next();
        }
        instance.currentPlayer.play();
        // 自动循环
        instance.currentPlayer.setOnEndOfMedia(() -> {
            instance.currentPlayer.stop();
            instance.isPlaying = false;
            play();
        });
        instance.rotateTransition.play();
    }

    public static void pause() {
        if (!instance.isPlaying) {
            return;
        }
        instance.isPlaying = false;
        instance.currentPlayer.pause();
        instance.rotateTransition.pause();
    }
}
