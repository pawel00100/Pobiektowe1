package main.mapElements;

import main.Vector2d;

public enum MapDirection {
    NORTH, EAST, SOUTH, WEST;

    public String toString() {
        switch (this) {
            case NORTH:
                return "Północ";
            case SOUTH:
                return "Południe";
            case WEST:
                return "Zachód";
            case EAST:
                return "Wschód";
            default:
                return null;
        }
    }
        public String toShortString(){
        switch (this) {
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case WEST:
                return "W";
            case EAST:
                return "E";
            default:
                return null;
        }
    }

    public MapDirection next() {
        int nextNum = (this.ordinal() + 1) % 4;
        return MapDirection.values()[nextNum];
    }

    public MapDirection previous() {
        int prevNum = (this.ordinal() + 3) % 4;
        return MapDirection.values()[prevNum];
    }

    private Vector2d vector;

    static {
        NORTH.vector = new Vector2d(0, 1);
        EAST.vector = new Vector2d(1, 0);
        SOUTH.vector = new Vector2d(0, -1);
        WEST.vector = new Vector2d(-1, 0);
    }

    public Vector2d toUnitVector() {
        return this.vector;
    }


}
