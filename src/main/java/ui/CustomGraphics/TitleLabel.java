package ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;

public class TitleLabel extends JLabel {
    // 从指定文本创建标题标签
    public TitleLabel(String text, String iconPath) {
        super(text, new TitleIcon(iconPath), SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 20)); // 设置按钮的字体
        setForeground(new Color(229, 159, 9));
    }
    @Override
    // 绘制标题标签的底部横线
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(229, 159, 9));
        g.drawLine(80, getHeight() - 5, getWidth(), getHeight() - 5);
    }
}
