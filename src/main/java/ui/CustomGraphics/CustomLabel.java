package ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 自定义Label按钮
public class CustomLabel extends JLabel {

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
