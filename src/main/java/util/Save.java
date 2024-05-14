package util;

/*
 * @description: 保存游戏板的存档
 * @Author: zaddle
 * @Date: 2024/4/11 10:00
 * @Version: 1.0
 * @Description: Complete {
 *     1. 实现静态方法saveBoard, 将游戏板序列化存档 [√]
 *     2. 实现静态方法loadBoard, 读取游戏板存档并反序列化 [√]
 *     }
 */

import model.Grid;

import java.io.*;

public class Save {
    public static void saveBoard(Grid grid) {
//        try {
//            makeDir();
//            FileOutputStream fileOut = new FileOutputStream(String.format("./savedata/board%d.ser", grid.getID()));
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(grid);
//            out.close();
//            fileOut.close();
//            System.out.printf("Serialized data is saved in ./savedate/board%d.ser\n", grid.getID());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static Grid loadBoard(int ID) {
        try {
            FileInputStream fileIn = new FileInputStream(String.format("./savedata/board%d.ser", ID));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Grid grid = (Grid) in.readObject();
            in.close();
            fileIn.close();
            return grid;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makeDir() {
        File file = new File("./savedata");
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
