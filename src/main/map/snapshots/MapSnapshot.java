package main.map.snapshots;

import main.UI.UIState;
import main.map.RectangularMap;
import main.map.statistics.MapInformation;
import main.map.statistics.MapStatistics;
import main.mapElements.AbstractMapElement;
import main.mapElements.Animal;
import main.mapElements.Genome;
import main.mapElements.Vector2d;

import java.util.*;

public class MapSnapshot {
    public final MapStatistics mapStatistics;
    private final AnimalSnapshot chosenAnimal;
    private final List<Genome> genomeList;
    private final int epochOfDeath;
    private final Map<Vector2d, TileInfo> tileInfoMap; //TODO consider replacing with 2d map
    private final Set<Vector2d> animalsWithMostFrequentGenome;

    public MapSnapshot(MapStatistics mapStatistics,
                       AnimalSnapshot chosenAnimal,
                       List<Genome> genomeList,
                       int epochOfDeath,
                       Map<Vector2d, TileInfo> tileInfoMap,
                       Set<Vector2d> animalsWithMostFrequentGenome) {
        this.mapStatistics = mapStatistics;
        this.chosenAnimal = chosenAnimal;
        this.genomeList = genomeList;
        this.epochOfDeath = epochOfDeath;
        this.tileInfoMap = tileInfoMap;
        this.animalsWithMostFrequentGenome = animalsWithMostFrequentGenome;
    }

    public AnimalSnapshot getChosenAnimal() {
        return chosenAnimal;
    }

    public List<Genome> getSortedGenomeList() {
        return genomeList;
    }

    public int getNumberOfAnimals() {
        return mapStatistics.getNumberOfAnimals();
    }

    public int getEpochOfDeath() {
        return epochOfDeath;
    }

    public MapStatistics getStatistics() {
        return mapStatistics;
    }

    public Map<Vector2d, TileInfo> getTileInfoMap() {
        return tileInfoMap;
    }

    public boolean hasMostFrequentGenome(Vector2d tilePosition) {
        return animalsWithMostFrequentGenome.contains(tilePosition);
    }

    public static boolean hasMostFrequentGenome(Vector2d tilePosition, Set<Vector2d> mostFrequentGenomePositions) {
        return mostFrequentGenomePositions.contains(tilePosition);
    }


    public static MapSnapshot fromMap(RectangularMap map, MapInformation mapInformation, UIState uiState) {
        AnimalSnapshot chosenAnimalSnapshot = getChosenAnimalSnapshot(uiState);

        List<Genome> genomes = getGenomes(map);

        Set<Vector2d> mostFrequentGenomePositions = getMostFrequentGenomePositions(map);

        Map<Vector2d, TileInfo> tileInfoMap = getTileInformation(map, mapInformation, uiState, mostFrequentGenomePositions);

        return new MapSnapshot(new MapStatistics(map.mapStatistics), chosenAnimalSnapshot, genomes, uiState.getEpochOfDeath(), tileInfoMap, mostFrequentGenomePositions);
    }

    private static Map<Vector2d, TileInfo> getTileInformation(RectangularMap map, MapInformation mapInformation, UIState uiState, Set<Vector2d> mostFrequentGenomePositions) {
        Map<Vector2d, TileInfo> tileInfoMap = new HashMap<>();
        for (int i = mapInformation.getLowerBoundary().x; i <= mapInformation.getUpperBoundary().x; i++) {
            for (int j = mapInformation.getLowerBoundary().y; j <= mapInformation.getUpperBoundary().y; j++) {
                var tilePosition = new Vector2d(i, j);
                TileInfo tileInfo;
                if (map.isTileOccupied(tilePosition)) {
                    if (uiState.getChosenAnimal() != null && uiState.isChosenAnimalAlive() && tilePosition.equals(uiState.getChosenAnimal().getPosition())) {
                        tileInfo = TileInfo.CHOSEN_ANIMAL_TILE;
                    } else if (map.isAnimalOnTile(tilePosition)) {
                        if (uiState.isShowMostFrequent() && hasMostFrequentGenome(tilePosition, mostFrequentGenomePositions)) {
                            tileInfo = TileInfo.MOST_FREQUENT_GENOME;
                        } else {
                            tileInfo = new TileInfo.AnimalOnTile(map.getTile(tilePosition).getStrongestAnimal().getEnergy());
                        }
                    } else if (map.isGrassOnTile(tilePosition)) {
                        tileInfo = TileInfo.GRASS_TILE;
                    } else {
                        throw new IllegalArgumentException("Tile not found");
                    }
                } else if (map.isTileJungle(tilePosition)) {
                    tileInfo = TileInfo.JUNGLE_TILE;
                } else {
                    tileInfo = TileInfo.GRAVEL_TILE;
                }
                tileInfoMap.put(tilePosition, tileInfo);
            }
        }
        return tileInfoMap;
    }

    private static Set<Vector2d> getMostFrequentGenomePositions(RectangularMap map) {
        Set<Vector2d> mostFrequentGenomePositions = new HashSet<>();
        map.animalList.stream()
                .map(AbstractMapElement::getPosition)
                .distinct()
                .filter(p -> map.mapStatistics.isMostFrequentGenome(map.getTile(p).getStrongestAnimal().getGenome()))
                .forEach(mostFrequentGenomePositions::add);
        return mostFrequentGenomePositions;
    }

    private static List<Genome> getGenomes(RectangularMap map) {
        List<Genome> genomes = new ArrayList<>();
        for (Animal animal : map.animalList) {
            genomes.add(animal.getGenome());
        }
        genomes.sort(Genome::compareTo);
        return genomes;
    }

    private static AnimalSnapshot getChosenAnimalSnapshot(UIState uiState) {
        var chosenAnimal = uiState.getChosenAnimal();
        AnimalSnapshot chosenAnimalSnapshot = null;
        if (chosenAnimal != null) { //TODO: replace with optional
            chosenAnimalSnapshot = new AnimalSnapshot(chosenAnimal.getEnergy(), chosenAnimal.getNumberOfChildrenSinceChoosing(), chosenAnimal.getNumberOfDescendants(), chosenAnimal.getGenome());
        }
        return chosenAnimalSnapshot;
    }

}
