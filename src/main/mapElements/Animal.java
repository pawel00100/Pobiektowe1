package main.mapElements;


import main.Parameters;
import main.map.RectangularMap;

import java.util.*;

public class Animal extends AbstractMapElement{
    private final SplittableRandom random = new SplittableRandom();

    private final RectangularMap map;
    private MapDirection currentDirection = MapDirection.NORTH;
    private int energy = 0;
    private int lifespan = 0;
    private IPositionChangeObserver positionChangeObserver = null;
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
        this.appendEnergy(Parameters.STARTING_ENERGY);
        this.position = checkCrossingBoundary(position);

        map.place(this);
        map.mapStatistics.addGenome(genome);
    }

    public Animal(RectangularMap map, Vector2d position, Animal parent1, Animal parent2){
        this(map, position, new Genome(parent1.genome, parent2.genome));

        parent1.changeNumberOfChildren(1);
        parent2.changeNumberOfChildren(1);
        parent1.numberOfChildrenSinceChoosing++;
        parent2.numberOfChildrenSinceChoosing++;

        if( map.uiStateTemp.getChosenAnimal() != null && (parent1.greatestAncestor == map.uiStateTemp.getChosenAnimal() || parent2.greatestAncestor == map.uiStateTemp.getChosenAnimal())){
            addThisToList(map.uiStateTemp.getChosenAnimal().descendantsOfChosenAnimal);
            greatestAncestor = map.uiStateTemp.getChosenAnimal();
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
        return lifespan;
    }

    public int getNumberOfChildrenSinceChoosing(){
        return numberOfChildrenSinceChoosing;
    }

    public int getNumberOfDescendants(){
        return descendantsOfChosenAnimal.size();
    }

    public Genome getGenome(){
        return genome;
    }

    public void setEnergy(int newEnergy){
        int startingEnergy = energy;
        energy = Math.min(newEnergy, 250);
        energyChanged(energy - startingEnergy);
    }

    public void appendEnergy(int newEnergy){
        int startingEnergy = energy;
        energy = Math.min(energy + newEnergy, 250);
        energyChanged(energy - startingEnergy);
    }

    public void move(){
        generateDirection();
        moveForward();
        lifespan++;
    }

    public void setTracked(){
        greatestAncestor = this;
        numberOfChildrenSinceChoosing = 0;
    }

    public void addObserver(IPositionChangeObserver observer){
        positionChangeObserver = observer;
    }

    public void removeObserver(IPositionChangeObserver observer){
        if (positionChangeObserver.equals(observer)) {
            observer = null;
        }
    }

    public void onDeath(){
        setNumberOfChildren(0);
        map.mapStatistics.removeGenome(genome);
    }

    private void generateDirection(){
        int geneNumber = random.nextInt(32);
        int rotationNumber = genome.getGene(geneNumber);
        currentDirection = currentDirection.rotateBy(rotationNumber);
    }

    private void addThisToList(ArrayList<Animal> list){
        if(!list.contains(this))
            list.add(this);
    }

    private void changeNumberOfChildren(int numberOfChildrenChange){
        numberOfChildren += numberOfChildrenChange;
        map.animalNumberOfChildrenChanged(numberOfChildrenChange);
    }

    private void setNumberOfChildren(int numberOfChildren){
        int change = this.numberOfChildren - numberOfChildren;
        changeNumberOfChildren(change);
    }

    private void energyChanged(int energyChange){
        map.animalEnergyChanged(energyChange);
    }

    private void positionChanged(Vector2d newPosition){
        positionChangeObserver.elementPositionToBeChangedTo(this, newPosition);
    }

    private  void moveForward(){
        this.appendEnergy(-Parameters.MOVE_ENERGY);
        Vector2d futureVector = this.position.add(this.currentDirection.toUnitVector());
        futureVector = checkCrossingBoundary(futureVector);
        positionChanged(futureVector);
        position = futureVector;
    }


    private Vector2d checkCrossingBoundary(Vector2d position){
        if(position.x > map.upperBoundary.x)
            position =  new Vector2d(0, position.y) ;

        if(position.y > map.upperBoundary.y)
            position =  new Vector2d(position.x, 0) ;

        if(position.x < map.lowerBoundary.x)
            position =  new Vector2d(map.upperBoundary.x, position.y) ;

        if(position.y < map.lowerBoundary.y)
            position =  new Vector2d(position.x, map.upperBoundary.y) ;


        return position;
    }


}
