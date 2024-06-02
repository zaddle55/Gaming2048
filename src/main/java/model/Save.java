package model;

import util.GameModeFactory;
import util.Time;
import util.Date;


public class Save {

    public String saveName;

    public Grid grid;

    public State state;

    public Date saveDate;
    public Time saveTime;
    public Time playTime;
    // 定义错误存档常量
    public static final Save ERROR_SAVE = new Save("ERROR", new Grid(4, GameModeFactory.CLASSIC), State.IN_PROGRESS, new Time(0, 0, 0), new Date(0, 0, 0), new Time(0, 0, 0));

    // 构造器, 弃用
    public Save(Grid grid, State state, Time playTime) {
        this.grid = grid;
        this.state = state;
        this.saveDate = new Date();
        this.saveTime = new Time();
        this.playTime = playTime;
    }

    // 构造器
    public Save(String saveName, Grid grid, State state, Time playTime) {
        this.saveName = saveName;
        this.grid = grid;
        this.state = state;
        this.playTime = playTime;
        this.saveDate = new Date();
        this.saveTime = new Time();
    }

    // 构造器
    public Save(String saveName, Grid grid, State state, Time playTime, Date saveDate, Time saveTime) {
        this.saveName = saveName;
        this.grid = grid;
        this.state = state;
        this.playTime = playTime;
        this.saveDate = saveDate;
        this.saveTime = saveTime;
    }

    // 自动存档构造器
    public Save(Grid grid, State state, Time playTime, Date saveDate, Time saveTime) {
        this.grid = grid;
        this.state = state;
        this.playTime = playTime;
        this.saveDate = saveDate;
        this.saveTime = saveTime;
        this.saveName = "Auto " + saveDate.getYear() + saveDate.getMonth() + saveDate.getDay()
                        + saveTime.getHour() + saveTime.getMinute() + saveTime.getSecond();
    }

    // 返回格式 "yyyy-MM-dd"
    public String getDate() {
        return saveDate.toString();
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
        WIN("-fx-text-fill: #FACE09;" +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;" +
                "-fx-background-color: rgba(245, 227, 137, 0.4);" +
                "-fx-background-radius: 8px;", 50.0),
        LOSE("-fx-text-fill: #878684;" +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;" +
                "-fx-background-color: rgba(215, 213, 200, 0.6);" +
                "-fx-background-radius: 8px;", 55.0),
        IN_PROGRESS("-fx-text-fill: #1582FE;" +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;" +
                "-fx-text-alignment: center;" +
                "-fx-background-color: rgba(183, 241, 250, 0.6);" +
                "-fx-background-radius: 8px;", 115.0);

        public String style;
        public double width;

        private State(String style, double width) {
            this.style = style;
            this.width = width;
        }

        @Override
        public String toString() {
            return switch (this) {
                case WIN -> "WIN";
                case LOSE -> "LOSE";
                case IN_PROGRESS -> "IN PROGRESS";
                default -> "UNKNOWN";
            };
        }

        public String getStyle() {
            return style;
        }

        // 各状态对应标签样式(CSS字符串)

    }

    public boolean isEquals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Save save = (Save) obj;
        return saveName.equals(save.saveName) && grid.equals(save.grid) && state.equals(save.state)
                && saveDate.equals(save.saveDate) && saveTime.equals(save.saveTime) && playTime.equals(save.playTime);
    }
}
