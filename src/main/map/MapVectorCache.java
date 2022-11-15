package main.map;

import main.mapElements.Vector2d;

public class MapVectorCache {
    final Vector2d[] vectors;
    final int xSize;
    final int ySize;
    final Vector2d lowerBoundary;
    final Vector2d upperBoundary;

    public MapVectorCache(Vector2d lowerBoundary, Vector2d upperBoundary) {
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
        int size = (upperBoundary.x - lowerBoundary.x + 1) * (upperBoundary.y - lowerBoundary.y + 1);
        xSize = (upperBoundary.x - lowerBoundary.x + 1);
        ySize = (upperBoundary.y - lowerBoundary.y + 1);
        vectors = new Vector2d[size];

        for (int i = lowerBoundary.y; i <= upperBoundary.y; i++) {
            for (int j = lowerBoundary.x; j <= upperBoundary.x; j++) {
                vectors[i * xSize + j] = new Vector2d(j, i);
            }
        }
    }

    public Vector2d get(Vector2d vec) {
        return vectors[vec.y * xSize + vec.x];
    }

    public Vector2d get(int x, int y) {
        return vectors[y * xSize + x];
    }

    public boolean contains(int x, int y) {
        return (x >= lowerBoundary.x) && (x <= upperBoundary.x) &&
                (y >= lowerBoundary.y) && (y <= upperBoundary.y);
    }
}
