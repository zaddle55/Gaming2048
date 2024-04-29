package util;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    public static int incrementI;
    public static int incrementJ;

    static {
        UP.incrementI = 0;
        incrementJ = 0;
    }
}
