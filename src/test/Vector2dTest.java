package test;

import main.mapElements.Vector2d;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2dTest {

    @Test
    public void testToString() {
        Vector2d v = new Vector2d(1, 9);
        assertEquals("(1,9)", v.toString());
    }

    @Test
    public void precedes() {
        Vector2d a = new Vector2d(1, 9);
        Vector2d b = new Vector2d(-1, 3);
        Vector2d c = new Vector2d(5, 3);
        assertTrue(b.precedes(a));
        assertFalse(a.precedes(c));
        assertFalse(c.precedes(a));
        assertTrue(a.precedes(a));
    }

    @Test
    public void follows() {
        Vector2d a = new Vector2d(1, 9);
        Vector2d b = new Vector2d(-1, 3);
        Vector2d c = new Vector2d(5, 3);
        assertTrue(a.follows(b));
        assertFalse(a.follows(c));
        assertFalse(c.follows(a));
        assertTrue(a.follows(a));
    }

    @Test
    public void upperRight() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(7, -8);
        Vector2d c = new Vector2d(7, 6);
        assertEquals(a.upperRight(b), c);
    }

    @Test
    public void lowerLeft() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(7, -8);
        Vector2d c = new Vector2d(-5, -8);
        assertEquals(a.lowerLeft(b), c);
    }

    @Test
    public void add() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(7, -8);
        Vector2d c = new Vector2d(2, -2);
        Vector2d d = new Vector2d(-10, 12);
        assertEquals(a.add(b), c);
        assertEquals(a.add(a), d);
    }

    @Test
    public void subtract() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(7, -8);
        Vector2d c = new Vector2d(-12, 14);
        Vector2d d = new Vector2d(0, 0);
        assertEquals(a.subtract(b), c);
        assertEquals(a.subtract(a), d);
    }

    @Test
    public void testEquals() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(7, -8);
        Vector2d c = new Vector2d(-5, 6);
        assertNotEquals(a, b);
        assertEquals(a, c);
        assertEquals(a, a);
    }

    @Test
    public void opposite() {
        Vector2d a = new Vector2d(-5, 6);
        Vector2d b = new Vector2d(5, -6);
        assertEquals(a.opposite(), b);
        assertNotEquals(a.opposite(), a);
    }
}