package model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.ColorMap;
import util.Coordination;

public class Tile extends StackPane {

    protected int value;
    private int hIndex;
    private int vIndex;

    public Rectangle getBlockRect() {
        return blockRect;
    }

    // 方块图形
    protected Rectangle blockRect;
    // 父容器
    private AnchorPane parentPane;
    protected double tileSize;
    protected Coordination coordinationTool;

    // 用于记录移动过程中的父子关系
    private Tile[] parentTile;
    private Tile childTile;

    public Tile(int value, int hIndex, int vIndex, AnchorPane parentPane, int boardSize) {
        super();
        this.value = value;
        this.hIndex = hIndex;
        this.vIndex = vIndex;
        this.parentPane = parentPane;
        this.coordinationTool = new Coordination(hIndex, vIndex, parentPane, boardSize);
        this.tileSize = calcTileSize();
        createTile();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // 创建方块
    private void createTile() {
        double layoutX = coordinationTool.getLayoutX();
        double layoutY = coordinationTool.getLayoutY();

        // 创建方块
        blockRect = new Rectangle(tileSize, tileSize);
        blockRect.setArcWidth(3);
        blockRect.setFill(ColorMap.getColor(value));

        switch (value % 2) {

            case 0: // 方块

                Text blockText = new Text(String.valueOf(value)); // 数字显示

                // 若val为小于三位，设置字体大小为方格宽度的1/3
                // 若val超过三位，设置字体大小自适应
                blockText.setFont(Font.font("Arial", tileSize / (value > 999 ? 0.9 * Math.floor(Math.log10(value)) : 2)));

                blockText.setFill(ColorMap.getTextColor(value)); // 设置字体颜色

                getChildren().addAll(blockRect, blockText);

                // 设置方块位置
                setLayoutX(layoutX);
                setLayoutY(layoutY);
                break;

            case 1: // 障碍物

                getChildren().add(blockRect);
                setLayoutX(layoutX);
                setLayoutY(layoutY);
                break;

            default:
                break;
        }
    }

    public void transVertical(int vIndex) {
        this.vIndex = vIndex;
        setLayoutY(coordinationTool.gridToLayoutY(vIndex));
    }

    public void transHorizontal(int hIndex) {
        this.hIndex = hIndex;
        setLayoutX(coordinationTool.gridToLayoutX(hIndex));
    }

    // 计算方块大小
    protected double calcTileSize() {
        return coordinationTool.getBlockWidth() + 6.0;
    }

    public int gethIndex() {
        return hIndex;
    }

    public int getvIndex() {
        return vIndex;
    }

    public void setParentTile(Tile[] parentTile) {
        this.parentTile = parentTile;
    }

    public Tile[] getParentTile() {
        return parentTile;
    }

    public void setChildTile(Tile childTile) {
        this.childTile = childTile;
    }

    public Tile getChildTile() {
        return childTile;
    }
}
