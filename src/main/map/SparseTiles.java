package main.map;

import main.mapElements.Vector2d;

import java.util.*;

public class SparseTiles implements Tiles {
    private Map<Vector2d, Tile> tilesOnMap = new LinkedHashMap<>();
    final int xSize;
    final int ySize;
    final Vector2d lowerBoundary;
    final Vector2d  upperBoundary;
    public SparseTiles(RectangularMap map) {
        this.lowerBoundary = map.lowerBoundary;
        this.upperBoundary = map.upperBoundary;
        xSize = (upperBoundary.x - lowerBoundary.x + 1);
        ySize = (upperBoundary.y - lowerBoundary.y + 1);
    }


    @Override
    public Tile get(Vector2d vec) {
        return tilesOnMap.get(vec);
    }
    @Override
    public Tile get(int x, int y) {
        return tilesOnMap.get(new Vector2d(x, y));
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

    public boolean containsKey(Vector2d position) {
        return tilesOnMap.containsKey(position);
    }

    @Override
    public Collection<Tile> getAll(){
        Map<Vector2d, Tile> newMap = new LinkedHashMap<>(this.tilesOnMap); //copy, because map may be modified
        return newMap.values();
    }

    @Override
    public void createTileIfNecessary(Vector2d position, RectangularMap map) {
        this.tilesOnMap.put(position, new Tile(map, position));

    }

    @Override
    public void removeTileIfNecessary(Vector2d position) {
        this.tilesOnMap.remove(position);
    }
}
