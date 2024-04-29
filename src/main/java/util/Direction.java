package util;

public enum Direction {
    UP(-1,0),
    DOWN(1,0),
    LEFT(1,-1),
    RIGHT(1,1);
    public final int incrementI;
    public final int incrementJ;

    private Direction(int incrementI, int incrementJ) {
        this.incrementI = incrementI;
        this.incrementJ = incrementJ;
    }
}
