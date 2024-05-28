package controller;


import model.User;
import model.UserManager;

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
}
