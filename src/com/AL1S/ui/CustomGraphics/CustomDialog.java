package com.AL1S.ui.CustomGraphics;

import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {

    // 主垂直箱容器
    protected Box mainBox = Box.createVerticalBox();

    public CustomDialog(JFrame frame, String title, String message) {

        super(frame, title, true);

        mainBox.add(Box.createVerticalStrut(30));

        // 对话框内容
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        messageLabel.setForeground(new Color(0, 0, 0));
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        mainBox.add(messageLabel);

        add(mainBox);

        // 其他配置
        setSize(500, 250);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
