package main.map;


import main.mapElements.*;

import java.util.*;

public class RectangularMap implements IWorldMap, IPositionChangeObserver {

    private List<Animal> animalList = new ArrayList<>();
    private Map<Vector2d, List<AbstractMapElement>> tilesOnMap = new HashMap<>(); //map contains lists with elements on each tile

    public final Vector2d upperBoundary;
    public final Vector2d lowerBoundary = new Vector2d(0, 0);

    public RectangularMap(int x, int y, int number) {
        this.upperBoundary = new Vector2d(x, y);

        for (int i = 0; i < number; i++)
            place(new Grass(generateRandomPos()));
    }

    public RectangularMap(int x, int y) {
        this(x, y, 0);
    }

    @Override
    public String toString() {
        if (upperBoundary == null || lowerBoundary == null) return "no items";
        return new MapVisualizer(this).draw(this.lowerBoundary, this.upperBoundary);
    }

    @Override
    public void place(AbstractMapElement element) { //adds element on map, assumes it can be placed
        place(element, element.getPosition());
    }

    public void place(AbstractMapElement element, Vector2d position) { //adds element on map, assumes it can be placed
        if(isElementOnMap(element))
            throw new IllegalArgumentException("Trying to put element that is already on its position");
        putOnTile(element, position);
        if( element instanceof Animal) {
            this.animalList.add((Animal) element);
            ((Animal) element).addObserver(this);
        }
    }

    public void remove(AbstractMapElement element){
        this.remove(element, element.getPosition());
    }

    public void remove(AbstractMapElement element, Vector2d position){
        if(!isElementOnMap(element))
            throw new IllegalArgumentException("Trying to put element that is already on its position");
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



    public void elementPositionToBeChanged(AbstractMapElement element, Vector2d futurePosition) { //changes position of an element
        Vector2d currentPosition = element.getPosition();
        if (futurePosition != null && !currentPosition.equals(futurePosition)) {
            this.remove(element);
            this.place(element, futurePosition);
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
        if(!tilesOnMap.containsKey(position))
            this.tilesOnMap.put(element.getPosition(), new LinkedList<AbstractMapElement>());

        tilesOnMap.get(position).add(element);
    }

    private void removeFromTile(AbstractMapElement element, Vector2d position){
        tilesOnMap.get(position).remove(element);

        if(tilesOnMap.get(position).isEmpty())
            tilesOnMap.remove(position);
    }

    private Vector2d generateRandomPos() {
        Vector2d vec;
        do {
            vec = new Vector2d((int) (this.upperBoundary.x * Math.random()), (int) (this.upperBoundary.y * Math.random()));
        }
        while (isTileOccupied(vec));
        return vec;
    }

}