package test;

import main.mapElements.MapDirection;
import main.mapElements.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapDirectionTest {


    @Test
    public void toUnitVector() {
        Vector2d vector = new Vector2d(0,1);
        assertEquals(vector,MapDirection.NORTH.toUnitVector());
    }
}