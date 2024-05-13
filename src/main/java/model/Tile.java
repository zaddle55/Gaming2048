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

    protected Rectangle blockRect;
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

    protected void createTile() {
        double layoutX = coordinationTool.getLayoutX();
        double layoutY = coordinationTool.getLayoutY();

        // 创建方块
        blockRect = new Rectangle(tileSize, tileSize);
        blockRect.setArcWidth(3);
        blockRect.setFill(ColorMap.getColor(value));

        switch (value % 2) {
            case 0:
                // 数字显示
                Text blockText = new Text(String.valueOf(value));
                // 若val为两位，设置字体大小为方格宽度的1/3
                // 若val超过两位，设置所有字体总宽度为方格宽度的2/3
                blockText.setFont(Font.font("Arial", tileSize / (value > 999 ? 0.9 * Math.floor(Math.log10(value)) : 2)));
//                blockText.setFont(Font.font("Arial", tileSize / 2.5));
                // 设置字体颜色
                blockText.setFill(ColorMap.getTextColor(value));

                getChildren().addAll(blockRect, blockText);

                setLayoutX(layoutX);
                setLayoutY(layoutY);

                break;

            case 1:

                getChildren().add(blockRect);
                setLayoutX(layoutX);
                setLayoutY(layoutY);

                break;

            default:

                break;
        }
    }

    protected double calcTileSize() {
        return coordinationTool.getBlockWidth() + 6.0;
    }

    public int gethIndex() {
        return hIndex;
    }

    public int getvIndex() {
        return vIndex;
    }
}
