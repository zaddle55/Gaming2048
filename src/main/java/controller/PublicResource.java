package controller;


import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.User;
import model.UserManager;
import util.music.MusicList;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: 公共资源类，用于储存运行时的共享资源
 * @Author: Zaddle
 */
public class PublicResource {

    private static PublicResource instance = new PublicResource();

    // 共享资源域
    private User loginUser;
    private UserManager userManager;

    private Map<String, Object> resourcePool = Collections.synchronizedMap(new java.util.HashMap<>()); // “资源名”-“资源”映射

    private PublicResource() {
    }

    public static PublicResource getInstance() {
        return instance;
    }

    // 初始化共享资源（用户、用户管理器等）
    public static void init(User loginUser, UserManager userManager) {
        PublicResource.getInstance().setLoginUser(loginUser);
        PublicResource.getInstance().setUserManager(userManager);

    }
    public static void init(UserManager userManager) {
        PublicResource.getInstance().setUserManager(userManager);
    }
    public static void init(User loginUser) {
        PublicResource.getInstance().setLoginUser(loginUser);
    }

    private void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    // 判断共享资源是否为空
    public static boolean isEmpty() {
        return PublicResource.getInstance().loginUser == null || PublicResource.getInstance().userManager == null;
    }

    // 获取共享资源
    public static User getLoginUser() {
        return PublicResource.getInstance().loginUser;
    }

    public static UserManager getUserManager() {
        return PublicResource.getInstance().userManager;
    }

    // 加载音效资源
    public static void loadSoundResource() {
        // 判断当前进程是否有声音资源
        if (PublicResource.getInstance().resourcePool.get("MoveSound") == null) {
            // 判断当前进程是否为javafx进程
            if (Platform.isFxApplicationThread()) {
                // 加载声音资源
                PublicResource.getInstance().resourcePool.put("MoveSound", new MediaPlayer(new Media(PublicResource.class.getResource("/assets/sound/moveSound.mp3").toString())));
            }
        }
    }

    // 加载音乐资源
    public static void loadMusicResource() {
        // 判断当前进程是否有音乐资源
        if (PublicResource.getInstance().resourcePool.get("MusicList") == null) {
            // 判断当前进程是否为javafx进程
            if (Platform.isFxApplicationThread()) {
                // 加载音乐资源
                PublicResource.getInstance().resourcePool.put("MusicList", new MusicList(
                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/夢の歩みを見上げて.mp3").toString())),
                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/STAR BEAT!.mp3").toString())),
                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/sakura.mp3").toString()))
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/3.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/4.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/5.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/6.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/7.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/8.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/9.mp3").toString())),
//                        new MediaPlayer(new Media(PublicResource.class.getResource("/assets/music/10.mp3").toString()))
                ));
            }
        }
    }

    // 按资源名获取资源
    public static Object getResource(String resourceName) {
        return PublicResource.getInstance().resourcePool.get(resourceName);
    }
}
