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

import java.io.*;

public class Save {
    public static void saveBoard(Board board) {
        try {
            makeDir();
            FileOutputStream fileOut = new FileOutputStream(String.format("./savedata/board%d.ser", board.getID()));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(board);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in ./savedate/board%d.ser\n", board.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Board loadBoard(int ID) {
        try {
            FileInputStream fileIn = new FileInputStream(String.format("./savedata/board%d.ser", ID));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Board board = (Board) in.readObject();
            in.close();
            fileIn.close();
            return board;
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