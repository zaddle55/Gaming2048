package util.music;

import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicList {

    private List<MediaPlayer> playList;

    private int currentMusicIndex;

    public MusicList(MediaPlayer... players) {
        playList = new ArrayList<>();
        if (players.length > 0) {
            playList.addAll(Arrays.asList(players));
        }
        currentMusicIndex = 0;
    }

    public MediaPlayer next() {
        if (playList.size() == 0) {
            return null;
        }
        currentMusicIndex = (currentMusicIndex + 1) % playList.size();
        return playList.get(currentMusicIndex);
    }
}
