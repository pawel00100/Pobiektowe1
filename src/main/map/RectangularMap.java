package main.map;

/*TODO:
*  add checking free space in Jungle*/

import main.mapElements.*;
import org.json.JSONObject;

import java.util.*;

public class RectangularMap implements IPositionChangeObserver
{
    public final Vector2d upperBoundary;
    public final Vector2d lowerBoundary = new Vector2d(0, 0);
    public Animal chosenAnimal = null;
    public boolean isChosenAnimalAlive = false; //not sure if needed
    public int epochOfDeath = 0;

    private JSONObject parameters;

    private List<Animal> animalList = new ArrayList<>(); //separate list of animals allows to skip iterating over grass tiles
    private Map<Vector2d, Tile> tilesOnMap = new LinkedHashMap<>();
    private int freeTiles;  //i have no idea what im doing

    public MapStatistics mapStatistics = new MapStatistics();

    private Vector2d lowerJungleBoundary;
    private Vector2d upperJungleBoundary;

    private List<IMapStateChangeObserver> observers = new LinkedList<>();

    public RectangularMap(int x, int y, int initialAnimals, double jungleRatio) {
        this.upperBoundary = new Vector2d(x, y);

        calculateMapParameters(jungleRatio);

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



//    public int getEpochOfDeath(){
//        return this.epochOfDeath;
//    }

    public void place(AbstractMapElement element) { //adds element on map, assumes it can be placed
        placeOnForcedPosition(element, element.getPosition());
    }

    public boolean isTileOccupied(Vector2d position) {
        return tilesOnMap.containsKey(position);
    }

    public Tile getTile(Vector2d position) {
        if (!isTileOccupied(position))
            throw new IllegalArgumentException("Accesing nonexistient tile at: " + position.toString());
        else
            return this.tilesOnMap.get(position);
    }

    public void elementPositionToBeChangedTo(AbstractMapElement element, Vector2d futurePosition) { //changes position of an element, asssumes calling method will change inner state
        Vector2d currentPosition = element.getPosition();
        if (futurePosition != null && !currentPosition.equals(futurePosition)) {
            this.removeOnlyFromHashmap(element, element.getPosition());
            this.placeOnlyOnHashmap(element, futurePosition);
        }
    }

    public void remove(AbstractMapElement element) {
        this.removeFromForcedPosition(element, element.getPosition());
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

    public boolean isTileJungle(Vector2d position) {
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
        Map<Vector2d, Tile> newMap = new LinkedHashMap<>(this.tilesOnMap); //copy, because map may be modified
        newMap.values().forEach(Tile::eatAndReproduce);
    }

    public void placePlants() {
        if (freeTiles > 0) {
            new Grass(this, generateRandomUnoccupiedPosition(this.lowerJungleBoundary, this.upperJungleBoundary));
            new Grass(this,generateRandomPositionWithException(this.lowerBoundary, this.upperBoundary, this.lowerJungleBoundary, this.upperJungleBoundary));
        }
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

    private void calculateMapParameters(double jungleRatio){
        this.upperJungleBoundary = calculateJungleBoundaries(jungleRatio, true);
        this.lowerJungleBoundary = calculateJungleBoundaries(jungleRatio, false);

        this.freeTiles = (this.upperBoundary.x - this.lowerBoundary.x + 1) * (this.upperBoundary.y - this.lowerBoundary.y + 1);
    }

    private Vector2d calculateJungleBoundaries(double jungleRatio, boolean calculateUpper) { //by default calculates lower
        int lowerBoundary = calulateJungleBoundary(jungleRatio, this.upperBoundary.x, this.lowerBoundary.x, calculateUpper);
        int upperBoundary = calulateJungleBoundary(jungleRatio, this.upperBoundary.y, this.lowerBoundary.y, calculateUpper);

        return new Vector2d(lowerBoundary, upperBoundary);
    }

    private int calulateJungleBoundary(double jungleRatio, int upperBoundary, int lowerBoundary, boolean calculateUpper){
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
        if (element instanceof Animal) {
            ((Animal) element).addObserver(this);
            this.animalList.add((Animal) element);
            this.mapStatistics.appendNumberOfAnimals(1);
        }
        if (element instanceof Grass)
            this.mapStatistics.appendNumberOfPlants(1);
    }

    private void placeOnlyOnHashmap(AbstractMapElement element, Vector2d position) {
        putOnTile(element, position);
        if (element instanceof Animal) {
            ((Animal) element).addObserver(this);
        }
    }

    private void removeFromForcedPosition(AbstractMapElement element, Vector2d position) { //removes from given position even if it contradicts instance's position
        if (!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to remove nonexistent element " + element);
        if (element instanceof Animal) {
            Animal animal = ((Animal) element);
            animal.removeObserver(this);
            this.animalList.remove(animal);
            this.mapStatistics.appendNumberOfAnimals(-1);
            if(this.chosenAnimal == animal){
                this.isChosenAnimalAlive = false;
                this.epochOfDeath = this.mapStatistics.getEpoch();
            }
            this.mapStatistics.appendTotalLifespanAtDeath( (animal).getLifespan() );
            this.mapStatistics.appendDeadAnimals(1);
            animal.onDeath();
        }
        if (element instanceof Grass)
            this.mapStatistics.appendNumberOfPlants(-1);
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

    public void animalEnergyChanged(int energyChange) {
        this.mapStatistics.appendTotalEnergy(energyChange);
    }

    public void animalNumberOfChildrenChanged(int numberOfChildrenChange) {
        this.mapStatistics.appendNumberOfChildrenOfAliveAnimals(numberOfChildrenChange);
    }
}