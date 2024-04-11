package com.AL1S.ui;

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
 * }
 */

public class MainInterface extends JFrame {
    public MainInterface() {

        /*
         * 初始化三个Panel
         */
        JPanel startPanel = new JPanel();

        JPanel gamePanel = new JPanel();

        JPanel loadPanel = new JPanel();

        // 设置startPanel的两个按钮
        JLabel newGame = new JLabel("New Game");

        JLabel loadGame = new JLabel("Load Game");

        // 设置返回主界面按钮
        JLabel exit = new JLabel("Main Menu");

        // 设置gamePanel的标题Label
        JLabel gameTitle = new JLabel("Game");

        // 设置loadPanel的标题Label
        JLabel loadTitle = new JLabel("Load");


        // 设置cardLayout
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);

        // 添加Start界面两个按钮的监听器
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

        // 设置startPanel布局
        startPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startPanel.setBackground(Color.getHSBColor(0.1667f, 0.33f, 1.00f));

        // 设置gamePanel布局为BoxLayout，左侧分割出100px的Box用于放置方向选择按钮，右侧放置游戏板
        Box mainBox = Box.createHorizontalBox();
        Box controlBox = Box.createVerticalBox();
        Box stageBox = Box.createVerticalBox();
        // 设置controlBox的布局
        controlBox.setPreferredSize(new Dimension(100, 400));




        startPanel.add(newGame);
        startPanel.add(loadGame);

        gamePanel.add(gameTitle);

        loadPanel.add(loadTitle);
        loadPanel.add(exit);

        add(startPanel, "Start");
        add(gamePanel, "Game");
        add(loadPanel, "Load");


        setTitle("Reach2048");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.PINK);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainInterface();
    }

}
