package main.mapElements;


import main.map.RectangularMap;

import java.util.*;

public class Animal extends AbstractMapElement{
    private RectangularMap map;
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 0;
    private int lifespan = 0;
    private List<IPositionChangeObserver> positionChangeObservers = new LinkedList<>();
    private Genome genome;
    private Animal greatestAncestor = null;
    private int numberOfChildren = 0;
    private int numberOfChildrenSinceChoosing = 0;
    private ArrayList<Animal> descendantsOfChosenAnimal = new ArrayList<>();

    public Animal(RectangularMap map, Vector2d position) {
        this(map, position, new Genome());
    }

    public Animal(RectangularMap map, Vector2d position, Genome genome) {
        super(map);
        this.map = map;
        this.genome = genome;
        this.appendEnergy(100);
        this.position = checkCrossingBoundary(position);

        this.map.place(this);
        this.map.mapStatistics.addGenome(genome);
    }

    public Animal(RectangularMap map, Vector2d position, Animal parent1, Animal parent2){
        this(map, position, new Genome(parent1.genome, parent2.genome));

        parent1.changeNumberOfChildren(1);
        parent2.changeNumberOfChildren(1);
        parent1.numberOfChildrenSinceChoosing++;
        parent2.numberOfChildrenSinceChoosing++;

        if((parent1.greatestAncestor == map.chosenAnimal || parent2.greatestAncestor == map.chosenAnimal) && map.chosenAnimal != null ){
            addThisToList(map.chosenAnimal.descendantsOfChosenAnimal);
            this.greatestAncestor = map.chosenAnimal;
        }
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


    public int getNumberOfChildrenSinceChoosing(){
        return numberOfChildrenSinceChoosing;
    }

    public int getNumberOfDescendants(){
        return this.descendantsOfChosenAnimal.size();
    }

    public Genome getGenome(){
        return this.genome;
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

    public void setTracked(){
        this.greatestAncestor = this;
        this.numberOfChildrenSinceChoosing = 0;
    }

    public void addObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.positionChangeObservers.remove(observer);
    }

    public void onDeath(){
        setNumberOfChildren(0);
        this.map.mapStatistics.removeGenome(this.genome);
    }

    private void generateDirection(){
        int geneNumber = (int) Math.floor(Math.random() * 32);
        int rotationNumber = this.genome.getGene(geneNumber);
        this.currentDirection = this.currentDirection.rotateBy(rotationNumber);
    }

    private void addThisToList(ArrayList<Animal> list){
        if(!list.contains(this))
            list.add(this);
    }

    private void changeNumberOfChildren(int numberOfChildrenChange){
        this.numberOfChildren += numberOfChildrenChange;
        this.map.animalNumberOfChildrenChanged(numberOfChildrenChange);
    }

    private void setNumberOfChildren(int numberOfChildren){
        int change = numberOfChildren - this.numberOfChildren;
        changeNumberOfChildren(change);
    }

    private void energyChanged(int energyChange){
        this.map.animalEnergyChanged(energyChange);
    }

    private void positionChanged(Vector2d newPosition){
        for (IPositionChangeObserver observer : this.positionChangeObservers) {
            observer.elementPositionToBeChangedTo(this, newPosition);
        }
    }

    private void moveForward(){
        this.appendEnergy(-1);
        Vector2d futureVector = this.position.add(this.currentDirection.toUnitVector());
        futureVector = checkCrossingBoundary(futureVector);
        positionChanged(futureVector);
        this.position = futureVector;
    }


    private Vector2d checkCrossingBoundary(Vector2d position){
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
