package main.mapElements;

/*TODO:
*  split method returning 2-element array
*  fight repetitions */

import java.util.ArrayList;
import java.util.List;

public class Genome {
    public final List<Integer> genes;

    public static void main(String[] args){
        System.out.println("aa");
        Genome aa = new Genome();
        Genome bb = new Genome();
        Genome cc = new Genome(aa, bb);
        System.out.println(aa);
        System.out.println(bb);
        System.out.println(cc);
    }

    public Genome(){
        ArrayList<Integer> genes = new ArrayList<>() ;

        for (int i = 0; i < 8; i++)
            genes.add(i);
        for (int i = 0; i < 24; i++)
            genes.add(randomGene());

        genes.sort(Integer::compareTo);
        this.genes = genes;
    }

    public Genome(List<Integer> genes){
        this.genes = genes;
        addMissingGenes(this.genes);
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
    }

    @Override
    public String toString(){
        String string =  this.genes.get(0).toString();
        for (int i = 1; i < 32; i++) {
            string = string + ", " + this.genes.get(i).toString();
        }
    return string;
    }

    public int getGene(int num){
        return this.genes.get(num);
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
        if(nonChosenFragment == 0){
            newList.addAll(donorFragment(parent, 1, splitIndex1, splitIndex2));
            newList.addAll(donorFragment(parent, 2, splitIndex1, splitIndex2));
        }
        else if (nonChosenFragment == 1){
            newList.addAll(donorFragment(parent, 0, splitIndex1, splitIndex2));
            newList.addAll(donorFragment(parent, 2, splitIndex1, splitIndex2));
        }
        else{
            newList.addAll(donorFragment(parent, 0, splitIndex1, splitIndex2));
            newList.addAll(donorFragment(parent, 1, splitIndex1, splitIndex2));
        }

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
    public boolean equals(Object genome2){
        if(genes.equals(genome2))
            return true;
        if(!(genome2 instanceof Genome))
            return false;
        return this.genes.equals(((Genome) genome2).genes);
    }

}