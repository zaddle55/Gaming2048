package controller;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Tile;
import util.ColorMap;
import util.graphic.Coordination;
import util.Direction;


import static controller.GameUI.drawBackground;
import static controller.GameUI.drawGrid;

/**
 * @Description: 加载动画类，用于显示加载动画
 * @Author: Zaddle
 * @Date: 2024/5/9 15:00
 */
public class LoadingAnimation extends Animation{

    // 动画默认持续时间
    private static double duration = 400;
    // 动画默认延迟
    private static double delay = 600;
    // 动画默认循环次数
    private static final int CYCLE_TIMES = 2;

    // 文字动画
    private AnimationTimer textAnimation;

    // 加载图形舞台
    private static AnchorPane stage = new AnchorPane();
    // 加载文字区域
    private static StackPane text = new StackPane();
    private VBox node;
    private final static int SIZE = 2;

    public LoadingAnimation() {
        this.node = new VBox();
        // 设置加载动画区域， 居中显示
        node.setPrefWidth(100);
        node.setLayoutX(300);
        node.setLayoutY(60);
        node.setSpacing(20);
        init();
    }

    // 获取组合加载动画区域
    public VBox getNode() {
        return node;
    }

    // 初始化加载动画
    private void init() {

        // 设置加载图形舞台
        stage.setPrefHeight(100);
        stage.setPrefWidth(100);
        stage.setMinHeight(100);
        stage.setMinWidth(100);
        stage.setLayoutX(200);
        stage.setLayoutY(200);

        // 绘制背景
        drawBackground(stage);
        this.tile = new Tile(5, 0, 0, stage, SIZE, 4.5);

        stage.getChildren().add(this.tile);

        // 绘制网格
        drawGrid(stage, SIZE, 4.5, 4.5);

        // 设置加载提示文字
        text.setPrefHeight(40);
        text.setPrefWidth(100);
        Label label = new Label("Loading.");
        label.setStyle("""
                -fx-font-size: 20px;
                -fx-text-fill: #776e65;
                -fx-font-weight: bold;
                -fx-font-family: 'Arial';
                -fx-alignment: center;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);
                """);
        text.getChildren().add(label);

        node.getChildren().addAll(stage, text);
    }

    // 生成加载动画，包括方块移动和颜色变化，共四个方向，每个方向移动结束后颜色变化，循环两次
    @Override
    public void makeTransition() {

        SequentialTransition transition = new SequentialTransition(); // 总动画组
        Coordination coordinationTool = new Coordination(SIZE, stage); // 坐标工具
        coordinationTool.setSpace(4.5); // 设置间隔
        double distance = coordinationTool.getBlockWidth() + coordinationTool.getSpace(); // 移动距离
        Direction[] directions = {Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP};
        // 设置颜色变化
        Color[] colors = {ColorMap.getColor(5),
                Color.rgb(237, 194, 46),
                Color.rgb(237, 224, 200),
                Color.rgb(245, 149, 99),
                ColorMap.getColor(5)};

        int count = 0; // 颜色变化计数
        for (Direction dir : directions) {
            ParallelTransition parallelTransition = new ParallelTransition();
            // 生成一个方向的动画
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), tile);
            translateTransition.setByX(dir.incrementJ() * distance);
            translateTransition.setByY(dir.incrementI() * distance);

            // 生成颜色变化动画
            FillTransition fillTransition = new FillTransition(Duration.millis(duration), tile.getBlockRect());
            fillTransition.setFromValue(colors[count++]);
            fillTransition.setToValue(colors[count]);
            fillTransition.setDuration(Duration.millis(delay));
            fillTransition.setDelay(Duration.millis(duration));

            // 添加到并行动画组
            parallelTransition.getChildren().addAll(translateTransition, fillTransition);
            transition.getChildren().add(parallelTransition);
        }
        transition.setCycleCount(CYCLE_TIMES);
        transition.setDelay(Duration.millis(delay));
        monoTransition = transition;

        // 文字动画
        textAnimation = new AnimationTimer() {
            private int count = 0;

            @Override
            public void handle(long now) {
                if (count % 60 == 0) { // 每100帧更新一次，即每秒更新一次
                    Label label = (Label) text.getChildren().get(0);
                    String text = label.getText();
                    if (text.length() == 10) {
                        label.setText("Loading.");
                    } else {
                        label.setText(text + ".");
                    }
                }
                count++;
            }
        };

    }

    // 播放加载动画
    public void play() {
        super.play(CombineType.MONO);
        textAnimation.start();
    }
}
