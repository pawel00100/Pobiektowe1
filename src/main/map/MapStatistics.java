package main.map;

import java.io.FileWriter;
import java.io.IOException;

public class MapStatistics {
    private int numberOfAnimals = 0;
    private int numberOfPlants = 0;
    private int epoch = 0;
    private int totalEnergy = 0;
    private int totalLifespanAtDeath = 0;
    private int deadAnimals = 0;
    private int numberOfChildrenOfAliveAnimals = 0;
    private StringBuilder history = new StringBuilder();

    MapStatistics(){

    }

    public int getNumberOfAnimals(){
        return this.numberOfAnimals;
    }

    public int getNumberOfPlants(){
        return this.numberOfPlants;
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

    private String generateString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Epoch: " + this.epoch);
        builder.append("    Number of Animals: " + this.numberOfAnimals);
        builder.append("    Number of Plants: " + this.numberOfPlants);
        builder.append("    Average energy: " + this.epoch);
        builder.append("    Average Lifespan: " + this.epoch);
        builder.append("    Average Children: " + this.epoch);
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
