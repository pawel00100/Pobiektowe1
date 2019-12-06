package test;

import main.map.IWorldMap;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.MapDirection;
import main.mapElements.MoveDirection;
import main.mapElements.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    @Test
    public void testToString() {
        IWorldMap map = new RectangularMap(4,4);
        Animal a = new Animal(map);
        assertEquals("N", a.toString());
    }


    @Test
    public void move() {
        IWorldMap map = new RectangularMap(4,4);
        Animal testedAnimal = new Animal(map);
        Vector2d vector;

        testedAnimal.move(MoveDirection.RIGHT);
        assertEquals(testedAnimal.getDirection(), MapDirection.EAST);

        testedAnimal.move(MoveDirection.FORWARD);
        vector = new Vector2d(3, 2);
        assertEquals(testedAnimal.getPosition(), vector);

        testedAnimal.move(MoveDirection.FORWARD);
        vector = new Vector2d(4, 2);
        assertEquals(testedAnimal.getPosition(), vector);

        testedAnimal.move(MoveDirection.FORWARD);   //Boundary on x=4
        vector = new Vector2d(4, 2);
        assertEquals(testedAnimal.getPosition(), vector);
    }

}