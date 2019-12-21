package main.mapElements;

/*TODO:
*  split method returning 2-element array
*  fight repetitions */

import java.util.ArrayList;
import java.util.List;

public class Genome implements Comparable {
    public final List<Integer> genes;
    public final long genesAsNumber;

    public static void main(String[] args){
        System.out.println("aa");
        Genome aa = new Genome();
        Genome bb = new Genome();
        Genome cc = new Genome(aa, bb);
        System.out.println(aa);
        System.out.println(bb);
        System.out.println(cc);
    }

    public Genome(){ //generates random genome
        ArrayList<Integer> genes = new ArrayList<>() ;

        for (int i = 0; i < 8; i++)
            genes.add(i);
        for (int i = 0; i < 24; i++)
            genes.add(randomGene());

        genes.sort(Integer::compareTo);
        this.genes = genes;
        this.genesAsNumber = convertToNumber();
    }

    public Genome(List<Integer> genes){
        this.genes = genes;
        addMissingGenes(this.genes);
        this.genesAsNumber = convertToNumber();

    }

    public Genome(Genome parent1, Genome parent2) {
        int[] splitIndicies = generateSplitIndices();

        int splitIndex1 = splitIndicies[0];
        int splitIndex2 = splitIndicies[1];

        ArrayList<Integer> genome = new ArrayList<>();

        Genome singleDonor;
        Genome doubleDonor;
        if(isFirstSingleDonor()) {
            singleDonor = parent1;
            doubleDonor = parent2;
        }
        else {
            singleDonor = parent2;
            doubleDonor = parent1;
        }

        int singleDonorFragmentNum = generateDonorFragmentNum();
        genome.addAll(donorFragment(singleDonor.genes, singleDonorFragmentNum, splitIndex1, splitIndex2));
        genome.addAll(nonDonorFragment(doubleDonor.genes, singleDonorFragmentNum, splitIndex1, splitIndex2));

        genome.sort(Integer::compareTo);
        addMissingGenes(genome);
        this.genes = genome;
        this.genesAsNumber = convertToNumber();

    }

    @Override
    public String toString(){
        String string =  this.genes.get(0).toString();
        for (int i = 1; i < 32; i++) {
            string += this.genes.get(i).toString();
        }
    return string;
    }

    @Override
    public boolean equals(Object genome2){
        if(genome2 == null) //delete and chceck if works checking most frequent
            return false;
        if(genes.equals(genome2))
            return true;
        if(!(genome2 instanceof Genome))
            return false;
        return this.genes.equals(((Genome) genome2).genes);
    }

    public int getGene(int num){
        return this.genes.get(num);
    }

    private long convertToNumber(){
        long number = 0;
//        for (int i = 31; i>=0 ; i--){
//
//        }
        for (int i = 0; i < 32; i++) {
            number += Math.round(Math.pow(10, 2* (7 - this.genes.get(i))));
        }
        return number;
    }

    private int randomGene(){
        return (int) Math.floor(Math.random() * 8);
    }

    private int randomPos(){
        return (int) Math.floor(Math.random() * 32);
    }

    private boolean isFirstSingleDonor(){
        return Math.random() < 0.5;
    }

    private int[] generateSplitIndices(){
        int splitIndex1 = (int) Math.floor(Math.random() * 31) + 1;
        int splitIndex2;
        do {
            splitIndex2 = (int) Math.floor(Math.random() * 31) + 1;
        }
        while (splitIndex1 == splitIndex2);

        if (splitIndex1 > splitIndex2) {
            int x = splitIndex1;
            splitIndex1 = splitIndex2;
            splitIndex2 = x;
        }
        return new int[] {splitIndex1, splitIndex2};
    }

    private List<Integer> donorFragment(List<Integer> parent, int chosenFragment, int splitIndex1, int splitIndex2){
        if(chosenFragment == 0)
            return  parent.subList(0, splitIndex1);
        else if (chosenFragment == 1)
            return  parent.subList(splitIndex1, splitIndex2);
        else
            return  parent.subList(splitIndex2, 32);
    }

    private List<Integer> nonDonorFragment(List<Integer> parent, int nonChosenFragment, int splitIndex1, int splitIndex2){
       List<Integer> newList = new ArrayList<>();

       int chosenFragment1;
       int chosenFragment2;

        if(nonChosenFragment == 0){
            chosenFragment1 = 1;
            chosenFragment2 = 2;
        }
        else if (nonChosenFragment == 1){
            chosenFragment1 = 0;
            chosenFragment2 = 2;
        }
        else {
            chosenFragment1 = 0;
            chosenFragment2 = 1;
        }

        newList.addAll(donorFragment(parent, chosenFragment1, splitIndex1, splitIndex2));
        newList.addAll(donorFragment(parent, chosenFragment2, splitIndex1, splitIndex2));

        return newList;
    }

    private int generateDonorFragmentNum(){
        return  (int) Math.floor(Math.random() * 3);
    }

    private void addMissingGenes(List<Integer> genes){
        while(firstMissingGene(genes) != -1){
            genes.set(randomPos(), firstMissingGene(genes));
            genes.sort(Integer::compareTo);
        }
    }

    private int firstMissingGene(List<Integer> genes){
        if(genes.get(0) != 0)
            return 0;
        int firstPosition = 0;
        while(firstPosition < 31){
            if(genes.get(firstPosition+1) - genes.get(firstPosition) > 1)
                return genes.get(firstPosition) + 1;
            firstPosition++;
        }
        if (genes.get(31) != 7)
            return 7;
        return -1;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o)
            return 0;
        Genome comparedGenome = (Genome) o;
        return Long.compare(this.genesAsNumber, comparedGenome.genesAsNumber);
    }
}
