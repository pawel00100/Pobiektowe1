package main.map;

/*TODO:
*  add checking free space in Jungle*/

import main.mapElements.*;
import org.json.JSONObject;

import java.util.*;

public class RectangularMap implements IWorldMap, IPositionChangeObserver {
    private JSONObject parameters;

    private List<Animal> animalList = new ArrayList<>();
    private Map<Vector2d, Tile> tilesOnMap = new LinkedHashMap<>(); //map contains lists with elements on each tile
    private int freeTiles;  //i have no idea what im doing
    public int numberOfAnimals = 0;
    public int numberOfPlants = 0;

    public final Vector2d upperBoundary;
    public final Vector2d lowerBoundary = new Vector2d(0, 0);
    public final Vector2d lowerJungleBoundary;
    public final Vector2d upperJungleBoundary;

    private List<IMapStateChangeObserver> observers = new LinkedList<>();

    public RectangularMap(int x, int y, int initialAnimals, double jungleRatio) {
        this.upperBoundary = new Vector2d(x, y);

        int uxupper = (this.upperBoundary.x - this.lowerBoundary.x) / 2 + (int) Math.ceil(Math.sqrt(jungleRatio) * (this.upperBoundary.x - this.lowerBoundary.x) / 2);
        int uxlower = (this.upperBoundary.x - this.lowerBoundary.x) / 2 - (int) Math.ceil(Math.sqrt(jungleRatio) * (this.upperBoundary.x - this.lowerBoundary.x) / 2);

        int uyupper = (this.upperBoundary.y - this.lowerBoundary.y) / 2 + (int) Math.ceil(Math.sqrt(jungleRatio) * (this.upperBoundary.y - this.lowerBoundary.y) / 2);
        int uylower = (this.upperBoundary.y - this.lowerBoundary.y) / 2 - (int) Math.ceil(Math.sqrt(jungleRatio) * (this.upperBoundary.y - this.lowerBoundary.y) / 2);

        this.upperJungleBoundary = new Vector2d(uxupper, uyupper);
        this.lowerJungleBoundary = new Vector2d(uxlower, uylower);

        this.freeTiles = (this.upperBoundary.x - this.lowerBoundary.x + 1) * (this.upperBoundary.y - this.lowerBoundary.y + 1);


        for (int i = 0; i < initialAnimals && freeTiles > 0; i++)
            new Animal(this, generateRandomUnoccupiedPosition(this.lowerBoundary, this.upperBoundary));
    }

    public RectangularMap(int x, int y) {
        this(x, y, 0, 0);
    }

    public RectangularMap(JSONObject parameters) {
        this(parameters.getInt("width") - 1, parameters.getInt("height") - 1, parameters.getInt("initialAnimals"), parameters.getDouble("jungleRatio"));
        this.parameters = parameters;
    }

    @Override
    public Vector2d lowerBoundary() {
        return this.lowerBoundary;
    }

    @Override
    public Vector2d upperBoundary() {
        return this.upperBoundary;
    }

    @Override
    public void place(AbstractMapElement element) { //adds element on map, assumes it can be placed
        placeOnForcedPosition(element, element.getPosition());
    }

    @Override
    public boolean isTileOccupied(Vector2d position) {
        return tilesOnMap.containsKey(position);
    }

    @Override
    public void elementPositionToBeChangedTo(AbstractMapElement element, Vector2d futurePosition) { //changes position of an element, asssumes calling method will change inner state
        Vector2d currentPosition = element.getPosition();
        if (futurePosition != null && !currentPosition.equals(futurePosition)) {
            this.removeOnlyFromHashmap(element, element.getPosition());
            this.placeOnlyOnHashmap(element, futurePosition);
        }
    }

    private void placeOnForcedPosition(AbstractMapElement element, Vector2d position) { //places on given position even if it contradicts instance's position
        putOnTile(element, position);
        if (element instanceof Animal) {
            ((Animal) element).addObserver(this);
            this.animalList.add((Animal) element);
            this.numberOfAnimals++;
        }
        if (element instanceof Grass)
            this.numberOfPlants++;
    }

    private void placeOnlyOnHashmap(AbstractMapElement element, Vector2d position) {
        putOnTile(element, position);
        if (element instanceof Animal) {
            ((Animal) element).addObserver(this);
        }
    }

    public void remove(AbstractMapElement element) {
        this.removeFromForcedPosition(element, element.getPosition());
    }

    private void removeFromForcedPosition(AbstractMapElement element, Vector2d position) { //removes from given position even if it contradicts instance's position
        if (!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to remove nonexistent element " + element);
        if (element instanceof Animal) {
            ((Animal) element).removeObserver(this);
            this.animalList.remove((Animal) element);
            this.numberOfAnimals--;
        }
        if (element instanceof Grass)
            this.numberOfPlants--;
        removeFromTile(element, position);
    }

    private void removeOnlyFromHashmap(AbstractMapElement element, Vector2d position) {//doesn't affect animalList
        if (!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to remove nonexistent element " + element);
        removeFromTile(element, position);
        if (element instanceof Animal) {
            ((Animal) element).removeObserver(this);
        }
    }

    public boolean isElementOnMap(AbstractMapElement element) {
        Vector2d position = element.getPosition();
        if (tilesOnMap.containsKey(position))
            return tilesOnMap.get(position).isElementOnTile(element);

        return false;
    }

    public Animal getAnimalByNumber(int num){
        return this.animalList.get(num);
    }

    public boolean isAnimalOnTile(Vector2d position) {
        if (isTileOccupied(position))
            return tilesOnMap.get(position).isAnimalOnTile();

        return false;
    }

    public boolean isGrassOnTile(Vector2d position) {
        if (isTileOccupied(position))
            return tilesOnMap.get(position).isGrassOnTile();

        return false;
    }

    public boolean isJungle(Vector2d position) {
        return position.follows(this.lowerJungleBoundary) && position.precedes(this.upperJungleBoundary);
    }

    public void updateEnergies() {
        List<Animal> newList = new ArrayList<>(animalList);
        for (Animal animal : newList)
            if (animal.getEnergy() <= 0)
                remove(animal);
    }

    public void run() {
        animalList.forEach(Animal::move);
    }

    public void eatAndReproduce() {
        Map<Vector2d, Tile> newMap = new LinkedHashMap<>(this.tilesOnMap); //copy, because Map may be modified
        newMap.values().forEach(Tile::eatAndReproduce);
    }

    public void placePlants() {
        if (freeTiles > 0) {
            new Grass(this, generateRandomUnoccupiedPosition(this.lowerJungleBoundary, this.upperJungleBoundary));
            new Grass(this,generateRandomPositionWithException(this.lowerBoundary, this.upperBoundary, this.lowerJungleBoundary, this.upperJungleBoundary));
        }
    }

    private void putOnTile(AbstractMapElement element, Vector2d position) { //adds element on tile
        if (!tilesOnMap.containsKey(position)) {
            this.tilesOnMap.put(position, new Tile(this, position));
            this.freeTiles--;
        }
        this.tilesOnMap.get(position).putOnTile(element);
    }

    private void removeFromTile(AbstractMapElement element, Vector2d position) {
        this.tilesOnMap.get(position).removeFromTile(element);

        if (this.tilesOnMap.get(position).isEmpty()) {
            this.tilesOnMap.remove(position);
            this.freeTiles++;
        }
    }

    private Vector2d generateRandomUnoccupiedPosition(Vector2d lowerBoundary, Vector2d upperBoundary) {
        if (this.freeTiles == 0)
            return null;
        Vector2d vec;
        do {
            int xOffset = (int) ((upperBoundary.x - lowerBoundary.x + 1) * Math.random());
            int yOffset = (int) ((upperBoundary.y - lowerBoundary.y + 1) * Math.random());
            vec = new Vector2d(xOffset + lowerBoundary.x, yOffset + lowerBoundary.y);
        }
        while (isTileOccupied(vec));
        return vec;
    }

    private Vector2d generateRandomPositionWithException(Vector2d lowerBoundary, Vector2d upperBoundary, Vector2d exceptionLowerBoundary, Vector2d exceptionUpperBondary){
        Vector2d vec;
        do{
            vec = generateRandomUnoccupiedPosition(lowerBoundary, upperBoundary);
        }
        while(vec.follows(exceptionLowerBoundary) && vec.precedes(exceptionUpperBondary));
        return vec;
    }

    public void addObserver(IMapStateChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IMapStateChangeObserver observer) {
        this.observers.remove(observer);
    }

    public void mapStateChanged() {
        for (IMapStateChangeObserver observer : this.observers) {
            observer.mapStateChanged();
        }
    }
}