package main.map.statistics;

import main.mapElements.Genome;

import java.util.Map;
import java.util.TreeMap;

public class MapStatistics {
    private int numberOfAnimals = 0;
    private int numberOfPlants = 0;
    private int epoch = 0;
    private int totalEnergy = 0;
    private int totalLifespanAtDeath = 0;
    private int deadAnimals = 0;
    private int numberOfChildrenOfAliveAnimals = 0;

    private Map<Genome, Integer> numberOfEachGenome = new TreeMap<>();

    public MapStatistics() {

    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }

    public int getEpoch() {
        return epoch;
    }

    public int getAverageEnergy() {
        return totalEnergy / numberOfAnimals;
    }

    public int getAverageLifespan() {
        if (deadAnimals == 0)
            return 0;
        return totalLifespanAtDeath / deadAnimals;
    }

    public double getAverageNumberOfChildren() {
        return (double) numberOfChildrenOfAliveAnimals / numberOfAnimals;
    }

    public void updateEpoch() {
        epoch++;
    }

    public void appendNumberOfAnimals(int number) {
        numberOfAnimals += number;
    }

    public void appendNumberOfPlants(int number) {
        numberOfPlants += number;
    }

    public void appendTotalLifespanAtDeath(int number) {
        totalLifespanAtDeath += number;
    }

    public void appendDeadAnimals(int number) {
        deadAnimals += number;
    }

    public void appendTotalEnergy(int number) {
        totalEnergy += number;
    }

    public void appendNumberOfChildrenOfAliveAnimals(int number) {
        numberOfChildrenOfAliveAnimals += number;
    }

    public void addGenome(Genome genome) {

        if (!numberOfEachGenome.containsKey(genome))
            numberOfEachGenome.put(genome, 1);
        else {
            int oldNumber = numberOfEachGenome.get(genome);
            numberOfEachGenome.put(genome, oldNumber + 1);
        }
    }

    public void removeGenome(Genome genome) {

        if (!numberOfEachGenome.containsKey(genome))
            throw new IllegalArgumentException("No genome in map");

        int oldNumber = numberOfEachGenome.get(genome);
        if (oldNumber == 1)
            numberOfEachGenome.remove(genome);
        else
            numberOfEachGenome.put(genome, oldNumber - 1);

    }

    public Genome mostFrequentGenome() {
        Genome mostFrequentGenome = null;
        int highestAmmount = 0;

        for (Map.Entry<Genome, Integer> entry : this.numberOfEachGenome.entrySet()) {
            int amount = entry.getValue();
            if (amount > highestAmmount) {
                highestAmmount = amount;
                mostFrequentGenome = entry.getKey();
            }
        }
        return mostFrequentGenome;
    }

    public boolean isMostFrequentGenome(Genome genome) {
        return genome.equals(mostFrequentGenome());
    }

}
