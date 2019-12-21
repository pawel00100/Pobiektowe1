package test;

import main.map.RectangularMap;
import main.map.Tile;
import main.mapElements.Animal;
import main.mapElements.Grass;
import main.mapElements.Vector2d;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {
    private RectangularMap map;
    private Tile tile;
    private Animal animal1, animal1a, animal2, animal3;
    private Grass grass1;
    @Before
    public void setUp() throws Exception {
        this.map = new RectangularMap(10,10);
        this.tile = new Tile(this.map, new Vector2d(2,2));
        this.animal1 = new Animal(this.map);
        this.animal1a = new Animal(this.map);
        this.animal2 = new Animal(this.map, 3, 3);
        this.animal3 = new Animal(this.map, 4, 4);
        this.grass1 = new Grass(this.map, new Vector2d(2,2));
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void putOnTile() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(new Animal(this.map));
        assertFalse(this.tile.isEmpty());
    }

    @Test
    public void removeFromTile() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(this.animal1);
        assertFalse(this.tile.isEmpty());
        this.tile.removeFromTile(this.animal1);
        assertTrue(this.tile.isEmpty());

        this.tile.putOnTile(this.grass1);
        assertFalse(this.tile.isEmpty());
        this.tile.removeFromTile(this.grass1);
        assertTrue(this.tile.isEmpty());
    }

    @Test
    public void isEmpty() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(new Animal(this.map));
        assertFalse(this.tile.isEmpty());
    }

    @Test
    public void isElementOnTile() {
        this.tile.putOnTile(this.animal1);
        assertTrue(this.tile.isElementOnTile(this.animal1));
        assertFalse(this.tile.isElementOnTile(this.animal2));
    }

    @Test
    public void isGrassOnTile() {
        assertFalse(this.tile.isGrassOnTile());
        this.tile.putOnTile(this.grass1);
        assertTrue(this.tile.isGrassOnTile());
    }

    @Test
    public void isAnimalOnTile() {
        assertFalse(this.tile.isAnimalOnTile());
        this.tile.putOnTile(this.animal1);
        assertTrue(this.tile.isAnimalOnTile());
    }

    @Test
    public void getElementsByEnergy(){
        this.tile.putOnTile(this.grass1);
        this.tile.putOnTile(this.animal1);
        this.tile.putOnTile(this.animal2);
        this.tile.putOnTile(this.animal3);
        this.animal1.setEnergy(20);
        this.animal2.setEnergy(50);
        this.animal3.setEnergy(30);
        assertEquals(this.animal2, this.tile.getElementsByEnergy().get(0));
        assertEquals(this.animal3, this.tile.getElementsByEnergy().get(1));
    }


}