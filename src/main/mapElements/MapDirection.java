package main.mapElements;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

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
                return "X";
        }
    }

    private Vector2d vector;

    static {
        NORTH.vector = new Vector2d(0, 1);
        NORTHEAST.vector = new Vector2d(1, 1);
        EAST.vector = new Vector2d(1, 0);
        SOUTHEAST.vector = new Vector2d(1, -1);
        SOUTH.vector = new Vector2d(0, -1);
        SOUTHWEST.vector = new Vector2d(-1, -1);
        WEST.vector = new Vector2d(-1, 0);
        NORTHWEST.vector = new Vector2d(-1, 1);
    }

    public Vector2d toUnitVector() {
        return this.vector;
    }


}
