package util;

public enum Direction {
    UP(-1,0),
    DOWN(1,0),
    LEFT(0,-1),
    RIGHT(0,1);
    public final int incrementI;
    public final int incrementJ;

    public int incrementI() {
        return incrementI;
    }

    public int incrementJ() {
        return incrementJ;
    }

    private Direction(int incrementI, int incrementJ) {
        this.incrementI = incrementI;
        this.incrementJ = incrementJ;
    }
}
