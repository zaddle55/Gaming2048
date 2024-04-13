package com.AL1S.ui.CustomGraphics;

import javax.swing.*;

public class TitleIcon extends ImageIcon {
    public TitleIcon(String imgPath) {
        super(new ImageIcon(imgPath).getImage().getScaledInstance(80, 80, 1));
    }
}
