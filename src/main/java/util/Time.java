package util;

public class Time {

    private String timeFormat;
    private long time;
    private int hour;
    private int minute;
    private int second;

    public Time(String timeFormat) {

        // 校验时间格式
        if (!timeFormat.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format");
        }

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

    public void reset() {
        this.time = 0;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        this.timeFormat = "00:00:00";
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
