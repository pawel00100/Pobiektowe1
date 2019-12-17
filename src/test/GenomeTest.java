package test;

import main.mapElements.Genome;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GenomeTest {
    Genome genome1;
    Genome genome2;
    List<Integer> genes1;
    List<Integer> genes2;

    @Before
    public void setUp() throws Exception {
        this.genes1 = new ArrayList<>();
        this.genes2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                this.genes1.add(i);
            }
        }
        for (int i = 0; i < 32; i++) {
            this.genes2.add(0);
        }
        this.genome1 = new Genome(this.genes1);
        this.genome2  = new Genome(this.genes2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToString() {

        assertEquals("0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7" , genome1.toString());
    }

    @Test
    public void equals(){
        List<Integer> compareGenes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                compareGenes.add(i);
            }
        }
        Genome compare = new Genome(compareGenes);
        assertTrue(compare.equals(this.genome1));
        assertFalse(compare.equals(this.genome2));
    }

    @Test
    public void getGene() {
        assertEquals(0, genome1.getGene(0));
        assertEquals(0, genome1.getGene(3));
        assertEquals(1, genome1.getGene(4));
        assertEquals(7, genome1.getGene(31));
    }


    @Test
    public void addMissingGenes(){
        List<Integer> expectedGenes = new ArrayList<>();
        for (int i = 0; i < 32 - 7; i++) {
            expectedGenes.add(0);
        }
        expectedGenes.addAll(Arrays.asList(1,2,3,4,5,6,7));
        Genome expectedGenome = new Genome(expectedGenes);

        Genome newGenome = new Genome(this.genes2);

        assertEquals(expectedGenome, genome2);
    }

}