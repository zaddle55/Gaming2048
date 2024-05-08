package controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import model.Tile;
import model.TileList;
import model.Grid;
import util.Coordination;
import util.Direction;

import java.util.List;

public class MoveAnimation extends Animation {
    private Direction direction;
    private Grid stageGrid;


    public MoveAnimation(List<Node> nodes, Direction direction, Grid grid, Coordination coordinationTool) {
        super(nodes, coordinationTool);
        this.direction = direction;
        this.stageGrid = grid;
    }


    // 移动动画具体实现
    public void makeTransition() {
        TileList tileList = toTileList(tiles);
        switch (direction) {
            case DOWN:
                for (int i = 0; i < stageGrid.getSize(); i++) {
                    int preEndPoint = stageGrid.getSize() - 1;
                    for (int j = stageGrid.getSize() - 1; j >= 0; j--) {
                        Tile tile = tileList.get(i, j);
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j - 1;
                        } else {
                            if (isMerge(tile, tileList)) {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByY(coordinationTool.gridToLayoutY(preEndPoint + 1) - coordinationTool.gridToLayoutY(j));
                                transitions.add(transition);
                            } else {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByY(coordinationTool.gridToLayoutY(preEndPoint) - coordinationTool.gridToLayoutY(j));
                                transitions.add(transition);
                                preEndPoint--;
                            }
                        }
                    }
                }
                break;

            case UP:
                for (int i = 0; i < stageGrid.getSize(); i++) {
                    int preEndPoint = 0;
                    for (int j = 0; j < stageGrid.getSize(); j++) {
                        Tile tile = tileList.get(i, j);
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j + 1;
                        } else {
                            if (isMerge(tile, tileList)) {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByY(coordinationTool.gridToLayoutY(preEndPoint - 1) - coordinationTool.gridToLayoutY(j));
                                transitions.add(transition);
                            } else {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByY(coordinationTool.gridToLayoutY(preEndPoint) - coordinationTool.gridToLayoutY(j));
                                transitions.add(transition);
                                preEndPoint++;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                for (int i = 0; i < stageGrid.getSize(); i++) {
                    int preEndPoint = stageGrid.getSize() - 1;
                    for (int j = stageGrid.getSize() - 1; j >= 0; j--) {
                        Tile tile = tileList.get(j, i);
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j - 1;
                        } else {
                            if (isMerge(tile, tileList)) {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByX(coordinationTool.gridToLayoutX(preEndPoint + 1) - coordinationTool.gridToLayoutX(j));
                                transitions.add(transition);
                            } else {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByX(coordinationTool.gridToLayoutX(preEndPoint) - coordinationTool.gridToLayoutX(j));
                                transitions.add(transition);
                                preEndPoint--;
                            }
                        }
                    }
                }
                break;

            case LEFT:
                for (int i = 0; i < stageGrid.getSize(); i++) {
                    int preEndPoint = 0;
                    for (int j = 0; j < stageGrid.getSize(); j++) {
                        Tile tile = tileList.get(j, i);
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j + 1;
                        } else {
                            if (isMerge(tile, tileList)) {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByX(coordinationTool.gridToLayoutX(preEndPoint - 1) - coordinationTool.gridToLayoutX(j));
                                transitions.add(transition);
                            } else {
                                TranslateTransition transition = new TranslateTransition(Duration.millis(duration), tile);
                                transition.setByX(coordinationTool.gridToLayoutX(preEndPoint) - coordinationTool.gridToLayoutX(j));
                                transitions.add(transition);
                                preEndPoint++;
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }

        groupTransition = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
    }

    // 将List<Tile>转换为TileList
    private static TileList toTileList(List<Tile> tiles) {
        TileList tileList = new TileList(tiles.size());
        for (Tile tile : tiles) {
            tileList.add(tile);
        }
        return tileList;
    }

    // 合并判断，需改进
    private boolean isMerge(Tile tile, TileList tiles) {
        int hIndex = tile.gethIndex();
        int vIndex = tile.getvIndex();
        int value = tile.getValue();
        switch (direction) {
            case DOWN:
                if (vIndex == stageGrid.getSize() - 1) {
                    return false;
                }
                Tile nextTile = tiles.get(hIndex, vIndex + 1);
                return nextTile != null && nextTile.getValue() == value;
            case UP:
                if (vIndex == 0) {
                    return false;
                }
                Tile preTile = tiles.get(hIndex, vIndex - 1);
                return preTile != null && preTile.getValue() == value;
            case RIGHT:
                if (hIndex == stageGrid.getSize() - 1) {
                    return false;
                }
                Tile rightTile = tiles.get(hIndex + 1, vIndex);
                return rightTile != null && rightTile.getValue() == value;
            case LEFT:
                if (hIndex == 0) {
                    return false;
                }
                Tile leftTile = tiles.get(hIndex - 1, vIndex);
                return leftTile != null && leftTile.getValue() == value;
            default:
                return false;
        }
    }

}
