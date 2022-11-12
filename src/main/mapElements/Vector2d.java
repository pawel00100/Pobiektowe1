package main.mapElements;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d vec) { //temp?
        this.x = vec.x;
        this.y = vec.y;
    }

    public boolean precedes(Vector2d other) {
        return (x <= other.x && y <= other.y);
    }

    public boolean follows(Vector2d other) {
        return (x >= other.x && y >= other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d that))
            return false;
        return (x == that.x && y == that.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-1 * x, -1 * y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += x * 31;
        hash += y * 17;
        return hash;
    }
}
