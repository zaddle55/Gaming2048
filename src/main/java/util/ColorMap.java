package util;

import javafx.scene.paint.Color;
import java.util.HashMap;
public class ColorMap {
    private static final HashMap<Integer, Color> colorMap = new HashMap<>();

    static {
        // 方块颜色映射
        colorMap.put(0, Color.rgb(238, 228, 218, 0.35));
        colorMap.put(2, Color.rgb(238, 228, 218));
        colorMap.put(4, Color.rgb(237, 224, 200));
        colorMap.put(8, Color.rgb(242, 177, 121));
        colorMap.put(16, Color.rgb(245, 149, 99));
        colorMap.put(32, Color.rgb(246, 124, 95));
        colorMap.put(64, Color.rgb(246, 94, 59));
        colorMap.put(128, Color.rgb(237, 207, 114));
        colorMap.put(256, Color.rgb(237, 204, 97));
        colorMap.put(512, Color.rgb(237, 200, 80));
        colorMap.put(1024, Color.rgb(237, 197, 63));
        colorMap.put(2048, Color.rgb(237, 194, 46));
        colorMap.put(4096, Color.rgb(237, 194, 46));
        colorMap.put(8192, Color.rgb(237, 194, 46));
        colorMap.put(16384, Color.rgb(237, 194, 46));
        colorMap.put(32768, Color.rgb(237, 194, 46));
        colorMap.put(65536, Color.rgb(237, 194, 46));
        colorMap.put(131072, Color.rgb(237, 194, 46));
        colorMap.put(262144, Color.rgb(237, 194, 46));
        colorMap.put(524288, Color.rgb(237, 194, 46));
        colorMap.put(1048576, Color.rgb(237, 194, 46));
        colorMap.put(2097152, Color.rgb(237, 194, 46));
        colorMap.put(4194304, Color.rgb(237, 194, 46));
        colorMap.put(8388608, Color.rgb(237, 194, 46));
        colorMap.put(16777216, Color.rgb(237, 194, 46));
        colorMap.put(33554432, Color.rgb(237, 194, 46));

        // 障碍物颜色映射
        colorMap.put(3, Color.rgb(146,137,137));
        colorMap.put(5, Color.rgb(242, 177, 137));
    }

    public static Color getColor(int val) {
        return colorMap.get(val);
    }

    public static Color getTextColor(int val) {
        return val < 8 ? Color.rgb(119, 110, 101) : Color.rgb(249, 246, 242);
    }

}
