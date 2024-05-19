import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.Arrays;

public class TestGson {

    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Test test = new Test("Tom", 20, 1.8, true);
        test.addHobby("swimming");
        test.addHobby("coding");
        String json = gson.toJson(test);
        System.out.println(json);
        saveToJson(json);
        String json2 = loadFromJson("test.json");
        Test load = gson.fromJson(json2, Test.class);
        System.out.println(load);
    }

    private static void saveToJson(String json) {
        File file = new File("test.json");
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static String loadFromJson(String pathname) {
        File file = new File(pathname);
        Reader reader = null;
        try {
            reader = new FileReader(file);
            char[] buffer = new char[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}

class Test {
    private String name;
    private int age;
    private double height;
    private boolean isStudent;
    private String[] hobbies;
    private transient AnchorPane anchorPane;

    public Test(String name, int age, double height, boolean isStudent) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.isStudent = isStudent;
        this.hobbies = new String[]{"reading", "running"};
        this.anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(100);
        anchorPane.setPrefWidth(100);
        anchorPane.setLayoutX(200);
        anchorPane.setLayoutY(200);
    }

    public Test(String name, int age, double height, boolean isStudent, String[] hobbies) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.isStudent = isStudent;
        this.hobbies = hobbies;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void addHobby(String hobby) {
        String[] newHobbies = new String[hobbies.length + 1];
        for (int i = 0; i < hobbies.length; i++) {
            newHobbies[i] = hobbies[i];
        }
        newHobbies[hobbies.length] = hobby;
        hobbies = newHobbies;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", isStudent=" + isStudent +
                ", hobbies=" + Arrays.toString(hobbies) +
                '}';
    }
}
