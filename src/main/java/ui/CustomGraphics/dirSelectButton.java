package ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;

// 绘制方向选择键按钮
public class dirSelectButton extends JButton {
    // 从指定图像路径创建按钮
    public dirSelectButton(String imgPath) {
        super(new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
        setContentAreaFilled(false); // 覆写按钮的背景
        setFocusPainted(false); // 去除按钮的焦点框
        setBorderPainted(false); // 去除按钮的边框
        setPreferredSize(new Dimension(20, 20));
    }
}
