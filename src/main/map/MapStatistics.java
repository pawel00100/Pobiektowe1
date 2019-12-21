package main.map;

import main.mapElements.Genome;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MapStatistics {
    private int numberOfAnimals = 0;
    private int numberOfPlants = 0;
    private int numberOfPlantsOnJungle = 0;
    private int epoch = 0;
    private int totalEnergy = 0;
    private int totalLifespanAtDeath = 0;
    private int deadAnimals = 0;
    private int numberOfChildrenOfAliveAnimals = 0;

    private Map<Genome, Integer> numberOfEachGenome = new TreeMap<>();

    private StringBuilder history = new StringBuilder();

    MapStatistics(){

    }

    public int getNumberOfAnimals(){
        return this.numberOfAnimals;
    }

    public int getNumberOfPlants(){
        return this.numberOfPlants;
    }

    public int getNumberOfPlantsOnJungle(){
        return this.numberOfPlantsOnJungle;
    }

    public int getEpoch(){
        return this.epoch;
    }

    public int getAverageEnergy(){
        return this.totalEnergy / this.numberOfAnimals;
    }

    public int getAverageLifespan(){
        if(this.deadAnimals == 0)
            return 0;
        return this.totalLifespanAtDeath / this.deadAnimals;
    }

    public double getAverageNumberOfChildren(){
        return (double) this.numberOfChildrenOfAliveAnimals / this.numberOfAnimals;
    }

    public void updateEpoch(){
        this.epoch++;
        this.history.append(generateString());
//        System.out.println(this.history);
    }

    public void appendNumberOfAnimals(int number){
        this.numberOfAnimals += number;
    }

    public void appendNumberOfPlants(int number){
        this.numberOfPlants += number;
    }

    public void appendNumberOfPlantsOnJungle(int number){
        this.numberOfPlantsOnJungle += number;
    }

    public void appendTotalLifespanAtDeath(int number){
        this.totalLifespanAtDeath += number;
    }

    public void appendDeadAnimals(int number){
        this.deadAnimals += number;
    }

    public void appendTotalEnergy(int number){
        this.totalEnergy += number;
    }

    public void appendNumberOfChildrenOfAliveAnimals(int number){
        this.numberOfChildrenOfAliveAnimals += number;
    }

    public void addGenome(Genome genome){

        if(!this.numberOfEachGenome.containsKey(genome))
            this.numberOfEachGenome.put(genome, 1);
        else {
            int oldNumber = this.numberOfEachGenome.get(genome);
            this.numberOfEachGenome.put(genome, oldNumber+1);
        }
    }

    public void removeGenome(Genome genome){
        if(!this.numberOfEachGenome.containsKey(genome))
            throw new IllegalArgumentException("No genome in map");

        int oldNumber = this.numberOfEachGenome.get(genome);
        if(oldNumber == 1)
            this.numberOfEachGenome.remove(genome);
        else
            this.numberOfEachGenome.put(genome, oldNumber-1);

    }

    public Genome mostFrequentGenome(){
        Genome mostFrequentGenome = null;
        int highestAmmount = 0;
        for (Genome genome : this.numberOfEachGenome.keySet()) {
            int ammount = this.numberOfEachGenome.get(genome);
            if(ammount > highestAmmount){
                highestAmmount = ammount;
                mostFrequentGenome = genome;
            }
        }
        return mostFrequentGenome;
    }

    public boolean isMostFrequentGenome(Genome genome){
        return genome.equals(mostFrequentGenome());
    }

    private String generateString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Epoch: " + this.epoch);
        builder.append("    Number of Animals: " + this.numberOfAnimals);
        builder.append("    Number of Plants: " + this.numberOfPlants);
        builder.append("    Average energy: " + this.epoch);
        builder.append("    Average Lifespan: " + getAverageLifespan());
        builder.append("    Average Children: " + getAverageNumberOfChildren());
        builder.append("    Most frequent Gene: " + this.mostFrequentGenome());
        builder.append("\n");
        return builder.toString();
    }

    public void writeFile(){
        try {
            FileWriter writer = new FileWriter("MyFile.txt", false);
            writer.write(this.history.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
