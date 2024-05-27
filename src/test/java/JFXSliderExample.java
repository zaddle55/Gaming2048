import com.jfoenix.controls.JFXSlider;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFXSliderExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        JFXSlider slider = new JFXSlider();

        // 设置滑动条的最小值、最大值和当前值
        slider.setMin(4);
        slider.setMax(8);
        slider.setValue(4);

        // 设置滑动条的主要刻度单位和次要刻度的数量
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);

        // 设置是否显示刻度标签和刻度线
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        // 设置滑动条在用户释放鼠标按钮时自动选择最近的刻度
        slider.setSnapToTicks(true);

        // 设置是否显示滑块上的值
        slider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT); // 可以设置为NONE, LEFT, RIGHT

        // 设置滑动条样式
        slider.getStyleClass().add("jfx-slider");

        VBox vbox = new VBox(slider);
        Scene scene = new Scene(vbox, 200, 100);
        scene.getStylesheets().add("/css/MainUI.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}