package com.AL1S.ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;

// 自定义按钮
public class CustomButton extends JButton {

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
