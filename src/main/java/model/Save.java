package model;

import util.Time;

import java.util.Date;

public class Save {

    public Grid grid;

    public State state;

    public Date saveDate;
    public Time saveTime;

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
