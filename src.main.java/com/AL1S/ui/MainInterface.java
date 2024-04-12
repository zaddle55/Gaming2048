package com.AL1S.ui;

import com.AL1S.util.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * @description: 游戏主界面
 * @Author: zaddle
 * @Date: 2024/4/11 10:00
 * @Version: 1.0
 * @Description: TODO {
 *                1. 实现游戏主界面的绘制,水平居中放置两个Button "New Game" 和 "Load Game" [ ]
 *                点击"New Game"按钮后,显示cardPanel"Game",隐藏cardPanel"Start"
 *                点击"Load Game"按钮后,显示cardPanel"Panel",隐藏startPanel"Start"
 *                2. 实现游戏界面的绘制,左侧为控制面板,右侧为游戏板 [ ]
 *                控制面板上侧为游戏板大小选择按钮,选择后调用drawBoard方法生成对应大小的游戏板
 *                同时使下方的上下左右按钮，“Undo”按钮“Save and Exit”按钮可用，替换掉原有的按钮
 *                点击上下左右按钮后,调用对应方法移动游戏板
 *                点击“Undo”按钮后,调用undo方法撤销上一步操作
 *                点击“Save and Exit”按钮后,调用save方法保存游戏板并返回主界面
 *                3. 实现游戏读档界面的绘制，居中显示包含./savedata/目录下所有存档的列表 [ ]
 *                点击存档后调用loadBoard方法读取存档并显示游戏界面
 *
 * }
 */

public class MainInterface extends JFrame {

    /* 引入Board */
    private Board board;

    /* JFrame控件 */
    // 初始化三个Panel
    JPanel startPanel = new JPanel();

    JPanel gamePanel = new JPanel();

    JPanel loadPanel = new JPanel();

    CardLayout cardLayout = new CardLayout();


    /* Start界面控件 */
    // 设置startPanel的两个按钮
    JLabel newGame = new JLabel("New Game");

    JLabel loadGame = new JLabel("Load Game");

    // 设置返回主界面按钮
    JLabel exit = new JLabel("Main Menu");

    // 设置gamePanel的标题Label
    JLabel gameTitle = new JLabel("Game");

    // 设置loadPanel的标题Label
    JLabel loadTitle = new JLabel("Load");



    /* Game界面控件 */
    // 设置gamePanel布局为BoxLayout，左侧分割出100px的Box用于放置方向选择按钮，右侧放置游戏板
    Box mainBox = Box.createHorizontalBox();
    Box controlBox = Box.createVerticalBox();
    Box stageBox = Box.createVerticalBox();

    // 设置绘制游戏板按钮
    protected CustomGraphics.CustomButton drawBoard = new CustomGraphics.CustomButton("Draw Board");

    // 设置controlBox的按钮
    protected JComboBox <String> sizeBox = new JComboBox <>();

    // 设置方向键区域
    Box buttonBox = Box.createVerticalBox();
    protected CustomGraphics.dirSelectButton upButton = new CustomGraphics.dirSelectButton(".\\resource\\buttonIcon\\up.png");
    protected CustomGraphics.dirSelectButton downButton = new CustomGraphics.dirSelectButton(".\\resource\\buttonIcon\\down.png");
    protected CustomGraphics.dirSelectButton leftButton = new CustomGraphics.dirSelectButton(".\\resource\\buttonIcon\\left.png");
    protected CustomGraphics.dirSelectButton rightButton = new CustomGraphics.dirSelectButton(".\\resource\\buttonIcon\\right.png");

    // 方向键居中控件
    Box horizontalBoxTop = Box.createHorizontalBox();
    Box horizontalBoxMiddle = Box.createHorizontalBox();
    Box horizontalBoxBottom = Box.createHorizontalBox();

    // 设置Undo键和Save and Return to Menu
    protected CustomGraphics.CustomButton undoButton = new CustomGraphics.CustomButton("Undo");
    CustomGraphics.CustomLabel returnToMenuButton = new CustomGraphics.CustomLabel("<- Return to menu");

    // returnToMenu按钮区域
    Box returnToMenuBox = Box.createHorizontalBox();

    // 游戏板区域
    CustomGraphics.gameBoardBorder gameBoard = new CustomGraphics.gameBoardBorder();

    /* Load 界面控件*/
    public MainInterface() {


        // 设置cardLayout

        setLayout(cardLayout);

        /*
         Start界面配置
         添加Start界面两个按钮的监听器
        */
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(getContentPane(), "Game");
            }

            public void mouseEntered(MouseEvent e) {
                newGame.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                newGame.setForeground(Color.BLACK);
            }
        });

        loadGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(getContentPane(), "Load");
            }

            public void mouseEntered(MouseEvent e) {
                loadGame.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                loadGame.setForeground(Color.BLACK);
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(getContentPane(), "Start");
            }

            public void mouseEntered(MouseEvent e) {
                exit.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                exit.setForeground(Color.BLACK);
            }
        });

        // 设置按钮样式,字体为黑色
        newGame.setPreferredSize(new Dimension(100, 50));
        loadGame.setPreferredSize(new Dimension(100, 50));
        newGame.setFont(new Font("Arial", Font.ITALIC, 16));
        loadGame.setFont(new Font("Arial", Font.ITALIC, 16));
        newGame.setForeground(Color.BLACK);
        loadGame.setForeground(Color.BLACK);
        exit.setPreferredSize(new Dimension(100, 50));
        exit.setFont(new Font("Arial", Font.ITALIC, 16));
        exit.setForeground(Color.BLACK);

        /* Game界面配置 */

        // 设置startPanel布局
        startPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startPanel.setBackground(Color.getHSBColor(0.1667f, 0.33f, 1.00f));

        // 设置controlBox的布局
        controlBox.setPreferredSize(new Dimension(200, 800));
        controlBox.setMinimumSize(new Dimension(200, 800));

        // 设置controlBox的按钮
        sizeBox.addItem("4x4");
        sizeBox.addItem("5x5");
        sizeBox.addItem("6x6");
        sizeBox.addItem("7x7");
        sizeBox.addItem("8x8");
        sizeBox.setMaximumSize(new Dimension(100, 20));

        controlBox.add(Box.createVerticalGlue());
        controlBox.add(sizeBox);
        // 加入水平间隔
        controlBox.add(Box.createVerticalStrut(30));


        // 设置绘制游戏板按钮
        drawBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlBox.add(drawBoard);

        controlBox.add(Box.createVerticalStrut(50));


        // 使四个方向键保持十字对称的操作
        horizontalBoxTop.add(Box.createHorizontalGlue());
        horizontalBoxTop.add(upButton);
        horizontalBoxTop.add(Box.createHorizontalGlue());

        horizontalBoxMiddle.add(Box.createHorizontalGlue());
        horizontalBoxMiddle.add(leftButton);
        horizontalBoxMiddle.add(Box.createHorizontalGlue());
        horizontalBoxMiddle.add(rightButton);
        horizontalBoxMiddle.add(Box.createHorizontalGlue());

        horizontalBoxBottom.add(Box.createHorizontalGlue());
        horizontalBoxBottom.add(downButton);
        horizontalBoxBottom.add(Box.createHorizontalGlue());

        buttonBox.add(Box.createVerticalGlue());
        buttonBox.add(horizontalBoxTop);
        buttonBox.add(horizontalBoxMiddle);
        buttonBox.add(horizontalBoxBottom);
        buttonBox.add(Box.createVerticalGlue());

        // 放置方向键区域
        buttonBox.setPreferredSize(new Dimension(180, 180));
        controlBox.add(buttonBox);


        // Return to Menu监听
        returnToMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(getContentPane(), "Start");
            }
        });

        // 设置Undo键居中
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 设置Return to Menu在左下角

        returnToMenuBox.add(returnToMenuButton);
        returnToMenuBox.add(Box.createHorizontalGlue());

        controlBox.add(undoButton);
        controlBox.add(Box.createVerticalStrut(115));
        controlBox.add(returnToMenuBox);
        controlBox.add(Box.createVerticalGlue());

        // 设置游戏板居中
        gameBoard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 设置stageBox的布局
        stageBox.add(gameBoard); // 之后改到drawBoard按钮触发事件中
        stageBox.setPreferredSize(new Dimension(533, 533));


        mainBox.add(controlBox);
        mainBox.add(Box.createHorizontalStrut(10));
        mainBox.add(stageBox);

        startPanel.add(newGame);
        startPanel.add(loadGame);

        // gamePanel.add(gameTitle);
        gamePanel.add(mainBox);

        loadPanel.add(loadTitle);
        loadPanel.add(exit);

        /* Load界面配置 */

        /* JFrame全局配置 */

        add(startPanel, "Start");
        add(gamePanel, "Game");
        add(loadPanel, "Load");

        setTitle("Reach2048");
        setBounds(500, 200, 800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.PINK);
        setLocationRelativeTo(null);
        setVisible(true);

        /* 游戏逻辑 */
        int size = 4; // 游戏板大小,后删
        drawBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board = new Board(0, size);
                board.init();
                draw(board.getBoard(), size);
            }
            // 隐藏下拉列表和绘制按钮
            public void mouseReleased(MouseEvent e) {
                drawBoard.setVisible(false);
                sizeBox.setVisible(false);
            }
        });

        // 添加上下左右按钮的监听器
        upButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.addToHistory();
                board.slip(0);
                draw(board.getBoard(), size);
            }
        });
        downButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.addToHistory();
                board.slip(1);
                draw(board.getBoard(), size);
            }
        });
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.addToHistory();
                board.slip(2);
                draw(board.getBoard(), size);
            }
        });
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.addToHistory();
                board.slip(3);
                draw(board.getBoard(), size);
            }
        });

        // 添加撤销按钮的监听器
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.undo();
                board.undo();
                draw(board.getBoard(), size);
            }
        });
    }

    // 在游戏板界面实时绘制游戏板
    public void draw(int[][] board, int size) {

        // 获取绘图对象
        Graphics2D g2d = (Graphics2D) gameBoard.getGraphics();

        // 方格大小
        int gridSize = 512 / size;

        // 方格起始坐标修正
        g2d.translate(10, 144);

        // 设置字体为黑体加粗，字号随方格大小变化，居中排列
        g2d.setFont(new Font("Arial", Font.PLAIN, gridSize / 2));


        // 绘制方块
        // 对与每个方块，绘制方块的数字，且数字居中，方块颜色随数字变化，为0的方块不绘制
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    g2d.setColor(numToColor(board[i][j]));
                    g2d.fillRoundRect(j * gridSize, i * gridSize, gridSize, gridSize, 10, 10);
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.drawString(String.valueOf(board[i][j]), j * gridSize + gridSize / 2 - 16, i * gridSize + gridSize / 2 + 20);
                }
            }
        }


        // 设置网格线颜色与样式
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4.0f)); // set the stroke

        // 绘制网格线
        for (int i = 0; i <= size; i++) {
            g2d.drawLine(0, i * gridSize, 512, i * gridSize);
            g2d.drawLine(i * gridSize, 0, i * gridSize, 512);
        }


    }

    // 数字与颜色的转换函数
    public Color numToColor(int num) {
        switch (num) {
            case 2:
                return new Color(238,226,213);
            case 4:
                return new Color(238,222,197);
            case 8:
                return new Color(238,178,115);
            case 16:
                return new Color(246,149,98);
            case 32:
                return new Color(246,125,90);
            case 64:
                return new Color(246,93,49);
            case 128:
                return new Color(238,206,106);
            case 256:
                return new Color(238,203,84);
            case 512:
                return new Color(238,200,65);
            case 1024:
                return new Color(238,197,45);
            case 2048:
                return new Color(238,194,25);
            default:
                return new Color(46, 155, 113, 100);
        }
    }

    public static void main(String[] args) {
        new MainInterface();
    }

}
