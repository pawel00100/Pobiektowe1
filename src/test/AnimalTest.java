package test;

import main.map.IWorldMap;
import main.map.RectangularMap;
import main.mapElements.Animal;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    @Test
    public void testToString() {
        IWorldMap map = new RectangularMap(4,4);
        Animal a = new Animal(map);
        assertEquals("N", a.toString());
    }


}