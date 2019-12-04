package test;

import main.MapDirection;
import main.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapDirectionTest {

    @Test
    public void next() {
        assertEquals(MapDirection.WEST.next(), MapDirection.NORTH);
        assertEquals(MapDirection.NORTH.next(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.next(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(), MapDirection.WEST);
    }

    @Test
    public void previous() {
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertEquals(MapDirection.NORTH.previous(), MapDirection.WEST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.EAST);
    }

    @Test
    public void toUnitVector() {
        Vector2d vector = new Vector2d(0,1);
        assertEquals(vector,MapDirection.NORTH.toUnitVector());
    }
}