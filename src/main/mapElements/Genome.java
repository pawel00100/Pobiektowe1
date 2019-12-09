package main.mapElements;

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
//        genome = new int[] {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7};
        ArrayList<Integer> genome = new ArrayList<>() ;

        for (int i = 0; i < 8; i++)
            genome.add(i);
        for (int i = 0; i < 24; i++)
            genome.add(randomGene());

        genome.sort(Integer::compareTo);
        this.genes = genome;
    }

    public Genome(Genome parent1, Genome parent2) {
        int split1 = (int) Math.floor(Math.random() * 31) + 1;
        int split2;
        do {
            split2 = (int) Math.floor(Math.random() * 31) + 1;
        }
        while (split1 == split2);
        if (split1 > split2) {
            int x = split1;
            split1 = split2;
            split2 = x;
        }

        ArrayList<Integer> genome = new ArrayList<>() ;

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
        genome.addAll(donorFragment(singleDonor.genes, singleDonorFragmentNum, split1, split2));
        genome.addAll(nonDonorFragment(doubleDonor.genes, singleDonorFragmentNum, split1, split2));

        genome.sort(Integer::compareTo);
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


    private int randomGene(){
        return (int) Math.floor(Math.random() * 8);
    }



    private boolean isFirstSingleDonor(){
        return Math.random() < 0.5;
    }

    private List<Integer> donorFragment(List<Integer> parent, int num, int split1, int split2){
        if(num == 0)
            return  parent.subList(0, split1);
        else if (num == 1)
            return  parent.subList(split1, split2);
        else
            return  parent.subList(split2, 32);
    }

    private List<Integer> nonDonorFragment(List<Integer> parent, int num, int split1, int split2){
       List<Integer> newList = new ArrayList<>();
        if(num == 0){
            newList.addAll(donorFragment(parent, 1, split1, split2));
            newList.addAll(donorFragment(parent, 2, split1, split2));
        }
        else if (num == 1){
            newList.addAll(donorFragment(parent, 0, split1, split2));
            newList.addAll(donorFragment(parent, 2, split1, split2));
        }
        else{
            newList.addAll(donorFragment(parent, 0, split1, split2));
            newList.addAll(donorFragment(parent, 1, split1, split2));
        }

        return newList;
    }

    private int generateDonorFragmentNum(){
        return  (int) Math.floor(Math.random() * 3);
    }
}
