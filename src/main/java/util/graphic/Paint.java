package util.graphic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Grid;
import model.Tile;

public class Paint {

    /**
     * @description: 绘制游戏板方法
     * @param grid 游戏板
     * @param gamePane 游戏板
     * @param size 游戏板大小
     */
    public static void draw(Grid grid, AnchorPane gamePane, int size, double space, double strokeWidth) {

        gamePane.getChildren().clear();

        drawBackground(gamePane);
        AnchorPane blockPane = new AnchorPane();

        drawGrid(gamePane, size, space, strokeWidth);

        for (Tile[] line : grid.getTileGrid()) {
            for (Tile tile : line) {
                if (tile != null) {
                    blockPane.getChildren().add(tile);
                }
            }
        }

        blockPane.setLayoutX(0);
        blockPane.setLayoutY(0);

        gamePane.getChildren().add(blockPane);




    }

    public static void drawBackground(AnchorPane gamePane) {

        Pane backgroundPane = new Pane();
        backgroundPane.setLayoutX(0);
        backgroundPane.setLayoutY(0);
        backgroundPane.setPrefSize(gamePane.getPrefWidth(), gamePane.getPrefHeight());
        backgroundPane.setStyle("""
                -fx-background-color: #cbbfb3;
                -fx-background-radius: 3px;
                -fx-background-size: cover;
                -fx-background-position: center;
                """);
        gamePane.getChildren().add(backgroundPane);

    }

    /**
     * @description: 绘制游戏板网格方法
     * @param gamePane 游戏板
     * @param size 游戏板大小
     * @return void
     */
    public static void drawGrid(AnchorPane gamePane, int size, double space, double strokeWidth) {

        double blockWidth = (gamePane.getPrefWidth() - space * (size + 1)) / size;

        for (int i = 1; i < size; i++) {
            Line hLine = new Line();
            hLine.setStartX(space);
            hLine.setEndX(gamePane.getPrefWidth() - space);
            hLine.setStartY(space * 0.5 + i * (blockWidth + space));
            hLine.setEndY(space * 0.5 + i * (blockWidth + space));
            hLine.setStrokeWidth(strokeWidth);
            hLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(hLine);

            Line vLine = new Line();
            vLine.setStartX(space * 0.5 + i * (blockWidth + space));
            vLine.setEndX(space * 0.5 + i * (blockWidth + space));
            vLine.setStartY(space);
            vLine.setEndY(gamePane.getPrefHeight() - space);
            vLine.setStrokeWidth(strokeWidth);
            vLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(vLine);

        }

        Pane borderPane = new Pane();
        borderPane.setLayoutX(0);
        borderPane.setLayoutY(0);
        borderPane.setPrefSize(gamePane.getPrefWidth(), gamePane.getPrefHeight());
        borderPane.setStyle("-fx-border-color: #baac9f;\n" +
                String.format("-fx-border-width: %fpx;\n", strokeWidth) +
                "-fx-border-radius: 3px;");
        gamePane.getChildren().add(borderPane);

    }
}
