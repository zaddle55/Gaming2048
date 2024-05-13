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
import util.Coordination;
import util.Direction;


import static controller.GameUI.drawBackground;
import static controller.GameUI.drawGrid;

public class LoadingAnimation extends Animation{

    private static double duration = 500;
    private static double delay = 600;
    private static int cycleTimes = 3;

    private AnimationTimer textAnimation;

    private static AnchorPane stage = new AnchorPane();
    private static StackPane text = new StackPane();
    private VBox node;
    private final static int SIZE = 2;

    public LoadingAnimation() {
        this.node = new VBox();
        node.setLayoutX(300);
        node.setLayoutY(60);
        node.setSpacing(20);
        init();
    }

    public VBox getNode() {
        return node;
    }

    private void init() {

        // 设置加载图形舞台
        stage.setPrefHeight(100);
        stage.setPrefWidth(100);
        stage.setMinHeight(100);
        stage.setMinWidth(100);
        stage.setLayoutX(200);
        stage.setLayoutY(200);
        drawBackground(stage);
        this.tile = new Tile(5, 0, 0, stage, SIZE){
            @Override
            protected double calcTileSize() {
                return coordinationTool.getBlockWidth() + 12.0;
            }
        };
        tile.setTranslateX(-6.0);
        tile.setTranslateY(-6.0);
        stage.getChildren().add(this.tile);
        drawGrid(stage, SIZE, 4.5, 4.5);

        // 设置加载提示文字
        text.setPrefHeight(40);
        text.setPrefWidth(100);
        Label label = new Label("Loading.");
        label.setStyle("-fx-font-size: 20px;\n" +
                "-fx-text-fill: #776e65;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-font-family: 'Arial';\n" +
                "-fx-alignment: center;\n" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        text.getChildren().add(label);

        node.getChildren().addAll(stage, text);
    }

    @Override
    public void makeTransition() {

            SequentialTransition transition = new SequentialTransition();
            Coordination coordinationTool = new Coordination(SIZE, stage);
            coordinationTool.setSpace(4.5);
            double distance = coordinationTool.getBlockWidth() + coordinationTool.getSpace();
            Direction[] directions = {Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP};
            Color[] colors = {ColorMap.getColor(5),
                    Color.rgb(237, 194, 46),
                    Color.rgb(237, 224, 200),
                    Color.rgb(245, 149, 99),
                    ColorMap.getColor(5)};
            int count = 0;
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

                parallelTransition.getChildren().addAll(translateTransition, fillTransition);
                transition.getChildren().add(parallelTransition);
            }
            transition.setCycleCount(2);
            transition.setDelay(Duration.millis(delay));
            monoTransition = transition;

            // 文字动画
            textAnimation = new AnimationTimer() {
                private int count = 0;
                @Override
                public void handle(long now) {
                    if (count % 100 == 0) {
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

    public void play() {
        super.play(CombineType.MONO);
        textAnimation.start();
    }
}
