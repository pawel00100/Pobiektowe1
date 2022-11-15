package main.map;

import main.mapElements.Vector2d;

import java.util.*;

public class DenseTiles implements Tiles {
    final Tile[] tiles;
    final int xSize;
    final int ySize;
    final Vector2d lowerBoundary;
    final Vector2d  upperBoundary;
    public DenseTiles(RectangularMap map) {
        this.lowerBoundary = map.lowerBoundary;
        this.upperBoundary = map.upperBoundary;
        int size = (upperBoundary.x - lowerBoundary.x + 1) *(upperBoundary.y - lowerBoundary.y + 1);
        xSize = (upperBoundary.x - lowerBoundary.x + 1);
        ySize = (upperBoundary.y - lowerBoundary.y + 1);
        tiles = new Tile[size];

        for (int i = lowerBoundary.y; i <= upperBoundary.y; i++) {
            for (int j = lowerBoundary.x; j <= upperBoundary.x; j++) {
                tiles[i * xSize + j] = new Tile(map, map.vectorCache.get(j, i));
            }
        }
    }


    @Override
    public Tile get(Vector2d vec) {
        return tiles[vec.y * xSize + vec.x];
    }
    @Override
    public Tile get(int x, int y) {
        return tiles[y * xSize + x];
    }

    @Override
    public boolean withinBoundary(int x, int y) {
        return (x >= lowerBoundary.x) && (x <= upperBoundary.x) &&
                (y >= lowerBoundary.y) && (y <= upperBoundary.y);
    }
    @Override
    public boolean withinBoundary(Vector2d vector) {
        return (vector.x >= lowerBoundary.x) && (vector.x <= upperBoundary.x) &&
                (vector.y >= lowerBoundary.y) && (vector.y <= upperBoundary.y);
    }

    @Override
    public boolean containsKey(Vector2d position) {
        return withinBoundary(position) && !get(position).isEmpty();
    }

    @Override
    public Collection<Tile> getAll(){
        return Arrays.asList(tiles);
    }


    @Override
    public void createTileIfNecessary(Vector2d position, RectangularMap map) {

    }

    @Override
    public void removeTileIfNecessary(Vector2d position) {

    }
}
