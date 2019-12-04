package main;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    List<Animal> animalList = new ArrayList<>();
    Map<Vector2d, AbstractMapElement> elementsOnMap = new HashMap<>();


    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(AbstractMapElement element) {
        if (canMoveTo(element.getPosition())) {
            this.elementsOnMap.put(element.getPosition(), element);
            element.addObserver(this);
            if( element instanceof Animal)
                this.animalList.add( (Animal) element);
            return true;
        }
        throw new IllegalArgumentException("Trying to place on taken position " + element.getPosition());
    }

    @Override
    public void run(MoveDirection[] directions) {
        int animalNumber = this.animalList.size();
        if (animalNumber == 0) return;
        for (int i = 0; i < directions.length; i++) {
            Animal animal = this.animalList.get(i % animalNumber);
            animal.move(directions[i]);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.elementsOnMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        if(newPosition != null && !oldPosition.equals(newPosition)){
            AbstractMapElement element = this.elementsOnMap.get(oldPosition);
            this.elementsOnMap.remove(oldPosition);
            this.elementsOnMap.put(newPosition, element);
        }
    }

}



