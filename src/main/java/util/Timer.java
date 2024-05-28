
package util;


import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

import static java.lang.Thread.sleep;

public class Timer extends Service<Void> {
    private volatile long startTime;
    private volatile long currentTime;
    private volatile long endTime;
    private volatile long interval;
    private final int mode;
    private volatile boolean isRunning;
    private static final int SECOND_PER_MILLIS = 1000;
    // 结束事件
    private volatile Runnable endEvent;
    // 定时事件
    private volatile Runnable timingSession;

    // 模式选择
    public static int COUNT_UP = 0;
    public static int COUNT_DOWN = 1;

    // 计时器模式
    public Timer(Time var1, Time var2) {

        if (var1 == null || var2 == null) {
            throw new IllegalArgumentException("Time can not be null");
        }
        if (var1.compareTo(var2) > 0) {
            this.mode = COUNT_DOWN;
        } else {
            this.mode = COUNT_UP;
        }
        this.startTime = var1.getTime();
        this.endTime = var2.getTime();

    }

    public void setEndEvent(Runnable endEvent) {
        this.endEvent = endEvent;
    }

    public void setTimingSession(Runnable endEvent, Duration interval) {
        this.timingSession = endEvent;
        this.interval = (long) interval.toSeconds();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                currentTime = startTime;
                if (mode == COUNT_UP) {
                    while (true) {

                        if (currentTime > endTime) {
                            break;
                        }

                        Time time = new Time(currentTime);
                        updateMessage(time.getTimeFormat());
                        sleep(SECOND_PER_MILLIS);
                        if (!isRunning) {
                            continue;
                        }
                        ++currentTime;

                        if (timingSession != null && (currentTime - startTime) % interval == 0) {
                            Platform.runLater(timingSession);
                        }
                    }
                } else {

                    while (true) {


                        if (currentTime < 0) {
                            break;
                        }

                        Time time = new Time(currentTime);
                        updateMessage(time.getTimeFormat());
                        sleep(SECOND_PER_MILLIS);
                        if (!isRunning) {
                            continue;
                        }

                        --currentTime;
                        if (timingSession != null && (currentTime - startTime) % interval == 0) {
                            Platform.runLater(timingSession);
                            }
                    }
                }

                if (endEvent != null) Platform.runLater(endEvent);

                return null;
            }
        };
    }

    public void begin() {
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;
        this.start();
    }

    public void reset() {
        if (this.mode == COUNT_UP) {
            this.startTime = 0;
            this.currentTime = 0;
        } else {
            this.currentTime = this.startTime;
        }
        this.isRunning = true;
    }

    public void continueTimer() {
        this.isRunning = true;
    }


    public void stop() {
        this.isRunning = false;
    }

    public long getElapsedTime() {
        return this.currentTime - this.startTime;
    }

    public static void main(String[] args) {

    }
}
