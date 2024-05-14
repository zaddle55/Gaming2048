package model;

import util.Direction;

import java.io.Serializable;
import java.util.*;

/**
 * @description: 用于2048的游戏板
 * @Author: zaddle
 * @Date: 2024/4/11 10:00
 * @Version: 1.0
 * @Description: Complete {
 *     1. 实现游戏板的初始化（可重载）
 *     2.实现游戏板四个方向上的移动
 *     3.封装Stack类，用于记录游戏板的历史
 *     4.实现游戏板的撤销功能
 *     5.实现游戏板的基本逻辑（判断游戏是否结束）
 *     6.继承Serializable接口，实现游戏板的序列化以存档
 *     7.计算游戏板的得分
 * } TODO {1.设置障碍物 }
 * @History:
 */
public class Grid implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int size;
    private int[][] board;
    private static int ID; // 游戏板ID
    private Stack<int[][]> history;
    private int score;
    private int mode; // 游戏模式, 用于后续扩展
    private boolean[][] isMerged; // 用于记录格子是否已经合并过
    private boolean[][] isNew; // 用于记录格子是否是新生成的

    // 通过初始ID与大小,模式初始化游戏板
    public Grid(int ID, int size, int mode) {
        Grid.ID = ID;
        this.size = size;
        this.board = new int[size][size];
        this.history = new Stack<>();
        this.mode = mode;
        this.isMerged = new boolean[size][size];
        this.isNew = new boolean[size][size];
        // ID++;
    }

    //通过二维数组初始化游戏板
    public Grid(int ID, int[][] board) {
        Grid.ID = ID;
        this.size = board.length;
        this.board = board;
        this.isMerged = new boolean[size][size];
        this.isNew = new boolean[size][size];
        this.history = new Stack<>();
    }

    public int[][] getBoard() {
        return board;
    }
    public int getID() {
        return ID;
    }
    public int getSize() {
        return size;
    }

    // 初始化游戏板
    // 随机生成一个“2”或"4"放在游戏板上
    public void init() {
        Random random = new Random();
        generateRandomGrid((random.nextInt(2) + 1) * 2, 1);
        addToHistory();
    } // 后改，使用GameModeFactory的map

    // 读取用户操作，移动游戏板
    // 0: 上  1: 下  2: 左  3: 右
    public void slip(Direction dir) {
        switch (dir) {
            case UP:
                slipUp();
                break; 
            case DOWN:
                slipDown();
                break;
            case LEFT:
                slipLeft();
                break;
            case RIGHT:
                slipRight();
                break;
            default:
                break;
        }
        Random random = new Random();
        generateRandomGrid((random.nextInt(2) + 1) * 2, 1);
    }

    // 待修改
    public void _slip(int direction) {

        //
        for (int i = 0; i < size; i++) {

            List<ArrayList<Integer>> elementList = new ArrayList<>();
            List<Integer> barrierList = new ArrayList<>();

            int count = 0;

            //
            for (int j = 0; j < size; j++) {

                if (board[i][j] != 0 && isEven(board[i][j])) {
                    elementList.get(count).add(board[i][j]);
                }
                if (!isEven(board[i][j])) {
                    barrierList.add(board[i][j]);
                    count++;
                }
            }

            for (List<Integer> splitList : elementList) {
                for (int j = 0; j < splitList.size() - 1; j++) {
                    if (splitList.get(j).equals(splitList.get(j + 1))) {
                        splitList.set(j, splitList.get(j) * 2);
                        splitList.remove(j + 1);
                    }
                }
            }

            count = 0;
            //
            for (int j = 0; j < size; j++) {
                if (j != barrierList.get(count)) {
                    board[i][j] = elementList.get(count).get(j);
                } else {
                    count++;
                }
            }
        }

        Random random = new Random();
        generateRandomGrid((random.nextInt(2) + 1) * 2, 1);
    }

    public void slipUp() {

        for (int i = 0; i < size; i++) {
            List<Integer> elementList = new ArrayList<>();

            // 从每一列的读取非0元素存储到elementList中
            for (int j = 0; j < size; j++) {
                if (board[j][i] != 0) {
                    elementList.add(board[j][i]);
                }
            }

            // 从列表头部开始合并相同元素
            for (int j = 0; j < elementList.size() - 1; j++) {
                if (elementList.get(j).equals(elementList.get(j + 1))) {
                    elementList.set(j, elementList.get(j) * 2);
                    elementList.remove(j + 1);
                }
            }

            // 将elementList中的元素写回到游戏板中
            for (int j = 0; j < size; j++) {
                if (j < elementList.size()) {
                    board[j][i] = elementList.get(j);
                } else {
                    board[j][i] = 0;
                }
            }
        }
    }

    public void slipDown() {

        for (int i = 0; i < size; i++) {
            List<Integer> elementList = new ArrayList<>();

            for (int j = size - 1; j >= 0; j--) {
                if (board[j][i] != 0) {
                    elementList.add(board[j][i]);
                }
            }

            for (int j = 0; j < elementList.size() - 1; j++) {
                if (elementList.get(j).equals(elementList.get(j + 1))) {
                    elementList.set(j, elementList.get(j) * 2);
                    elementList.remove(j + 1);
                }
            }

            for (int j = size - 1; j >= 0; j--) {
                if (size - 1 - j < elementList.size()) {
                    board[j][i] = elementList.get(size - 1 - j);
                } else {
                    board[j][i] = 0;
                }
            }
        }
    }

    public void slipLeft() {

        for (int i = 0; i < size; i++) {
            List<Integer> elementList = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    elementList.add(board[i][j]);
                }
            }

            for (int j = 0; j < elementList.size() - 1; j++) {
                if (elementList.get(j).equals(elementList.get(j + 1))) {
                    elementList.set(j, elementList.get(j) * 2);
                    elementList.remove(j + 1);
                }
            }

            for (int j = 0; j < size; j++) {
                if (j < elementList.size()) {
                    board[i][j] = elementList.get(j);
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    public void slipRight() {

        for (int i = 0; i < size; i++) {
            List<Integer> elementList = new ArrayList<>();

            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] != 0) {
                    elementList.add(board[i][j]);
                }
            }

            for (int j = 0; j < elementList.size() - 1; j++) {
                if (elementList.get(j).equals(elementList.get(j + 1))) {
                    elementList.set(j, elementList.get(j) * 2);
                    elementList.remove(j + 1);
                }
            }

            for (int j = size - 1; j >= 0; j--) {
                if (size - 1 - j < elementList.size()) {
                    board[i][j] = elementList.get(size - 1 - j);
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }



    // 交换指定索引的两个格子
    public void swapGrid(int x1, int y1, int x2, int y2) {
        int temp = board[x2][y2];
        board[x2][y2] = board[x1][y1];
        board[x1][y1] = temp;
    }

    // 保存游戏板历史
    public void addToHistory() {
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, size);
        }
        // 校验是否有重复的历史
        if (!history.isEmpty() && Arrays.deepEquals(history.peek(), newBoard)) {
            return;
        }
        history.push(newBoard);
    }

    // 撤销上一步操作
    public void undo() {
        if (!history.isEmpty()) {
            board = history.pop();
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
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // 在游戏板空余处上随机生成指定数目times个“tarNum”
    // 若无空余位置则不生成
    public void generateRandomGrid(int tarNum, int times) {

        if (isFull()) {
            return;
        }

        Random random = new Random();
        int count = 0;
        while (count < times) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (board[x][y] == 0) {
                board[x][y] = tarNum;
                count++;
            }
        }
    }

    // 重载toString方法，用于输出游戏板
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // 计算游戏板目前得分
    public int getScore() {
        score = 0;
        for (int[] ints : board) {
            for (int j = 0; j < size; j++) {
                score += ints[j];
            }
        }
        return score;
    }

    // 判断数字是否为偶数
    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    // 游戏模式的getter和setter
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
