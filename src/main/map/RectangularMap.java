package main.map;

import main.UI.UIState;
import main.map.statistics.MapStatistics;
import main.mapElements.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class RectangularMap implements IPositionChangeObserver {
    private static final Random random = new Random();

    public final Vector2d upperBoundary;
    public final Vector2d lowerBoundary = new Vector2d(0, 0);

    public UIState uiStateTemp = null;
    private Consumer<Animal> onAnimalDeath;
    public JSONObject parameters;

    public List<Animal> animalList = new ArrayList<>(); //separate list of animals allows to skip iterating over grass tiles
    private Tiles tiles;
    private int freeTiles;
    private int freeTilesOnJungle;

    public final MapStatistics mapStatistics = new MapStatistics();

    private Vector2d lowerJungleBoundary;
    private Vector2d upperJungleBoundary;
    public final MapVectorCache vectorCache;

    public RectangularMap(int x, int y, int initialAnimals, double jungleRatio) {
        upperBoundary = new Vector2d(x, y);
        calculateMapParameters(jungleRatio);

        for (int i = 0; i < initialAnimals && this.freeTiles > 0; i++)
            new Animal(this, generateRandomUnoccupiedPosition(this.lowerBoundary, this.upperBoundary));
        vectorCache = new MapVectorCache(lowerBoundary, upperBoundary);
        tiles = new DenseTiles(this);
    }

    public RectangularMap(int x, int y) {
        this(x, y, 0, 0);
    }

    public RectangularMap(JSONObject parameters) {
        this(parameters.getInt("width") - 1, parameters.getInt("height") - 1, 0, parameters.getDouble("jungleRatio"));
        this.parameters = parameters;
    }

    public void setOnAnimalDeath(Consumer<Animal> onAnimalDeath) {
        this.onAnimalDeath = onAnimalDeath;
    }

    public void place(AbstractMapElement element) { //adds element on map, assumes it can be placed
        placeOnForcedPosition(element, element.getPosition());
    }

    public void remove(AbstractMapElement element) {
        removeFromForcedPosition(element, element.getPosition());
    }

    public boolean isTileOccupied(Vector2d position) {
        return tiles.containsKey(position);
    }

    public Tile getTile(Vector2d position) {
        if (!isTileOccupied(position))
            throw new IllegalArgumentException("Accesing nonexistient tile at: " + position.toString());
        else
            return tiles.get(position);
    }

    @Override
    public void elementPositionToBeChangedTo(AbstractMapElement element, Vector2d futurePosition) { //changes position of an element, asssumes calling method will change inner state
        Vector2d currentPosition = element.getPosition();
        if (futurePosition != null && !currentPosition.equals(futurePosition)) {
            removeOnlyFromHashmap(element, element.getPosition());
            placeOnlyOnHashmap(element, futurePosition);
        }
    }

    public boolean isElementOnMap(AbstractMapElement element) {
        Vector2d position = element.getPosition();
        if (tiles.containsKey(position))
            return tiles.get(position).isElementOnTile(element);

        return false;
    }

    public Animal getAnimalByNumber(int num) {
        return animalList.get(num);
    }

    public boolean isAnimalOnTile(Vector2d position) {
        if (isTileOccupied(position))
            return tiles.get(position).isAnimalOnTile();

        return false;
    }

    public boolean isGrassOnTile(Vector2d position) {
        if (isTileOccupied(position))
            return tiles.get(position).isGrassOnTile();

        return false;
    }

    public boolean isTileJungle(Vector2d position) {
        return position.follows(lowerJungleBoundary) && position.precedes(upperJungleBoundary);
    }

    public void updateEnergies() {
        List<Animal> toRemove = new ArrayList<>(6);
        for (Animal animal : animalList) {
            if (animal.getEnergy() <= 0) {
                toRemove.add(animal);
            }
        }
        for (Animal animal : toRemove) {
            remove(animal);
        }
    }

    public void run() {
        animalList.forEach(Animal::move);
    }

    public void eatAndReproduce() {
        var allTiles = tiles.getAll();

        int eatenGrass = 0; // loop instead of stream for performance
        if (allTiles instanceof List<Tile> list) {
            for (int i = 0; i < allTiles.size(); i++) {
                boolean e = list.get(i).eatAndReproduce();
                if (e) {
                    eatenGrass++;
                }
            }
        } else {
            for (Tile allTile : allTiles) {
                boolean e = allTile.eatAndReproduce();
                if (e) {
                    eatenGrass++;
                }
            }
        }


        mapStatistics.appendNumberOfPlants(-eatenGrass);
    }

    public void placePlants() {
        for (int i = 0; i < parameters.getInt("plantsPerTick"); i++) {
            if (freeTilesOnJungle > 0)
                new Grass(this, generateRandomUnoccupiedPosition(lowerJungleBoundary, upperJungleBoundary));
            if (freeTiles - freeTilesOnJungle > 0)
                new Grass(this, generateRandomPositionWithException(lowerBoundary, upperBoundary, lowerJungleBoundary, upperJungleBoundary));
        }
    }

    public void mapStateChanged() {
        uiStateTemp.redraw();

        try {
            Thread.sleep(200);
        } catch (Exception ignored) {
        }
    }

    private void calculateMapParameters(double jungleRatio) {
        upperJungleBoundary = calculateJungleBoundary(jungleRatio, true);
        lowerJungleBoundary = calculateJungleBoundary(jungleRatio, false);


        freeTiles = (upperBoundary.x - lowerBoundary.x + 1) * (upperBoundary.y - lowerBoundary.y + 1);
        freeTilesOnJungle = (upperJungleBoundary.x - lowerJungleBoundary.x + 1) * (upperJungleBoundary.y - lowerJungleBoundary.y + 1);
    }

    private Vector2d calculateJungleBoundary(double jungleRatio, boolean calculateUpper) { //by default calculates lower
        int lowerBoundary = calculateJungleBoundary(jungleRatio, this.upperBoundary.x, this.lowerBoundary.x, calculateUpper);
        int upperBoundary = calculateJungleBoundary(jungleRatio, this.upperBoundary.y, this.lowerBoundary.y, calculateUpper);

        return new Vector2d(lowerBoundary, upperBoundary);
    }

    private int calculateJungleBoundary(double jungleRatio, int upperBoundary, int lowerBoundary, boolean calculateUpper) {
        int boundaryCenter = (upperBoundary - lowerBoundary) / 2;
        int boundaryOffset = (int) Math.ceil(Math.sqrt(jungleRatio) * (upperBoundary - lowerBoundary) / 2);
        int boundary;
        if (calculateUpper)
            boundary = boundaryCenter + boundaryOffset;
        else
            boundary = boundaryCenter - boundaryOffset;

        return boundary;
    }

    private void placeOnForcedPosition(AbstractMapElement element, Vector2d position) { //places on given position even if it contradicts instance's position
        putOnTile(element, position);
        if (element instanceof Animal animal) {
            animal.addObserver(this);
            animalList.add(animal);
            mapStatistics.appendNumberOfAnimals(1);
        }
        if (element instanceof Grass)
            mapStatistics.appendNumberOfPlants(1);
    }

    private void placeOnlyOnHashmap(AbstractMapElement element, Vector2d position) {
        putOnTile(element, position);
        if (element instanceof Animal animal) {
            animal.addObserver(this);
        }
    }

    private void removeFromForcedPosition(AbstractMapElement element, Vector2d position) { //removes from given position even if it contradicts instance's position
        if (!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to remove nonexistent element " + element);
        if (element instanceof Animal animal) {
            animal.removeObserver(this);
            animalList.remove(animal);
            mapStatistics.appendNumberOfAnimals(-1);
            onAnimalDeath.accept(animal);
            mapStatistics.appendTotalLifespanAtDeath((animal).getLifespan());
            mapStatistics.appendDeadAnimals(1);
            animal.onDeath();
        }
        if (element instanceof Grass)
            mapStatistics.appendNumberOfPlants(-1);
        removeFromTile(element, position);
    }

    private void removeOnlyFromHashmap(AbstractMapElement element, Vector2d position) {//doesn't affect animalList
        if (!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to remove nonexistent element " + element);
        removeFromTile(element, position);
        if (element instanceof Animal animal) {
            animal.removeObserver(this);
        }
    }

    private void putOnTile(AbstractMapElement element, Vector2d position) { //adds element on tile
        if (!tiles.containsKey(position)) {
            tiles.createTileIfNecessary(position, this);
            this.freeTiles--;
            if (position.precedes(this.upperJungleBoundary) && position.follows(this.lowerJungleBoundary))
                this.freeTilesOnJungle--;
        }
        tiles.get(position).putOnTile(element);
    }

    private void removeFromTile(AbstractMapElement element, Vector2d position) {
        tiles.get(position).removeFromTile(element);

        if (tiles.get(position).isEmpty()) {
            tiles.removeTileIfNecessary(position);
            this.freeTiles++;
            if (position.precedes(this.upperJungleBoundary) && position.follows(this.lowerJungleBoundary))
                this.freeTilesOnJungle++;
        }
    }

    private Vector2d generateRandomUnoccupiedPosition(Vector2d lowerBoundary, Vector2d upperBoundary) {
        if (freeTiles == 0)
            return null;
        Vector2d vec;
        do {
            int x = random.nextInt(lowerBoundary.x, upperBoundary.x + 1);
            int y = random.nextInt(lowerBoundary.y, upperBoundary.y + 1);
            vec = new Vector2d(x, y);
        }
        while (isTileOccupied(vec));
        return vec;
    }

    private Vector2d generateRandomPositionWithException(Vector2d lowerBoundary, Vector2d upperBoundary, Vector2d exceptionLowerBoundary, Vector2d exceptionUpperBondary) {
        Vector2d vec;
        do {
            vec = generateRandomUnoccupiedPosition(lowerBoundary, upperBoundary);
        }
        while (vec.follows(exceptionLowerBoundary) && vec.precedes(exceptionUpperBondary));
        return vec;
    }

    public void animalEnergyChanged(int energyChange) {
        mapStatistics.appendTotalEnergy(energyChange);
    }

    public void animalNumberOfChildrenChanged(int numberOfChildrenChange) {
        mapStatistics.appendNumberOfChildrenOfAliveAnimals(numberOfChildrenChange);
    }
}