package util;

import javafx.scene.layout.AnchorPane;

public class Coordination {
    private double blockWidth;
    private double layoutX;
    private double layoutY;
    private double space;
    private final int size;
    private int gridX;
    private int gridY;
    private AnchorPane gamePane;


    public Coordination(int x, int y, AnchorPane gamePane, int size) {

        this.space = 11.0;
        this.size = size;
        this.gridX = x;
        this.gridY = y;
        this.gamePane = gamePane;
        this.blockWidth = getBlockWidth();
        this.layoutX = gridToLayoutX();
        this.layoutY = gridToLayoutY();
    }

    public double getBlockWidth() {
        double boardWidth = gamePane.getWidth();
        return (boardWidth - space * (size + 1)) / size;
    }

    private double gridToLayoutY() {
        return space + gridY * (blockWidth + space) + 2.6;
    }

    private double gridToLayoutX() {
        return space + gridX * (blockWidth + space) + 3.1;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }
}
