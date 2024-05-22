package util;

/*
 * @description: 工具类，用于创建存档文件夹，存档和读取存档等操作
 * @Author: zaddle
 * @Date: 2024/5/19 10:00
 * @Version: 2.0
 * @Description: Complete {
 *     }
 */

import model.Grid;

import java.io.*;

public class Saver {

    public static void makeDir() {
        File file = new File("./savedata");
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public void saveToJson(String json, String pathName) {

    }
    public boolean hasFile(String dir, String fileName) {

    }

}
