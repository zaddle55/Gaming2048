package ui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.ColorMap;
import util.Coordination;

public class Tile extends StackPane {

    private int value;
    private int hIndex;
    private int vIndex;
    private AnchorPane parentPane;
    private double tileSize;
    private Coordination coordinationTool;

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

    private void createTile() {
        double layoutX = coordinationTool.getLayoutX();
        double layoutY = coordinationTool.getLayoutY();

        // 创建方块
        Rectangle blockRect = new Rectangle(tileSize, tileSize);
        blockRect.setArcWidth(3);
        blockRect.setFill(ColorMap.getColor(value));

        switch (value % 2) {
            case 0:
                // 数字显示
                Text blockText = new Text(String.valueOf(value));
                // 设置字体大小为方格宽度的1/3
                blockText.setFont(new Font("PingFang SC", tileSize / 2.0));
                // 设置字体颜色
                blockText.setFill(ColorMap.getTextColor(value));
                // 设置字体

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

    private double calcTileSize() {
        return coordinationTool.getBlockWidth() + 6.0;
    }

    public int gethIndex() {
        return hIndex;
    }

    public int getvIndex() {
        return vIndex;
    }
}
