package test;

import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Vector2d;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangularMapTest {
    private RectangularMap map, map2;
    private Animal animal1, animal2, animal3;

    @Before
    public void setUp() {
        this.map = new RectangularMap(4, 4);
        this.map2 = new RectangularMap(4, 4);
        this.animal1 = new Animal(map);
        this.animal2 = new Animal(map, 3, 3);
        this.animal3 = new Animal(map2, 5, 5);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void place() {

        assertTrue(map.isElementOnMap(animal1));
        assertTrue(map.isElementOnMap(animal2));
    }

    @Test
    public void remove() {
        map.remove(animal1);

        assertFalse(map.isElementOnMap(animal1));
    }

    @Test
    public void isTileOccupied() {
        new Animal(map, 3, 3);

        assertTrue(map.isTileOccupied(new Vector2d(2, 2)));
        assertTrue(map.isTileOccupied(new Vector2d(3, 3)));

        assertFalse(map.isTileOccupied(new Vector2d(3, 1)));
        assertFalse(map.isTileOccupied(new Vector2d(2, 0)));
    }

    @Test
    public void elementPositionToBeChangedTo() {
    }

    @Test
    public void isElementOnMap() {
        assertTrue(this.map.isElementOnMap(animal1));
        assertFalse(this.map.isElementOnMap(animal3));
    }

    @Test
    public void getAnimalByNumber() {
    }

    @Test
    public void isAnimalOnTile() {
    }

    @Test
    public void isGrassOnTile() {
    }

    @Test
    public void isTileJungle() {
    }

    @Test
    public void updateEnergies() {
    }

    @Test
    public void run() {
    }

    @Test
    public void eatAndReproduce() {
    }

    @Test
    public void placePlants() {
    }
}