/*TODO:
*  make animals split plant*/

package main.map;

import main.mapElements.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tile {
    private List<AbstractMapElement> elementsOnTile = new LinkedList<>();
    private int numberOfAnimals = 0;
    private Vector2d tilePosition;
    private RectangularMap map;

    public Tile(RectangularMap map, Vector2d tilePosition){
        this.map = map;
        this.tilePosition = tilePosition;
    }

    void eatAndReproduce(){
        if(numberOfAnimals >= 1 && isGrassOnTile()){
            eatGrass();
        }

        if(numberOfAnimals >= 2 && ((Animal)(getElementsByEnergy().get(1))).getEnergy() >= 50) {
            spawnChild((Animal)(getElementsByEnergy().get(0)), (Animal)(getElementsByEnergy().get(1)));

        }
    }

    public void putOnTile(AbstractMapElement element){
        elementsOnTile.add(element);
        elementsOnTile.sort(this::compareElements);
        if (element instanceof Animal)
            numberOfAnimals++;
    }

    public void removeFromTile(AbstractMapElement element){
        elementsOnTile.remove(element);
        if (element instanceof Animal)
            numberOfAnimals--;
    }

    public boolean isEmpty(){
        return elementsOnTile.isEmpty();
    }

    public boolean isElementOnTile(AbstractMapElement element){
        return elementsOnTile.contains(element);
    }

    public boolean isGrassOnTile(){
        for (AbstractMapElement element : elementsOnTile)
            if (element instanceof Grass)
                return true;
        return false;
    }

    public boolean isAnimalOnTile(){
        for (AbstractMapElement element : elementsOnTile)
            if (element instanceof Animal)
                return true;
        return false;
    }

    public List<AbstractMapElement> getElementsByEnergy(){
        return elementsOnTile;
    }

    public Animal getStrongestAnimal(){
        AbstractMapElement element = getElementsByEnergy().get(0);
        if(!(element instanceof Animal))
            throw new IllegalArgumentException("Trying to get animal from tile with no animals");
        return (Animal) element;
    }

    private List<Animal> getStrongestAnimals(){
        List<Animal> list = new ArrayList<>();
        int i = 0;
        while(compareElements(elementsOnTile.get(i), getStrongestAnimal()) == 0 ) {
            list.add((Animal) getElementsByEnergy().get(i));
            i++;
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
        for (AbstractMapElement element : elementsOnTile)
            if (element instanceof Grass){
                map.remove(element);
                return;
            }
    }

    private void spawnChild(Animal parent1, Animal parent2){
        int energyTransfer1 = parent1.getEnergy()/4;
        int energyTransfer2 = parent2.getEnergy()/4;
        parent1.appendEnergy(-energyTransfer1);
        parent2.appendEnergy(-energyTransfer2);
        int childEnergy = energyTransfer1 + energyTransfer2;
        Vector2d childPosition = tilePosition.add(MapDirection.generateRandomDirection().toUnitVector());
        Animal child = new Animal(map, childPosition, parent1, parent2);
//        Animal child = new Animal(map, tilePosition);
        child.setEnergy(childEnergy);
    }

    private int compareElements(Object o1, Object o2) {
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
