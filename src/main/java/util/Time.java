package util;

import java.time.LocalTime;

public class Time implements Comparable<Time>{

    private String timeFormat;
    private long time;
    private int hour;
    private int minute;

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    private int second;

    public final static Time INFINITE = new Time(Long.MAX_VALUE);
    public final static Time ZERO = new Time(0);

    public Time(String timeFormat) {

        // 校验时间格式
        if (!timeFormat.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format");
        }

        this.timeFormat = timeFormat;
        // 解析时间
        String[] timeArray = timeFormat.split(":");
        this.hour = Integer.parseInt(timeArray[0]);
        this.minute = Integer.parseInt(timeArray[1]);
        this.second = Integer.parseInt(timeArray[2]);

        this.time = this.hour * 3600 + this.minute * 60 + this.second;
    }

    public Time(long time) {
        this.time = time;
        this.hour = (int) (time / 3600);
        this.minute = (int) ((time % 3600) / 60);
        this.second = (int) (time % 60);
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.time = hour * 3600 + minute * 60 + second;
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        this.hour = (int) (time / 3600);
        this.minute = (int) ((time % 3600) / 60);
        this.second = (int) (time % 60);
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }

    public void setTimeFormat(String timeFormat) {
        if (!timeFormat.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format");
        }

        String[] timeArray = timeFormat.split(":");
        this.hour = Integer.parseInt(timeArray[0]);
        this.minute = Integer.parseInt(timeArray[1]);
        this.second = Integer.parseInt(timeArray[2]);

        this.time = this.hour * 3600 + this.minute * 60 + this.second;
        this.timeFormat = timeFormat;
    }

    public void addTime(long time) {
        this.time += time;
        this.hour = (int) (this.time / 3600);
        this.minute = (int) ((this.time % 3600) / 60);
        this.second = (int) (this.time % 60);
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }

    public void subtractTime(long time) {
        this.time -= time;
        this.hour = (int) (this.time / 3600);
        this.minute = (int) ((this.time % 3600) / 60);
        this.second = (int) (this.time % 60);
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }

    // 无参构造器，初始化为当前时间
    public Time() {
        this.time = LocalTime.now().toSecondOfDay();
        this.hour = LocalTime.now().getHour();
        this.minute = LocalTime.now().getMinute();
        this.second = LocalTime.now().getSecond();
        this.timeFormat = String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);

    }

    public void reset() {
        this.time = 0;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        this.timeFormat = "00:00:00";
    }

    @Override
    public int compareTo(Time time) {
        return Long.compare(this.time, time.getTime());
    }

    @Override
    public String toString() {
        return timeFormat;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

}
