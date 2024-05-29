package model;
import util.Saver;


import java.io.IOException;
import java.util.*;
public class UserManager {
    private List<User> userList;
    private static final String savePath = "src/main/resources/general";
    public UserManager() {
        try {
            Saver.makeDir(savePath); // 创建存储用户信息的文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
        userList = new ArrayList<>();
    }
    public User register(String name, String password) throws IOException {
        User user = null;
        if (hasUser(name)) { // 检查用户是否已存在
//            JOptionPane.showMessageDialog(null, "This user has existed!", "Register Failure", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("This user has existed!");
        } else if (!isPasswordValid(password)) { // 检查密码是否有效
//            JOptionPane.showMessageDialog(null, "Invalid password!" + "Your password should contain at least 8 characters.", "Register Failure", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Your password should contain at least 8 characters.");
        } else if (name.equals("") || password.equals("")) { // 检查用户名和密码是否为空
            throw new IllegalArgumentException("Username or password cannot be empty!");
        } else {
            user = new User(name, password, "src/main/resources/savedata/" + name);
            Saver.makeDir(user.getPath());
            userList.add(user);
            // 保存用户信息
            Saver.saveToJson(Saver.buildGson(this), savePath + "/userInfo.json");

        }
        return user;

    }
    public User login(String name, String password) {
        if (userList.isEmpty()){
//            JOptionPane.showMessageDialog(null, "This user doesn't exist!", "Log-in Failure", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("This user doesn't exist!");
        } else {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getName().equals(name)) {
                    if (!userList.get(i).getPassword().equals(password)) {
//                        JOptionPane.showMessageDialog(null, "Incorrect password!", "Log-in Failure", JOptionPane.ERROR_MESSAGE);
                        throw new IllegalArgumentException("Incorrect password!");
                    } else {
//                        JOptionPane.showMessageDialog(null,"Welcome," + name + "!", "Log-in Success", JOptionPane.INFORMATION_MESSAGE);
                        return userList.get(i);
                    }
                }
            }
            throw new IllegalArgumentException("This user doesn't exist!");
        }
    }

    public void deleteUser(User user) throws IOException {
        userList.remove(user);
        Saver.saveToJson(Saver.buildGson(this), savePath);

    }

    public boolean hasUser(String name) {
        boolean hasUser = false;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(name)) {
                hasUser = true;
                break;
            }
        }
        return hasUser;
    }
    public boolean isPasswordValid(String password) {
        boolean isPasswordValid = false;
        if (password.length() >= 8) {
            isPasswordValid = true;
        }
        return isPasswordValid;
    }
    public boolean isPasswordCorrect(String name, String password) {
        boolean isPasswordCorrect = false;
        return isPasswordCorrect;
    }
}