package main.map;


import main.mapElements.*;
import org.json.JSONObject;

import java.util.*;

public class RectangularMap implements IWorldMap, IPositionChangeObserver {
    private JSONObject parameters;

    private List<Animal> animalList = new ArrayList<>();
    private Map<Vector2d, List<AbstractMapElement>> tilesOnMap = new HashMap<>(); //map contains lists with elements on each tile
    private int freeTiles;

    public final Vector2d upperBoundary;
    public final Vector2d lowerBoundary = new Vector2d(0, 0);


    public RectangularMap(int x, int y, int initialGrass, int initialAnimals) {
        this.upperBoundary = new Vector2d(x, y);
        this.freeTiles = (this.upperBoundary.x - this.lowerBoundary.x + 1) * (this.upperBoundary.y - this.lowerBoundary.y + 1);

        for (int i = 0; i < initialGrass && freeTiles != 0; i++)
            place(new Grass(generateRandomUnoccupiedPosition()));

        for (int i = 0; i < initialAnimals && freeTiles != 0; i++)
            new Animal(this, generateRandomUnoccupiedPosition());
    }

    public RectangularMap(int x, int y) {
        this(x, y, 0, 0);
    }

    public RectangularMap(JSONObject parameters){
        this(parameters.getInt("width") - 1,parameters.getInt("height") - 1,parameters.getInt("initialGrass"), parameters.getInt("initialAnimals"));
        this.parameters = parameters;
    }
    @Override
    public String toString() {
        if (upperBoundary == null || lowerBoundary == null) return "no items";
        return new MapVisualizer(this).draw(this.lowerBoundary, this.upperBoundary);
    }

    @Override
    public void place(AbstractMapElement element) { //adds element on map, assumes it can be placed
        placeOnForcedPosition(element, element.getPosition());
    }

    public void placeOnForcedPosition(AbstractMapElement element, Vector2d position) { //places on given position even if it contradicts instance's position
        if(isElementOnMap(element))
            throw new IllegalArgumentException("Trying to put element that is already on chosen position");
        putOnTile(element, position);
        if( element instanceof Animal) {
            this.animalList.add((Animal) element);
            ((Animal) element).addObserver(this);
        }
    }

    public void remove(AbstractMapElement element){
        this.remove(element, element.getPosition());
    }

    public void remove(AbstractMapElement element, Vector2d position){ //removes from given position even if it contradicts instance's position
        if(!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to put element that is already on chosen position");
        removeFromTile(element, position);
        if( element instanceof Animal) {
            this.animalList.remove((Animal) element);
            ((Animal) element).removeObserver(this);
        }
    }

    @Override
    public boolean isTileOccupied(Vector2d position) {
        return tilesOnMap.containsKey(position);
    }



    public void elementPositionToBeChanged(AbstractMapElement element, Vector2d futurePosition) { //changes position of an element, asssumes calling method will change inner state
        Vector2d currentPosition = element.getPosition();
        if (futurePosition != null && !currentPosition.equals(futurePosition)) {
            this.remove(element);
            this.placeOnForcedPosition(element, futurePosition);
        }
    }

    public boolean isElementOnMap(AbstractMapElement element){
        Vector2d position = element.getPosition();
        if(tilesOnMap.containsKey(position))
            return tilesOnMap.get(position).contains(element);

        return false;
    }

    public boolean isAnimalOnTile(Vector2d position){
        if(isTileOccupied(position))
            for (AbstractMapElement element : tilesOnMap.get(position))
                if (element instanceof Animal)
                    return true;

        return false;
    }

    public boolean isGrassOnTile(Vector2d position){
        if(isTileOccupied(position))
            for (AbstractMapElement element : tilesOnMap.get(position))
                if (element instanceof Grass)
                    return true;

        return false;
    }

    private void putOnTile(AbstractMapElement element, Vector2d position){ //adds element on tile
        if(!tilesOnMap.containsKey(position)) {
            this.tilesOnMap.put(element.getPosition(), new LinkedList<AbstractMapElement>());
            this.freeTiles--;
        }
        this.tilesOnMap.get(position).add(element);
    }

    private void removeFromTile(AbstractMapElement element, Vector2d position){
        this.tilesOnMap.get(position).remove(element);

        if(this.tilesOnMap.get(position).isEmpty()){
            this.tilesOnMap.remove(position);
            this.freeTiles++;
        }
    }

    private Vector2d generateRandomUnoccupiedPosition() {
        if(this.freeTiles == 0)
            return null;
        Vector2d vec;
        do {
            vec = new Vector2d((int) ((this.upperBoundary.x +1) * Math.random()), (int) ((this.upperBoundary.y + 1)* Math.random()));
        }
        while (isTileOccupied(vec));
        return vec;
    }



}