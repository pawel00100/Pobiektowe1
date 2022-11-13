package main.map.statistics;

import main.map.RectangularMap;
import main.mapElements.Vector2d;

public class MapInformation {

    RectangularMap map;

    public MapInformation(RectangularMap map) {
        this.map = map;
    }

    public Vector2d getUpperBoundary() {
        return map.upperBoundary;
    }

    public Vector2d getLowerBoundary() {
        return map.lowerBoundary;
    }

}
