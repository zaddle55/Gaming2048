package controller;

import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Save;
import model.User;
import model.UserManager;
import util.Direction;
import util.GameModeFactory;
import util.Saver;
import util.comparator.CompareByScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import static model.Save.State.LOSE;
import static model.Save.State.WIN;

public class MainUI extends Application {

    public TextField usernameField;
    public PasswordField passwordField;
    @FXML
    private Label startButton;
    @FXML
    private Label loadButton;
    @FXML
    private Label settingButton;
    @FXML
    private Label achieveButton;
    @FXML
    private Label exitButton;
    @FXML
    private AnchorPane loginInterface;
    @FXML
    private AnchorPane mainInterface;
    @FXML
    private AnchorPane optionInterface;
    @FXML
    private StackPane loadingPane;

    private static User user;
    private static UserManager userManager;
    private static int currentIndex = 0;
    private List<AnchorPane> downPanes = new ArrayList<>();

    @FXML
    public void startAction() {
//        try {
//            GameUI.init(4, GameModeFactory.CLASSIC);
//            GameUI.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            Stage stage = (Stage) startButton.getScene().getWindow();
//            stage.close();
//        }
        Animation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>(){{
            add(mainInterface);
            add(optionInterface);
        }}, Direction.LEFT);
        switchAnimation.makeTransition();
        switchAnimation.setOnFinished(event -> {
            loginInterface.setVisible(false);
            optionInterface.setVisible(true);
            loadingPane.setVisible(true);
            LoadingAnimation loadingAnimation = new LoadingAnimation();
            optionInterface.getChildren().add(loadingAnimation.getNode());
            loadingAnimation.makeTransition();

            loadingAnimation.setOnFinished(event1 -> {
                try {
                    GameUI.init(4, GameModeFactory.CLASSIC, user);
                    GameUI.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 移除加载动画

                    Stage stage = (Stage) startButton.getScene().getWindow();
                    stage.close();
                }
            });
            loadingAnimation.play();
        });
        switchAnimation.play(Animation.CombineType.GROUP);
    }

    public void loadAction(MouseEvent mouseEvent) {
        if (user != null){
            ArchiveUI.init(null);
            ArchiveUI.run();
            Stage stage = (Stage) achieveButton.getScene().getWindow();
            stage.close();
        } else {
            // 提示用户登录, 后改
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Please login first");
            alert.setContentText("You need to login to view your achievements");
            alert.showAndWait();
        }
    }


    public void settingAction(MouseEvent mouseEvent) {
    }


    public void exitAction() {
        try {
            // Save the game
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }


    public void achieveAction() {

    }

    public void enterStartAction(MouseEvent mouseEvent) {
    }

    public void exitStartAction(MouseEvent mouseEvent) {

    }

    public void enterLoadAction(MouseEvent mouseEvent) {
    }

    public void exitLoadAction(MouseEvent mouseEvent) {
    }

    public void enterAchieveAction(MouseEvent mouseEvent) {
    }

    public void exitAchieveAction(MouseEvent mouseEvent) {
    }

    public void enterSettingAction(MouseEvent mouseEvent) {
    }

    public void exitSettingAction(MouseEvent mouseEvent) {
    }

    public void enterExitAction(MouseEvent mouseEvent) {
    }

    public void exitExitAction(MouseEvent mouseEvent) {
    }


    public void enterAction() {
        if (user != null) {
            // 初始化user信息
            try {
                PublicResource.init(user); // 将用户信息存入公共资源
                List<Save> saveList = Saver.getSaveList(user);
                user.setBestScore(saveList.stream().max(new CompareByScore()).get().grid.getScore());
                user.setTotalGames(saveList.size());
                user.setTotalWins((int) saveList.stream().filter(save -> save.state == WIN).count());
                user.setTotalLoses((int) saveList.stream().filter(save -> save.state == LOSE).count());
                Saver.saveToJson(Saver.buildGson(userManager), "general/userInfo.json");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Animation switchAnimation = new SwitchInterfaceAnimation(new ArrayList<>(){{
            add(loginInterface);
            add(mainInterface);
        }}, Direction.LEFT);
        switchAnimation.makeTransition();
        switchAnimation.setOnFinished(event -> {
            loginInterface.setVisible(false);
            mainInterface.setVisible(true);
            currentIndex = 1;
        });
        switchAnimation.play(Animation.CombineType.GROUP);
    }

    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            user = userManager.login(username, password);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println(e.getMessage()); // 替换为UI显示
            } else {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText("Please try again later");
                alert.showAndWait();
            }
        } finally {
            if (user != null) {
                // 登录成功
                enterAction();
            }
        }
    }

    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            user = userManager.register(username, password);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println(e.getMessage()); // 替换为UI显示
            } else {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText("Please try again later");
                alert.showAndWait();
            }
        } finally {
            if (user != null) {
                // 注册成功
                enterAction();
            }
        }
    }

    /* ************************** Starting point of the program ************************** */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXView/MainUI.fxml")));

//        primaryStage.setTitle("Reach 2048");
        Scene scene = new Scene(root, 700, 500);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new javafx.scene.image.Image("assets/titleIcon/favicon-32x32.png"));

        primaryStage.initStyle(StageStyle.UNIFIED);

        primaryStage.setMaximized(false);

        primaryStage.setResizable(false);

        // 寻找节点
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        startButton = (Label) scene.lookup("#startButton");
        loadButton = (Label) scene.lookup("#loadButton");
        settingButton = (Label) scene.lookup("#settingButton");
        achieveButton = (Label) scene.lookup("#achieveButton");
        exitButton = (Label) scene.lookup("#exitButton");
        loginInterface = (AnchorPane) scene.lookup("#loginInterface");
        mainInterface = (AnchorPane) scene.lookup("#mainInterface");
        optionInterface = (AnchorPane) scene.lookup("#optionInterface");
        loadingPane = (StackPane) scene.lookup("#loadingPane");

        // 加入界面组
        if (downPanes.isEmpty()) {
            downPanes.add(loginInterface);
            downPanes.add(mainInterface);
            downPanes.add(optionInterface);
        }

        primaryStage.show();
        MainUI.init(null);
        onInterfaceChange(currentIndex);
    }

    public static void init(User u) {
        // 如果公共资源中已有用户信息，直接使用
        if (!PublicResource.isEmpty()) {
            MainUI.userManager = PublicResource.getUserManager();
            MainUI.user = PublicResource.getLoginUser();
            return;
        }
        if (Saver.hasFile("general", "userInfo.json")){
            try {
                String json = Saver.loadFromJson("general/userInfo.json");
                userManager = new GsonBuilder().create().fromJson(json, UserManager.class);
                // 加载到公共资源
                PublicResource.init(userManager);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load user information");
                alert.setContentText("Please try to handle it");
                alert.showAndWait();
            }
        } else {
            userManager = new UserManager();
            PublicResource.init(userManager);
        }
    }


    public static void run() {
        // 若无JavaFX线程，启动JavaFX线程
        if (Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                MainUI mainUI = new MainUI();
                Stage primaryStage = new Stage();
                try {
                    mainUI.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else { //
            Executors.newSingleThreadExecutor().execute(Application::launch);

        }
    }

    public void onInterfaceChange(Object newValue) {

        if (newValue instanceof Integer) {
            if (downPanes.isEmpty()) return;
            downPanes.get((Integer) newValue).setLayoutX(0);
            for (int i = 0; i < downPanes.size(); i++) {
                if (i > (Integer) newValue) {
                    downPanes.get(i).setLayoutX(700);
                } else if (i < (Integer) newValue) {
                    downPanes.get(i).setLayoutX(-700);
                }
            }
        }
    }

}
