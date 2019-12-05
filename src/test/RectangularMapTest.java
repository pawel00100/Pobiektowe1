package test;

import main.*;
import org.junit.Test;

import static main.MoveDirection.*;
import static org.junit.Assert.*;

public class RectangularMapTest {

    @Test
    public void canMoveTo() {
        IWorldMap map = new RectangularMap(4, 4);
        new Animal(map);
        new Animal(map, 4, 2);

        //EMPTY SPOT CHECK:
        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(4, 4)));

        //BOUNDARY CHECK:
        assertFalse(map.canMoveTo(new Vector2d(2, 5)));
        assertFalse(map.canMoveTo(new Vector2d(-1, 2)));


        //EMPTY SPOT AFTER MOVE CHECK:
        MoveDirection[] directions = {FORWARD, BACKWARD};
        map.run(directions);
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(4, 2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canMoveToBlocked() {
        IWorldMap map = new RectangularMap(4, 4);
        map.place(new Animal(map));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
    }

    @Test
    public void place() {
        IWorldMap map = new RectangularMap(4, 4);

        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 1, 1);

        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));

        assertEquals(animal2, map.objectAt(new Vector2d(1, 1)));

        map = new RectangularMap(10, 20);
        Animal animal3 = new Animal(map, 7, 12);
        assertEquals(animal3, map.objectAt(new Vector2d(7, 12)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeOnBlocked() {
        IWorldMap map = new RectangularMap(4, 4);
        new Animal(map);
        new Animal(map);
    }

    @Test
    public void run() {
        IWorldMap map = new RectangularMap(4, 4);

        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 3, 3);
        Animal animal3 = new Animal(map, 1, 1);

        MoveDirection[] moves = {FORWARD, RIGHT, BACKWARD, FORWARD, FORWARD, FORWARD, BACKWARD};
        map.run(moves);
        map.run(new MoveDirection[]{});

        assertEquals(animal1, map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal2, map.objectAt(new Vector2d(4, 3)));
        assertEquals(animal3, map.objectAt(new Vector2d(1, 1)));
    }

    @Test
    public void isOccupied() {
        IWorldMap map = new RectangularMap(4, 4);
        new Animal(map);  //(2,2)
        new Animal(map, 3, 3);

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 3)));

        assertFalse(map.isOccupied(new Vector2d(3, 1)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }

    @Test
    public void objectAt() {
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 3, 3);

        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));
        assertEquals(animal2, map.objectAt(new Vector2d(3, 3)));

        assertNull(map.objectAt(new Vector2d(3, 1)));
        assertNull(map.objectAt(new Vector2d(2, 0)));
    }




}