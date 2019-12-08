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
    RectangularMap map;
    Tile tile;
    Animal animal1, animal1a, animal2, animal3;
    Grass grass1;
    @Before
    public void setUp() throws Exception {
        map = new RectangularMap(10,10);
        tile = new Tile();
        animal1 = new Animal(map);
        animal1a = new Animal(map);
        animal2 = new Animal(map, 3, 3);
        animal3 = new Animal(map, 4, 4);
        grass1 = new Grass(new Vector2d(5,5));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void eatAndReproduce() {
        this.tile.putOnTile(grass1);
        this.tile.putOnTile(animal1);
        this.tile.putOnTile(animal1a);
        animal1.setEnergy(10);
        animal1a.setEnergy(20);

        assertTrue(this.tile.isGrassOnTile());
        this.tile.eatAndReproduce();
        assertFalse(this.tile.isGrassOnTile());
        assertTrue(animal1.getEnergy() == 10);
        assertTrue(animal1a.getEnergy() > 20);
    }

    @Test
    public void putOnTile() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(new Animal(map));
        assertFalse(this.tile.isEmpty());
    }

    @Test
    public void removeFromTile() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(animal1);
        assertFalse(this.tile.isEmpty());
        this.tile.removeFromTile(animal1);
        assertTrue(this.tile.isEmpty());
    }

    @Test
    public void isEmpty() {
        assertTrue(this.tile.isEmpty());
        this.tile.putOnTile(new Animal(map));
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
        this.tile.putOnTile(grass1);
        this.tile.putOnTile(animal1);
        this.tile.putOnTile(animal2);
        this.tile.putOnTile(animal3);
        animal1.setEnergy(20);
        animal2.setEnergy(50);
        animal3.setEnergy(30);
        assertEquals(animal2, this.tile.getElementsByEnergy().get(0));
        assertEquals(animal3, this.tile.getElementsByEnergy().get(1));
    }


}