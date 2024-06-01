package util;

/*
 * @description: 工具类，用于创建存档文件夹，存档和读取存档等操作
 * @Author: zaddle
 * @Date: 2024/5/19 10:00
 * @Version: 2.0
 * @Description: Complete {
 *     }
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Save;
import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Saver {
    public static boolean makeDir(String dir) {
        if (!isPathNameValid(dir)) {
            return false;
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
            return true;
        } else {
            return false;
        }

    }
    public static boolean hasFile(String dir, String fileName) {
        // 判断目标路径是否存在指定文件
        File file = new File(dir + "/" + fileName);
        if (!file.exists()) {
            return false;
        }
        return true;
    }
    public static void saveToJson(String json, String pathName) throws IOException {
        if (!isPathNameValid(pathName)) {
            throw new IOException("Invalid path name");
        }
        File file = new File(pathName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }
    public static String loadFromJson(String pathName) throws IOException {
        if (!isPathNameValid(pathName)) {
            throw new IOException("Invalid path name");
        }
        File file = new File(pathName);
        if (!file.exists()) {
            throw new IOException("File not found");
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        fileReader.close();
        return stringBuilder.toString();

    }
    public static boolean changeFileName(String oldName, String newName) {
        return true;
    }
    public static boolean isPathNameValid(String pathName) {
        if (pathName == null || pathName.isEmpty()) {
            return false;
        } else if (pathName.contains("\\") || pathName.contains("/") || pathName.contains(".") || pathName.contains(":")) {
            return true;
        } else {
            return false;
        }
    }

    public static String buildGson(Object object) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static List<Save> getSaveList(User currentUser){
        String path = currentUser.getPath();
        List<File> files;
        // 读取存档文件夹下的存档文件
        try {
            files = getFilesBySuffix(path, ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Save> saveList = new ArrayList<>();
            for (File file : files) {
                try {
                    String json = loadFromJson(file.getPath());
                    Save save = new Gson().fromJson(json, Save.class);
                    saveList.add(save);
                } catch (Exception e) {
                    saveList.add(Save.ERROR_SAVE);
                }
            }
            return saveList;
        }
    }

    public static List<File> getFilesBySuffix(String path, String suffix) throws IOException {
        String directory = path; // 替换为你的目录路径
        String fileExtension = ".json"; // 替换为你想要查找的文件后缀

        Stream<Path> paths = Files.walk(Paths.get(directory));
        List<File> result = paths
                .filter(Files::isRegularFile) // 过滤出文件（非目录）
                .map(Path::toFile) // 将路径转换为File对象
                .filter(file -> file.getName().endsWith(fileExtension)) // 过滤出指定后缀的文件
                .collect(Collectors.toList()); // 收集结果

        return result;

    }

    public static void deleteSave(User currentUser, Save save) {
        String path = currentUser.getPath();
        File file = new File(path + "/" + save.saveName + ".json");
        if (file.exists()) {
            file.delete();
        } else {
            throw new RuntimeException("File not found");
        }
    }
}