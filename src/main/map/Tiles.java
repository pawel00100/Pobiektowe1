package main.map;

import main.mapElements.Vector2d;

import java.util.Collection;
import java.util.List;

public interface Tiles {
    Tile get(Vector2d vec);

    Tile get(int x, int y);

    boolean withinBoundary(int x, int y);

    boolean withinBoundary(Vector2d vector);

    public boolean containsKey(Vector2d position);

    Collection<Tile> getAll();

    void createTileIfNecessary(Vector2d position, RectangularMap map);

    void removeTileIfNecessary(Vector2d position);
}
