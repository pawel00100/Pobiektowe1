package test;

import main.map.IWorldMap;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Vector2d;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangularMapTest {

//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

    @Test
    public void place() {
        RectangularMap map = new RectangularMap(4, 4);

        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 1, 1);

        assertTrue(map.isElementOnMap(animal1));
        assertTrue(map.isElementOnMap(animal2));
    }

    @Test
    public void remove(){
        RectangularMap map = new RectangularMap(4, 4);

        Animal animal1 = new Animal(map); //(2,2)
        map.remove(animal1);

        assertFalse(map.isElementOnMap(animal1));
    }




    @Test
    public void isTileOccupied(){
        IWorldMap map = new RectangularMap(4, 4);
        new Animal(map);  //(2,2)
        new Animal(map, 3, 3);

        assertTrue(map.isTileOccupied(new Vector2d(2, 2)));
        assertTrue(map.isTileOccupied(new Vector2d(3, 3)));

        assertFalse(map.isTileOccupied(new Vector2d(3, 1)));
        assertFalse(map.isTileOccupied(new Vector2d(2, 0)));
    }





}