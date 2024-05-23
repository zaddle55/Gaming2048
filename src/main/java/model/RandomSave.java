package model;

import util.GameModeFactory;
import util.Time;
import util.Date;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @description: 测试类，随机生成save对象
 * @version: 1.0
 */
public class RandomSave {

    public static List<Save> randomSave(int num) throws InterruptedException {
        List<Save> saveList = new ArrayList<>();
        for (int i = 0; i < num; i++) {

            saveList.add(makeRandomSave(i));

        }
        return saveList;
    }

    private static Save makeRandomSave(int index) {
        String saveName = "Save " + index;

        // 创建符合2048游戏规则的随机格子
        int[][] gridArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 生成0或2的整数幂
                gridArray[i][j] = (Math.random() >= 0.5 ) ? (int) Math.pow(2, (int) (Math.random() * 13)) : 0;
            }
        }
        int mode = (int) (Math.random() * 4);
        Grid grid = new Grid(gridArray, mode);

        // 随机生成游戏状态
        Save.State state = switch ((int) (Math.random() * 3)) {
            case 0 -> Save.State.IN_PROGRESS;
            case 1 -> Save.State.WIN;
            case 2 -> Save.State.LOSE;
            default -> throw new IllegalStateException("Unexpected value: " + (int) (Math.random() * 3));
        };
        // 随机生成存档日期
        int randomYear = 2020 + (int) (Math.random() * 5);
        int randomMonth = 1 + (int) (Math.random() * 12);
        int randomDay = 1 + (int) (Math.random() * 28);
        Date saveDate = new Date(randomYear, randomMonth, randomDay);
        // 随机生成存档时间
        int randomHour = (int) (Math.random() * 24);
        int randomMinute = (int) (Math.random() * 60);
        int randomSecond = (int) (Math.random() * 60);
        Time saveTime = new Time(randomHour, randomMinute, randomSecond);
        Time playTime = new Time(Math.abs((long) (Math.random() * 100000)));
        System.out.println("Save " + index + " generated");
        return new Save(saveName, grid, state, playTime, saveDate, saveTime);
    }

}
