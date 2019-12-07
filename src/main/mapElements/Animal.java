package main.mapElements;


import main.map.IPositionChangeObserver;
import main.map.IWorldMap;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 100;
    private List<IPositionChangeObserver> observers = new LinkedList<>();
    private IWorldMap map;

    public Animal(IWorldMap map, int x, int y) {
        this.position = new Vector2d(x, y);
        this.map = map;
        map.place(this);
    }

    public Animal(IWorldMap map) {
        this(map, 2, 2);
    }

    public String toString() {
        return currentDirection.toShortString();
    }

    public int getEnergy(){
        return energy;
    }

    public MapDirection getDirection() {
        return this.currentDirection;
    }

    public void updateEnergy(){
        this.energy--;
        if(this.energy <= 0);
        this.map.remove(this);
    }

    public void moveForward(){
        this.energy -= 1;
        Vector2d futureVector = this.position.add(this.currentDirection.toUnitVector());
            positionToBeChanged(this.position, futureVector);
            this.position = futureVector;
    }

    private void generateDirection(){
        this.currentDirection = MapDirection.values()[(int)(8 * Math.random())];
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionToBeChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers) {
            observer.elementPositionToBeChanged(this, newPosition);
        }
    }

}
