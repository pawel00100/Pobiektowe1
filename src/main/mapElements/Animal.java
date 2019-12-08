package main.mapElements;


import main.map.IWorldMap;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 100;
    private List<IPositionChangeObserver> observers = new LinkedList<>();
    private IWorldMap map;

    public Animal(IWorldMap map, Vector2d position) {
        this.position = position;
        this.map = map;
        map.place(this);
    }

    public Animal(IWorldMap map, int x, int y) {
        this(map, new Vector2d(x, y));
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

    public void setEnergy(int energy){ //only for tests
        this.energy = energy;
    }

    public void appendEnergy(int energy){
        this.energy += energy;
    }

    public void updateEnergy(){
        this.energy--;
        if(this.energy <= 0);
        this.map.remove(this);
    }

    public void move(){
        generateDirection();
        moveForward();
    }

    public void moveForward(){
        this.energy -= 1;
        Vector2d futureVector = this.position.add(this.currentDirection.toUnitVector());
        futureVector = staysOnMap(futureVector);
        positionToBeChangedTo(futureVector);
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

    private void positionToBeChangedTo(Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers) {
            observer.elementPositionToBeChangedTo(this, newPosition);
        }
    }

    private Vector2d staysOnMap(Vector2d position){
        if(position.x > this.map.upperBoundary().x)
            position = position.subtract( new Vector2d(this.map.upperBoundary().x + 1, 0) );
        if(position.y > this.map.upperBoundary().y)
            position = position.subtract( new Vector2d(0, this.map.upperBoundary().y + 1) );
        if(position.x < this.map.lowerBoundary().x)
            position = position.add( new Vector2d(this.map.upperBoundary().x + 1, 0) );
        if(position.y < this.map.lowerBoundary().y)
            position = position.add( new Vector2d(0, this.map.upperBoundary().y + 1) );

        return position;
    }


}
