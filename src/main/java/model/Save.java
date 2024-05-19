package model;

import util.Time;

import java.util.Date;

public class Save {

    public Grid grid;

    public State state;

    public Date saveDate;
    public Time saveTime;

    // 构造器
    public Save(Grid grid, State state) {
        this.grid = grid;
        this.state = state;
        this.saveDate = new Date();
        // 计算存档时间
        this.saveTime = new Time(saveDate.getTime());
    }

    public String getDate() {
        return saveDate.toString();
    }

    public String getTime() {
        return saveTime.toString();
    }

    public String getState() {
        return state.toString();
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

    }
}
