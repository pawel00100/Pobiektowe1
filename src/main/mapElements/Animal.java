package main.mapElements;


import main.map.IWorldMap;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 100;
    private List<IPositionChangeObserver> observers = new LinkedList<>();
    private Genome genome;

    public Animal(IWorldMap map, Vector2d position) {
        super(map);
        this.position = staysOnMap(position);
        map.place(this);
        this.genome = new Genome();
    }

    public Animal(IWorldMap map, Vector2d position, Animal parent1, Animal parent2){
        this(map, position);
        this.genome = new Genome(parent1.genome, parent2.genome);
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

    public Genome getGenome(){
        return this.genome;
    }

    public MapDirection getDirection() {
        return this.currentDirection;
    }

    public void setEnergy(int energy){ //only for tests
        this.energy = Math.min(energy, 100);
    }

    public void appendEnergy(int energy){
        this.energy = Math.min(this.energy + energy, 100);
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
        int geneNumber = (int) Math.floor(Math.random() * 32);
        int rotationNumber = this.genome.genes.get(geneNumber);
        this.currentDirection = this.currentDirection.rotateBy(rotationNumber);
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
