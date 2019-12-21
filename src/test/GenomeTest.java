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
        this.genome1 = new Genome(this.genes1);  //0000111122223333...   four each genes
        this.genome2  = new Genome(this.genes2);  //00000...001234567   has 25 zeros
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToString() {

        assertEquals("00001111222233334444555566667777" , genome1.toString());
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
        assertEquals(compare, this.genome1);
        assertNotEquals(compare, this.genome2);
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

        assertEquals(expectedGenome, genome2);
    }

    @Test
    public void convertToNumber(){
        assertEquals(404040404040404L, genome1.genesAsNumber);
        assertEquals(2501010101010101L, genome2.genesAsNumber);

    }

}