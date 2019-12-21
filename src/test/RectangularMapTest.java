package test;

import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Vector2d;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangularMapTest {
    private RectangularMap map1, map2;
    private Animal animal1, animal2, animal3;

    @Before
    public void setUp() {
        this.map1 = new RectangularMap(20, 10, 0, 0.4);
        this.map2 = new RectangularMap(200,200, 0, 0.25);
        this.animal1 = new Animal(map1);
        this.animal2 = new Animal(map1, 3, 3);
        this.animal3 = new Animal(map2, 5, 5);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void place() {

        assertTrue(map1.isElementOnMap(animal1));
        assertTrue(map1.isElementOnMap(animal2));
    }

    @Test
    public void remove() {
        map1.remove(animal1);

        assertFalse(map1.isElementOnMap(animal1));
    }

    @Test
    public void isTileOccupied() {
        new Animal(map1, 3, 3);

        assertTrue(map1.isTileOccupied(new Vector2d(2, 2)));
        assertTrue(map1.isTileOccupied(new Vector2d(3, 3)));

        assertFalse(map1.isTileOccupied(new Vector2d(3, 1)));
        assertFalse(map1.isTileOccupied(new Vector2d(2, 0)));
    }

    @Test
    public void isElementOnMap() {
        assertTrue(this.map1.isElementOnMap(animal1));
        assertFalse(this.map1.isElementOnMap(animal3));
    }

    @Test
    public void getAnimalByNumber() {
        assertEquals(animal1, map1.getAnimalByNumber(0));
        assertEquals(animal2, map1.getAnimalByNumber(1));
        assertEquals(animal3, map2.getAnimalByNumber(0));
    }

    @Test
    public void isAnimalOnTile() {
        assertTrue(map1.isAnimalOnTile(new Vector2d(2,2)));
        assertTrue(map1.isAnimalOnTile(new Vector2d(3,3)));
        assertTrue(map2.isAnimalOnTile(new Vector2d(5,5)));
        assertFalse(map1.isAnimalOnTile(new Vector2d(5,5)));
    }


    @Test
    public void isTileJungle() {
        assertTrue(map1.isTileJungle(new Vector2d(10,5)));
        assertTrue(map2.isTileJungle(new Vector2d(100,100)));
        assertFalse(map1.isTileJungle(new Vector2d(0,0)));
        assertFalse(map2.isTileJungle(new Vector2d(0,0)));
    }


}