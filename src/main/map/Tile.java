package main.map;

import main.mapElements.*;

import java.util.*;

public class Tile {
    private final List<Animal> animalsOnTile = new ArrayList<>(6);
    private int numberOfAnimals = 0;
    private final Vector2d tilePosition;
    private final RectangularMap map;
    public static final Comparator<Object> COMPARATOR = Tile::compareElements;
    private Grass grass = null;

    public Tile(RectangularMap map, Vector2d tilePosition){
        this.map = map;
        this.tilePosition = tilePosition;
    }

    public Vector2d getTilePosition() {
        return tilePosition;
    }

    boolean eatAndReproduce(){
        boolean grassEaten = false;
        if(numberOfAnimals >= 1 && isGrassOnTile()){
            eatGrass();
            grassEaten = true;
        }

        if(numberOfAnimals >= 2 && (getElementsByEnergy().get(1)).getEnergy() >= 50) {
            spawnChild(getElementsByEnergy().get(0), getElementsByEnergy().get(1));
        }
        return grassEaten;
    }

    public void putOnTile(AbstractMapElement element){
        if (element instanceof Grass grass) {
            this.grass = grass;
            return;
        }

        animalsOnTile.add( (Animal) element);
        animalsOnTile.sort(Tile.COMPARATOR);
        numberOfAnimals++;
    }

    public void removeFromTile(AbstractMapElement element){
        if (element instanceof Grass) {
            removeGrass();
            return;
        }

        animalsOnTile.remove(element);
        if (element instanceof Animal)
            numberOfAnimals--;
    }

    public boolean isEmpty(){
        return this.animalsOnTile.isEmpty() && grass == null;
    }

    public boolean isElementOnTile(AbstractMapElement element){
        return this.animalsOnTile.contains(element) || element.equals(grass);
    }

    public boolean isGrassOnTile(){
        return grass != null;
    }

    public boolean isAnimalOnTile(){
        for (AbstractMapElement element : this.animalsOnTile)
            if (element instanceof Animal)
                return true;
        return false;
    }

    public List<Animal> getElementsByEnergy(){
        return animalsOnTile;
    }

    public Animal getStrongestAnimal(){
        return getElementsByEnergy().get(0);
    }

    private List<Animal> getStrongestAnimals(){
        var strongestAnimal = this.getStrongestAnimal();

        List<Animal> list = null; //Not using stream for performance
        for (Animal animal : animalsOnTile) {
            if (compareElements(animal, strongestAnimal) == 0) {
                if (list == null) {
                    list = Collections.singletonList(animal);
                } else {
                    list = new ArrayList<>(list);
                    list.add(animal);
                }
            }
        }
        return list;
    }

    private void eatGrass(){
        int energy = map.parameters.getInt("plantEnergy");

        for(Animal animal : getStrongestAnimals())
            animal.appendEnergy(energy/getStrongestAnimals().size());
        removeGrass();
    }

    private void removeGrass(){
        grass = null;
    }

    private void spawnChild(Animal parent1, Animal parent2){
        int energyTransfer1 = parent1.getEnergy()/4;
        int energyTransfer2 = parent2.getEnergy()/4;
        parent1.appendEnergy(-energyTransfer1);
        parent2.appendEnergy(-energyTransfer2);
        int childEnergy = energyTransfer1 + energyTransfer2;
        Vector2d childPosition = tilePosition.add(MapDirection.generateRandomDirection().toUnitVector());
        Animal child = new Animal(map, childPosition, parent1, parent2);
        child.setEnergy(childEnergy);
    }

    private static int compareElements(Object o1, Object o2) {
        if(!(o1 instanceof AbstractMapElement element1) || !(o2 instanceof AbstractMapElement element2))
            throw new IllegalArgumentException("comparing not map element");
        if(!(element1 instanceof Animal)  && !(element2 instanceof Animal))
            return 0;
        if(!(element1 instanceof Animal))
            return 1;
        if(!(element2 instanceof Animal))
            return -1;

        return -Integer.compare(((Animal) element1).getEnergy(), ((Animal) element2).getEnergy());

    }
}
