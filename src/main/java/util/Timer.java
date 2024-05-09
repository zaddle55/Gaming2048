
package util;

import javafx.animation.AnimationTimer;

public abstract class Timer {
    private long startTime;
    private long endTime;
    private boolean isRunning;

    // 模式选择
    public static int COUNT_UP = 0;
    public static int COUNT_DOWN = 1;

    // 计时器模式
    public Timer(int mode, Time var) {
        if (mode == COUNT_UP) {
            this.startTime = System.currentTimeMillis();
        } else {
            this.startTime = 0;
        }
    }

    public abstract void start();

    public void stop() {
        this.endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return this.endTime - this.startTime;
    }

    public static void main(String[] args) {

    }
}
