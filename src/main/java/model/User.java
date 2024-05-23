package model;
public class User {
    private String name;
    private String password;
    private String path;
    public User (String name, String password, String path) {
        this.name = name;
        this.password = password;
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getPath() {
        return path;
    }
}