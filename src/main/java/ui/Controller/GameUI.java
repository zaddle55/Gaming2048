package ui.Controller;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import util.ColorMap;
import util.Coordination;
import util.Board;

public class GameUI {

    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button restartButton;

    @FXML
    public void restartAction() {

    }

    @FXML
    public void upAction() {

        // Animation here

        // Update the board
    }

    // 获取gamePane参数
    public double getGamePaneWidth() {
        return gamePane.getWidth();
    }

    public double getLayoutX() {
        return gamePane.getLayoutX();
    }

    public double getLayoutY() {
        return gamePane.getLayoutY();
    }

    // 初始化gamePane网格
    public static void initGamePane(AnchorPane gamePane) {


        int size = 4;
        double space = 11.0;
        double strokeWidth = 11.0;

        double blockWidth = (gamePane.getWidth() - space * (size + 1)) / size;

        Pane backgroundPane = new Pane();
        backgroundPane.setLayoutX(0);
        backgroundPane.setLayoutY(0);
        backgroundPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());
        backgroundPane.setStyle("-fx-background-color: #cbbfb3;\n" +
                "-fx-background-radius: 3px;\n" +
                "-fx-background-size: cover;\n" +
                "-fx-background-position: center;\n" +
                "-fx-border-color: #baac9f;\n" +
                "-fx-border-width: 11px;\n" +
                "-fx-border-radius: 3px;");
        gamePane.getChildren().add(backgroundPane);

        for (int i = 1; i < size; i++) {
            Line hLine = new Line();
            hLine.setStartX(space);
            hLine.setEndX(gamePane.getWidth() - space);
            hLine.setStartY(space + i * (blockWidth + space));
            hLine.setEndY(space + i * (blockWidth + space));
            hLine.setStrokeWidth(strokeWidth);
            hLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(hLine);

            Line vLine = new Line();
            vLine.setStartX(space + i * (blockWidth + space));
            vLine.setEndX(space + i * (blockWidth + space));
            vLine.setStartY(space);
            vLine.setEndY(gamePane.getHeight() - space);
            vLine.setStrokeWidth(strokeWidth);
            vLine.setStroke(Color.rgb(187, 173, 160));
            gamePane.getChildren().add(vLine);

        }
    }

    public static void draw(Board board, AnchorPane gamePane) {

        gamePane.getChildren().clear();

        initGamePane(gamePane);
        AnchorPane blockPane = new AnchorPane();

        int[][] grid = board.getBoard();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    blockPane.getChildren().add(createEntity(grid[i][j], i, j, gamePane));
                }
            }
        }

        blockPane.setLayoutX(0);
        blockPane.setLayoutY(0);


        gamePane.getChildren().add(blockPane);


    }

    private static StackPane createEntity(int val, int x, int y, AnchorPane gamePane) {

        int size = 4;

        StackPane blockPane = new StackPane();
        // 坐标转换
        Coordination coordination = new Coordination(x, y, gamePane, size);
        double layoutX = coordination.getLayoutX();
        double layoutY = coordination.getLayoutY();

        int blockWidth = (int) coordination.getBlockWidth();

        // 创建方块
        Rectangle blockRect = new Rectangle(blockWidth, blockWidth);
        blockRect.setArcWidth(3);
        blockRect.setFill(ColorMap.getColor(val));


        switch (val % 2) {
            case 0:
                // 数字显示
                Text blockText = new Text(String.valueOf(val));
                // 设置字体大小为方格宽度的1/3
                blockText.setFont(javafx.scene.text.Font.font(blockWidth / 2.32));
                // 设置字体颜色
                blockText.setFill(ColorMap.getTextColor(val));
                // 设置字体
                blockText.setStyle("-fx-font-family: 'Arial';");

                blockPane.getChildren().addAll(blockRect, blockText);

                blockPane.setLayoutX(layoutX);
                blockPane.setLayoutY(layoutY);

                return blockPane;

            case 1:
                blockPane.getChildren().add(blockRect);

                blockPane.setLayoutX(layoutX);
                blockPane.setLayoutY(layoutY);

                return blockPane;

            default:
                return null;
        }
    }

}
