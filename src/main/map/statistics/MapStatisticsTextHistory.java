package main.map.statistics;

import java.io.FileWriter;
import java.io.IOException;

public class MapStatisticsTextHistory {

    private final StringBuilder history = new StringBuilder();

    public void updateEpoch(MapStatistics statistics) {
        history.append(generateString(statistics));
    }

    private String generateString(MapStatistics statistics) {
        return "Epoch: " + statistics.getEpoch() +
                "    Number of Animals: " + statistics.getNumberOfAnimals() +
                "    Number of Plants: " + statistics.getNumberOfPlants() +
                "    Average energy: " + statistics.getAverageEnergy() +
                "    Average Lifespan: " + statistics.getAverageLifespan() +
                "    Average Children: " + statistics.getAverageNumberOfChildren() +
                "    Most frequent Gene: " + statistics.mostFrequentGenome() +
                "\n";
    }

    public void writeFile() {
        try (FileWriter writer = new FileWriter("History.txt", false)) {
            writer.write(history.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
