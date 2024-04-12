package com.AL1S.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * @description: 静态素材类，用来自定义按钮与形状
 */
public class CustomGraphics {

    // 自定义Label按钮
    static class CustomLabel extends JLabel {

        public CustomLabel(String text) {

            super(text);
            setFont(new Font("Arial", Font.PLAIN, 13)); // 设置按钮的字体
            setForeground(new Color(133, 85, 19));

            addMouseListener(new MouseAdapter() {
                @Override
                // 加入悬浮变色触发
                public void mouseEntered(MouseEvent e) {
                    setForeground(Color.WHITE);
                }

                // 加入退出恢复触发
                @Override
                public void mouseExited(MouseEvent e) {
                    setForeground((new Color(133, 85, 19)));
                }
            });
        }
    }

    // 自定义按钮
    static class CustomButton extends JButton {

        public CustomButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.PLAIN, 13)); // 设置按钮的字体
            setForeground(Color.WHITE); // 设置按钮的字体颜色
            setBackground(new Color(0x776e65)); // 设置按钮的背景颜色
            setContentAreaFilled(false); // 覆写按钮的背景
            setFocusPainted(false); // 去除按钮的焦点框
            setBorderPainted(false); // 去除按钮的边框
        }

        // 绘制圆角矩形按钮作为形状
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(getBackground().darker()); // 颜色为
            } else {
                g.setColor(getBackground()); // 颜色为
            }
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            super.paintComponent(g);
        }
        // 绘制按钮的边框
        @Override
        protected void paintBorder(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(getBackground().darker()); // 颜色为
            } else {
                g.setColor(getBackground()); // 颜色为
            }
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            super.paintBorder(g);
        }

    }

    // 绘制方向选择键按钮
    static class dirSelectButton extends JButton {
        // 从指定图像路径创建按钮
        public dirSelectButton(String imgPath) {
            super(new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            setContentAreaFilled(false); // 覆写按钮的背景
            setFocusPainted(false); // 去除按钮的焦点框
            setBorderPainted(false); // 去除按钮的边框
            setPreferredSize(new Dimension(20, 20));
        }
    }

    // 绘制游戏板边界
    static class gameBoardBorder extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //设置内填充颜色
            g.setColor(Color.GRAY);

            Point p = getLocation();

            // 绘制512 * 512的方形
            int squareSize = 512;
            int x = (p.x + getWidth() - squareSize) / 2;
            int y = (p.y + getHeight() - squareSize) / 2;
            g.fillRoundRect(x, y, squareSize, squareSize, 10, 10);

            // 设置边界颜色
            g.setColor(Color.BLACK);

            // 绘制边界
            g.drawRoundRect(x, y, squareSize, squareSize, 10, 10);

            // 绘制外嵌边框
            int BorderSize = 5;
            g.drawRect(x - BorderSize, y - BorderSize, squareSize + 2 * BorderSize, squareSize + 2 * BorderSize);
        }
    }
}
