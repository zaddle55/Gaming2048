
package util;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Timer extends Service<Void> {
    private long startTime;
    private long endTime;
    private int mode;
    private boolean isRunning;
    private static final int SECOND_PER_MILLIS = 1000;

    // 模式选择
    public static int COUNT_UP = 0;
    public static int COUNT_DOWN = 1;

    // 计时器模式
    public Timer(int mode, Time var) {
        this.mode = mode;
        if (mode == COUNT_UP) {
            this.endTime = var.getTime();
        } else {
            this.startTime = var.getTime();
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (mode == COUNT_UP) {
//                    countUp();
                } else {
//                    countDown();
                }
                return null;
            }
        };
    }


    public void stop() {
        this.isRunning = false;
    }

    public long getElapsedTime() {
        return this.endTime - this.startTime;
    }

    public static void main(String[] args) {

    }
}
