package model;

import controller.SwitchInterfaceAnimation;
import util.Time;

import java.time.LocalTime;
import java.util.Date;

public class Save {

    public Grid grid;

    public State state;

    public Date saveDate;
    public LocalTime saveTime;

    public Time playTime;

    // 构造器
    public Save(Grid grid, State state, Time playTime) {
        this.grid = grid;
        this.state = state;
        this.saveDate = new Date();
        // 计算存档时间
        this.saveTime = LocalTime.now();
        this.playTime = playTime;
    }

    // 返回格式 "yyyy-MM-dd"
    public String getDate() {
        StringBuilder sb = new StringBuilder();
        String[] dateArray = saveDate.toString().split(" ");
        sb.append(dateArray[5]).append("-").append(dateArray[1]).append("-").append(dateArray[2]);
        return sb.toString();
    }

    public String getTime() {
        return saveTime.toString();
    }

    public String getState() {
        return state.toString();
    }

    public Grid getGrid() {
        return grid;
    }

    public int getScore() {
        return grid.getScore();
    }

    public int getStep() {
        return grid.getStep();
    }

    public String getMode() {
        return switch(grid.getMode()) {
            case 0 -> "Classic";
            case 1 -> "Endless";
            case 2 -> "Challenge";
            default -> "Unknown";
        };
    }

    public String getPlayTime() {
        return playTime.toString();
    }

    public static enum State {
        WIN, LOSE, IN_PROGRESS;

        @Override
        public String toString() {
            return switch (this) {
                case WIN -> "Win";
                case LOSE -> "Lose";
                case IN_PROGRESS -> "In Progress";
                default -> "Unknown";
            };
        }

        // 各状态对应标签样式(CSS字符串)

    }
}
