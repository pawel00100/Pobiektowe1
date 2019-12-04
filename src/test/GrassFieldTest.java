package test;

import main.*;
import org.junit.Test;

import static main.MoveDirection.*;
import static org.junit.Assert.*;

public class GrassFieldTest {
    @Test
    public void canMoveTo() {
        IWorldMap map = new GrassField(0);
        new Animal(map);
        new Animal(map, -50, 100);

        //EMPTY SPOT CHECK:
        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(100, 200)));
        assertTrue(map.canMoveTo(new Vector2d(-20, -5)));

        //ANIMAL PRESENT ON MAP CHECK:
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(-50, 100)));

        //EMPTY SPOT AFTER MOVE CHECK:
        MoveDirection[] directions = {FORWARD, BACKWARD};
        map.run(directions);
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(-50, 100)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void canMoveToBlocked(){
        IWorldMap map = new GrassField(0);
        new Animal(map, 10, -15);
        new Animal(map, 10, -15);
    }

    @Test
    public void place() {
        IWorldMap map = new GrassField(0);

        Animal animal1 = new Animal(map,10,-15);
        Animal animal2 = new Animal(map, 1, 1);

        assertEquals(animal1, map.objectAt(new Vector2d(10,-15)));
        assertEquals(animal2, map.objectAt(new Vector2d(1, 1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeOnBlocked(){
        IWorldMap map = new GrassField(0);
        new Animal(map, 10, -15);
        new Animal(map, 10, -15);
    }

    @Test
    public void run() {
        IWorldMap map = new GrassField(0);

        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 3, 3);
        Animal animal3 = new Animal(map, 1, 1);

        MoveDirection[] moves = {FORWARD, RIGHT, BACKWARD, FORWARD, FORWARD, BACKWARD, BACKWARD};
        map.run(moves);
        map.run(new MoveDirection[] {});

        assertEquals(animal1, map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal2, map.objectAt(new Vector2d(4, 3)));
        assertEquals(animal3, map.objectAt(new Vector2d(1, -1)));
    }

    @Test
    public void isOccupied() {
        IWorldMap map = new GrassField(0);
        new Animal(map);  //(2,2)
        new Animal(map, 3, 3);

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 3)));

        assertFalse(map.isOccupied(new Vector2d(3, 1)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }

    @Test
    public void objectAt() {
        IWorldMap map = new GrassField(0);
        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 3, 3);

        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));
        assertEquals(animal2, map.objectAt(new Vector2d(3, 3)));

        assertNull(map.objectAt(new Vector2d(3, 1)));
        assertNull(map.objectAt(new Vector2d(2, 0)));
    }


    @Test
    public void parseAndRun() {
        IWorldMap map = new GrassField(0);

        Animal animal1 = new Animal(map); //(2,2)
        Animal animal2 = new Animal(map, 4, 4);
        Animal animal3 = new Animal(map, 1, 1);

        String[] args = {"f", "b", "r", "l", "r", "f"};

        map.run(OptionsParser.parse(args));

        assertEquals(animal1, map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal2, map.objectAt(new Vector2d(4, 3)));
        assertEquals(animal3, map.objectAt(new Vector2d(2, 1)));
    }

    @Test
    public void generatesGrass(){
        IWorldMap map = new GrassField(10);
        int grassStackNumber = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (map.objectAt(new Vector2d(i,j)) instanceof Grass)
                    grassStackNumber++;

        assertEquals(10,grassStackNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeOnGrass(){
        IWorldMap map = new GrassField(10);
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                if (map.objectAt(new Vector2d(i,j)) instanceof Grass)
                    new Animal(map, i,j);
    }

}