package model;

import com.google.gson.annotations.Expose;
import controller.Animation;
import controller.Animation.CombineType;
import controller.MoveAnimation;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.Direction;
import util.GameModeFactory;

import java.util.*;

/**
 * @description: 用于2048的游戏板
 * @Author: zaddle
 * @Date: 2024/4/11 10:00
 * @Version: 2.0
 * @Description: Complete {
 *     1. 实现游戏板的初始化（可重载）
 *     2.实现游戏板四个方向上的移动
 *     3.封装Stack类，用于记录游戏板的历史
 *     4.实现游戏板的撤销功能
 *     5.实现游戏板的基本逻辑（判断游戏是否结束）
 *     6.继承Serializable接口，实现游戏板的序列化以存档
 *     7.计算游戏板的得分
 *     8.设置障碍物
 * }
 * @History:
 */
public class Grid {


    private final int mode;

    private final int size;


    private int[][] board;

    public AnchorPane getGamePane() {
        return gamePane;
    }

    public void setGamePane(AnchorPane gamePane) {
        this.gamePane = gamePane;
    }

    private transient AnchorPane gamePane;
    private transient Tile[][] tileGrid;

    private Stack<Status> history;

    private int score;

    private int step;

    private transient boolean[][] isMerged; // 用于记录格子是否已经合并过
    private transient boolean[][] isNew; // 用于记录格子是否是新生成的

    // 通过初始ID与大小,模式初始化游戏板
    public Grid(int size, int mode) {
        this.size = size;
        this.board = new int[size][size];
        this.tileGrid = new Tile[size][size];
        this.history = new Stack<>();
        this.mode = mode;
        this.isMerged = new boolean[size][size];
        this.isNew = new boolean[size][size];

    }


    public Grid(int[][] board, int mode) {
        this.size = board.length;
        this.board = board;
        this.tileGrid = new Tile[size][size];

        this.isMerged = new boolean[size][size];
        this.isNew = new boolean[size][size];
        this.history = new Stack<>();
        this.mode = 0;
    }

    public void fillTileGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    tileGrid[i][j] = new Tile(board[i][j], j, i, gamePane, size);
                }
            }
        }
    }

    public void fillTileGrid(double space) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    tileGrid[i][j] = new Tile(board[i][j], j, i, gamePane, size, space);
                }
            }
        }
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }
    public void setTileGrid(Tile[][] tileGrid) {
        this.tileGrid = tileGrid;
    }

    public int getSize() {
        return size;
    }

    // 初始化游戏板
    // 随机生成一个“2”或"4"放在游戏板上
    public void init(AnchorPane gamePane) {
        this.gamePane = gamePane;
        Random random = new Random();

        if (mode == GameModeFactory.CHALLENGE) {
            generateRandomTile(3, 1);
        }
        generateRandomTile((random.nextInt(2) + 1) * 2, 1);
        updateBoard();
        addToHistory();
    } // 后改，使用GameModeFactory的map

    public void load(AnchorPane gamePane) {
        this.gamePane = gamePane;
        fillTileGrid();
        addToHistory();
    }

    public void load(AnchorPane gamePane, double space) {
        this.gamePane = gamePane;
        fillTileGrid(space);
        addToHistory();
    }

    // 读取用户操作，移动游戏板
    // 0: 上  1: 下  2: 左  3: 右
    public Map<Tile, Double> move(Direction direction) {
        Tile[][] temp = new Tile[size][size];
        copyTileGrid(tileGrid, temp, gamePane);
        Map<Tile, Double> distanceMap = new HashMap<>();
        slip(direction, distanceMap);
        merge(direction, distanceMap);
        Random random = new Random();
        // 随机生成2或4
        generateRandomTile((random.nextInt(2) + 1) * 2, 1);
        // 判断是否为有效移动
        if (isMatrixEqual(temp, tileGrid)) {
            return null;
        } else {
            step++;
            updateBoard();
            addToHistory();

            return distanceMap;
        }

    }

    // 待修改
    public void slip(Direction direction, Map<Tile, Double> distanceMap) {

        switch (direction) {
            case DOWN:
                for (int i = 0; i < size; i++) {
                    int preEndPoint = size - 1;
                    for (int j = size - 1; j >= 0; j--) {
                        Tile tile = tileGrid[j][i];
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j - 1;
                        } else {
                            distanceMap.put(tile, tile.coordinationTool.gridToLayoutY(preEndPoint) - tile.coordinationTool.gridToLayoutY(j));
                            swapTile(j, i, preEndPoint, i);
                            preEndPoint--;
                        }
                    }
                }
                break;

            case UP:
                for (int i = 0; i < size; i++) {
                    int preEndPoint = 0;
                    for (int j = 0; j < size; j++) {
                        Tile tile = tileGrid[j][i];
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j + 1;
                        } else {
                            distanceMap.put(tile, tile.coordinationTool.gridToLayoutY(j) - tile.coordinationTool.gridToLayoutY(preEndPoint));
                            swapTile(j, i, preEndPoint, i);
                            preEndPoint++;
                        }
                    }
                }
                break;

            case RIGHT:
                for (int i = 0; i < size; i++) {
                    int preEndPoint = size - 1;
                    for (int j = size - 1; j >= 0; j--) {
                        Tile tile = tileGrid[i][j];
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j - 1;
                        } else {
                            distanceMap.put(tile, tile.coordinationTool.gridToLayoutX(preEndPoint) - tile.coordinationTool.gridToLayoutX(j));
                            swapTile(i, j, i, preEndPoint);
                            preEndPoint--;
                        }
                    }
                }
                break;

            case LEFT:
                for (int i = 0; i < size; i++) {
                    int preEndPoint = 0;
                    for (int j = 0; j < size; j++) {
                        Tile tile = tileGrid[i][j];
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getValue() % 2 == 1) {
                            preEndPoint = j + 1;
                        } else {
                            distanceMap.put(tile, tile.coordinationTool.gridToLayoutX(j) - tile.coordinationTool.gridToLayoutX(preEndPoint));
                            swapTile(i, j, i, preEndPoint);
                            preEndPoint++;
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    public void merge(Direction direction, Map<Tile, Double> distanceMap) {
        memSet(isMerged);
        switch (direction) {
            case UP:
                int rear = 0;
                int head = 1;
                for (int i = 0; i < size; i++) {
                    while (head < size) {
                        if (tileGrid[head][i] == null || !isEven(tileGrid[head][i].getValue())) {

                        } else if (tileGrid[rear][i] == null) {

                            distanceMap.put(tileGrid[head][i], distanceMap.get(tileGrid[head][i]) + tileGrid[head][i].coordinationTool.getBlockWidth() + tileGrid[head][i].coordinationTool.getSpace());
                            swapTile(rear, i, head, i);
                            // 窗口回退
                            rear = rear - 2;
                            head = head - 2;

                        } else if (tileGrid[rear][i].getValue() == tileGrid[head][i].getValue() && !isMerged[rear][i] && !isMerged[head][i]) {

                            distanceMap.put(tileGrid[head][i], distanceMap.get(tileGrid[head][i]) + tileGrid[head][i].coordinationTool.getBlockWidth() + tileGrid[head][i].coordinationTool.getSpace());
                            tileGrid[head][i] = null;
                            tileGrid[rear][i] = new Tile(tileGrid[rear][i].getValue() * 2, i, rear, gamePane, size);
                            score += tileGrid[rear][i].getValue();
                            isMerged[rear][i] = true;

                        }

                        rear++;
                        head++;
                    }
                    rear = 0;
                    head = 1;
                }

                break;

            case DOWN:
                rear = size - 1;
                head = size - 2;
                for (int i = 0; i < size; i++) {
                    while (head >= 0) {
                        if (tileGrid[head][i] == null || !isEven(tileGrid[head][i].getValue())) {

                        } else if (tileGrid[rear][i] == null) {

                            distanceMap.put(tileGrid[head][i], distanceMap.get(tileGrid[head][i]) + tileGrid[head][i].coordinationTool.getBlockWidth() + tileGrid[head][i].coordinationTool.getSpace());
                            swapTile(rear, i, head, i);

                            // 窗口回退
                            rear = rear + 2;
                            head = head + 2;

                        } else if (tileGrid[rear][i].getValue() == tileGrid[head][i].getValue() && !isMerged[rear][i] && !isMerged[head][i]) {
                            tileGrid[rear][i] = new Tile(tileGrid[rear][i].getValue() * 2, i, rear, gamePane, size);
                            score += tileGrid[rear][i].getValue();
                            distanceMap.put(tileGrid[head][i], distanceMap.get(tileGrid[head][i]) + tileGrid[head][i].coordinationTool.getBlockWidth() + tileGrid[head][i].coordinationTool.getSpace());
                            tileGrid[head][i] = null;
                            isMerged[rear][i] = true;

                        }

                        rear--;
                        head--;
                    }
                    rear = size - 1;
                    head = size - 2;
                }

                break;

                case LEFT:
                rear = 0;
                head = 1;
                for (int i = 0; i < size; i++) {
                    while (head < size) {
                        if (tileGrid[i][head] == null || !isEven(tileGrid[i][head].getValue())) {

                        } else if (tileGrid[i][rear] == null) {

                            distanceMap.put(tileGrid[i][head], distanceMap.get(tileGrid[i][head]) + tileGrid[i][head].coordinationTool.getBlockWidth() + tileGrid[i][head].coordinationTool.getSpace());
                            swapTile(i, rear, i, head);

                            // 窗口回退
                            rear = rear - 2;
                            head = head - 2;

                        } else if (tileGrid[i][rear].getValue() == tileGrid[i][head].getValue() && !isMerged[i][rear] && !isMerged[i][head]) {
                            tileGrid[i][rear] = new Tile(tileGrid[i][rear].getValue() * 2, rear, i, gamePane, size);
                            score += tileGrid[i][rear].getValue();
                            distanceMap.put(tileGrid[i][head], distanceMap.get(tileGrid[i][head]) + tileGrid[i][head].coordinationTool.getBlockWidth() + tileGrid[i][head].coordinationTool.getSpace());
                            tileGrid[i][head] = null;
                            isMerged[i][rear] = true;

                        }

                        rear++;
                        head++;
                    }
                    rear = 0;
                    head = 1;
                }

                break;

                case RIGHT:
                rear = size - 1;
                head = size - 2;
                for (int i = 0; i < size; i++) {
                    while (head >= 0) {
                        if (tileGrid[i][head] == null || !isEven(tileGrid[i][head].getValue())) {

                        } else if (tileGrid[i][rear] == null) {

                            distanceMap.put(tileGrid[i][head], distanceMap.get(tileGrid[i][head]) + tileGrid[i][head].coordinationTool.getBlockWidth() + tileGrid[i][head].coordinationTool.getSpace());
                            swapTile(i, rear, i, head);

                            // 窗口回退
                            rear = rear + 2;
                            head = head + 2;

                        } else if (tileGrid[i][rear].getValue() == tileGrid[i][head].getValue() && !isMerged[i][rear] && !isMerged[i][head]) {
                            tileGrid[i][rear] = new Tile(tileGrid[i][rear].getValue() * 2, rear, i, gamePane, size);
                            score += tileGrid[i][rear].getValue();
                            distanceMap.put(tileGrid[i][head], distanceMap.get(tileGrid[i][head]) + tileGrid[i][head].coordinationTool.getBlockWidth() + tileGrid[i][head].coordinationTool.getSpace());
                            tileGrid[i][head] = null;
                            isMerged[i][rear] = true;

                        }

                        rear--;
                        head--;
                    }
                    rear = size - 1;
                    head = size - 2;
                }

                break;

            default:
                break;
        }
    }

    // 保存游戏板历史
    private void addToHistory() {
        history.push(new Status(board, score, step, tileGrid, gamePane));
    }

    // 撤销上一步操作
    public void undo() {

        if (!history.isEmpty()) {
            history.pop();
            if (history.isEmpty()) {
                return;
            }
            Status status = history.peek();
            this.board = new int[size][size];
            this.tileGrid = new Tile[size][size];
            copyBoard(status.getBoard(), board);
            score = status.getScore();
            step = status.getStep();
            if (status.getTileGrid() != null){
                copyTileGrid(status.getTileGrid(), tileGrid, gamePane);
            } else {
                fillTileGrid();
            }
        }
    }

    // 判断游戏是否结束
    public boolean isOver() {
        if (!isFull()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j] == board[i][j + 1]) {
                    return false;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[j][i] == board[j + 1][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // 判断游戏是否胜利
    public boolean isWin() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    // 判断游戏板是否已满
    public boolean isFull() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileGrid[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // 在游戏板空余处上随机生成指定数目times个“tarNum”
    // 若无空余位置则不生成
    // 更新isNew用于记录新生成的格子
    public void generateRandomTile(int tarNum, int times) {

        memSet(isNew);

        if (isFull()) {
            return;
        }

        Random random = new Random();
        int count = 0;
        while (count < times) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (tileGrid[x][y] == null) {
                tileGrid[x][y] = new Tile(tarNum, y, x, gamePane, size);
                isNew[x][y] = true;
                count++;
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = tileGrid[i][j] == null ? 0 : tileGrid[i][j].getValue();
            }
        }
    }

    private boolean isMatrixEqual(Tile[][] g1, Tile[][] g2) {
        for (int i = 0; i < g1.length; i++) {
            for (int j = 0; j < g1[0].length; j++) {
                if (g1[i][j] == null && g2[i][j] != null) {
                    return false;
                }
                if (g1[i][j] != null && g2[i][j] == null) {
                    return false;
                }
                if (g1[i][j] != null && g2[i][j] != null && g1[i][j].getValue() != g2[i][j].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void swapTile(int v1, int h1, int v2, int h2) {
        Tile temp = tileGrid[v1][h1];
        tileGrid[v1][h1] = tileGrid[v2][h2];
        tileGrid[v2][h2] = temp;
    }

    // 计算游戏板目前得分
    public int getScore() {

        return score;
    }

    // 判断数字是否为偶数
    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    private static void memSet(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            Arrays.fill(arr[i], false);
        }
    }

    // 游戏模式的getter和setter
    public int getMode() {
        return mode;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getStep() {
        return step;
    }

    public static void copyTileGrid(Tile[][] src, Tile[][] dest, AnchorPane parentPane) {

        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                if (src[i][j] != null) {
                    dest[i][j] = new Tile(src[i][j].getValue(), j, i, parentPane, src.length);
                }
            }
        }
    }

    public static void copyBoard(int[][] src, int[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
        }
    }

    public Boolean[][] getIsNew() {
        Boolean[][] isNew = new Boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                isNew[i][j] = this.isNew[i][j];
            }
        }
        return isNew;
    }

    public Boolean[][] getIsMerge() {
        Boolean[][] isMerge = new Boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                isMerge[i][j] = this.isMerged[i][j];
            }
        }
        return isMerge;
    }

}

class Status {
    private final int[][] board;
    private final int score;
    private final int step;
    private transient final Tile[][] tileGrid;

    public Status(int[][] board, int score, int step, Tile[][] tileGrid, AnchorPane parentPane) {
        this.board = new int[board.length][board[0].length];
        Grid.copyBoard(board, this.board);
        this.score = score;
        this.step = step;
        this.tileGrid = new Tile[tileGrid.length][tileGrid[0].length];
        Grid.copyTileGrid(tileGrid, this.tileGrid, parentPane);
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getStep() {
        return step;
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }
}
