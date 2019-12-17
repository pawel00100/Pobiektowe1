package test;

import main.map.RectangularMap;
import main.mapElements.Animal;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    @Test
    public void testToString() {
        RectangularMap map = new RectangularMap(4,4);
        Animal a = new Animal(map);
        assertEquals("N", a.toString());
    }


}