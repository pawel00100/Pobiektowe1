package main.mapElements;


import main.map.RectangularMap;

import java.util.LinkedList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 0;
    private int lifespan = 0;
    private List<IPositionChangeObserver> positionChangeObservers = new LinkedList<>();
    private List<IAnimalEnergyChangeObserver> energyChangeObservers = new LinkedList<>();
    private Genome genome;

    public Animal(RectangularMap map, Vector2d position) {
        super(map);
        this.appendEnergy(100);
        this.position = staysOnMap(position);
        map.place(this);
        this.genome = new Genome();
    }

    public Animal(RectangularMap map, Vector2d position, Animal parent1, Animal parent2){
        this(map, position);
        this.genome = new Genome(parent1.genome, parent2.genome);
    }


    public Animal(RectangularMap map, int x, int y) {
        this(map, new Vector2d(x, y));
    }

    public Animal(RectangularMap map) {
        this(map, 2, 2);
    }

    public String toString() {
        return currentDirection.toShortString();
    }

    public int getEnergy(){
        return energy;
    }

    public int getLifespan(){
        return this.lifespan;
    }

    public Genome getGenome(){
        return this.genome;
    }

    public MapDirection getDirection() {
        return this.currentDirection;
    }

    public void setEnergy(int energy){ //only for tests
        int startingEnergy = this.energy;
        this.energy = Math.min(energy, 100);
        this.energyChanged(this.energy - startingEnergy);
    }

    public void appendEnergy(int energy){
        int startingEnergy = this.energy;
        this.energy = Math.min(this.energy + energy, 100);
        this.energyChanged(this.energy - startingEnergy);
    }

    public void move(){
        generateDirection();
        moveForward();
        this.lifespan++;
    }

    public void moveForward(){
        this.appendEnergy(-1);
        Vector2d futureVector = this.position.add(this.currentDirection.toUnitVector());
        futureVector = staysOnMap(futureVector);
        positionChanged(futureVector);
        this.position = futureVector;
    }



    private void generateDirection(){
        int geneNumber = (int) Math.floor(Math.random() * 32);
        int rotationNumber = this.genome.getGene(geneNumber);
        this.currentDirection = this.currentDirection.rotateBy(rotationNumber);
    }

    public void addObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.remove(observer);
    }

    public void addEnegyChangeObserver(IAnimalEnergyChangeObserver observer){
        this.energyChangeObservers.add(observer);
    }

    public void removeEnergyChangeObserver(IAnimalEnergyChangeObserver observer){
        this.energyChangeObservers.add(observer);
    }

    private void energyChanged(int energyChange){
        this.map.animalEnergyChanged(this, energyChange);
    }

    private void positionChanged(Vector2d newPosition){
        for (IPositionChangeObserver observer : this.positionChangeObservers) {
            observer.elementPositionToBeChangedTo(this, newPosition);
        }
    }

    private Vector2d staysOnMap(Vector2d position){
        if(position.x > this.map.upperBoundary.x)
            position = position.subtract( new Vector2d(this.map.upperBoundary.x + 1, 0) );
        if(position.y > this.map.upperBoundary.y)
            position = position.subtract( new Vector2d(0, this.map.upperBoundary.y + 1) );
        if(position.x < this.map.lowerBoundary.x)
            position = position.add( new Vector2d(this.map.upperBoundary.x + 1, 0) );
        if(position.y < this.map.lowerBoundary.y)
            position = position.add( new Vector2d(0, this.map.upperBoundary.y + 1) );

        return position;
    }


}
