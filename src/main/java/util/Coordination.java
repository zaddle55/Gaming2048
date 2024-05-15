package util;

import javafx.scene.layout.AnchorPane;

public class Coordination {
    private double blockWidth;
    private double layoutX;
    private double layoutY;
    private double translateX = 0;
    private double translateY = 0;
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

    public Coordination (int size, AnchorPane gamePane)
    {
        this.space = 11.0;
        this.size = size;
        this.gamePane = gamePane;
        this.blockWidth = getBlockWidth();
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public double getBlockWidth() {
        double boardWidth = gamePane.getPrefWidth();
        return (boardWidth - space * (size + 1)) / size;
    }

    private double gridToLayoutY() {
        return space + gridY * (blockWidth + space) + translateY;
    }

    public double gridToLayoutY(int gridY) {
        return space + gridY * (blockWidth + space) + translateY;
    }

    private double gridToLayoutX() {
        return space + gridX * (blockWidth + space) + translateX;
    }

    public double gridToLayoutX(int gridX) {
        return space + gridX * (blockWidth + space) + translateX;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public double getSpace() {
        return space;
    }
}
