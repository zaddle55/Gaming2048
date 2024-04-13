package com.AL1S.ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;

// 绘制游戏板边界
public class gameBoardBorder extends JPanel {
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
