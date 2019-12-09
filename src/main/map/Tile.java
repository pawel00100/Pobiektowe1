/*TODO:
*  make animals split plant*/

package main.map;

import main.mapElements.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class CompareByEnergy implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(!(o1 instanceof AbstractMapElement) || !(o2 instanceof AbstractMapElement))
            throw new IllegalArgumentException("comparing not map element");
        AbstractMapElement element1 = (AbstractMapElement) o1;
        AbstractMapElement element2 = (AbstractMapElement) o2;
        if(!(element1 instanceof Animal)  && !(element2 instanceof Animal))
            return 0;
        if(!(element1 instanceof Animal))
            return 1;
        if(!(element2 instanceof Animal))
            return -1;

        return -Integer.compare(((Animal) element1).getEnergy(), ((Animal) element2).getEnergy());

    }
}


public class Tile {
    List<AbstractMapElement> elementsOnTile = new LinkedList<>();
    int numberOfAnimals = 0;
    int numberOfElements = 0;
    private Vector2d tilePosition;
    private RectangularMap map;

    public Tile(RectangularMap map, Vector2d tilePosition){
        this.map = map;
        this.tilePosition = tilePosition;
    }

    public void eatAndReproduce(){
        if(this.numberOfAnimals >= 1 && isGrassOnTile()){
            ((Animal)(getElementsByEnergy().get(0))).appendEnergy(50);
            removeGrass();
        }

        if(this.numberOfAnimals >= 2 && ((Animal)(getElementsByEnergy().get(1))).getEnergy() >= 50) {
            spawnChild((Animal)(getElementsByEnergy().get(0)), (Animal)(getElementsByEnergy().get(1)));

        }
    }

    public void putOnTile(AbstractMapElement element){
        this.elementsOnTile.add(element);
        if (element instanceof Animal)
            this.numberOfAnimals++;
        this.numberOfElements++;
    }

    public void removeFromTile(AbstractMapElement element){
        this.elementsOnTile.remove(element);
        if (element instanceof Animal)
            this.numberOfAnimals--;
        this.numberOfElements--;
    }

    public boolean isEmpty(){
        return this.elementsOnTile.isEmpty();
    }

    public boolean isElementOnTile(AbstractMapElement element){
        return this.elementsOnTile.contains(element);
    }

    public boolean isGrassOnTile(){
        for (AbstractMapElement element : this.elementsOnTile)
            if (element instanceof Grass)
                return true;
        return false;
    }

    public boolean isAnimalOnTile(){
        for (AbstractMapElement element : this.elementsOnTile)
            if (element instanceof Animal)
                return true;
        return false;
    }

    public List<AbstractMapElement> getElementsByEnergy(){
        List<AbstractMapElement> sortedList = new LinkedList<>(this.elementsOnTile);
        sortedList.sort(new CompareByEnergy());
        return sortedList;
    }

    private void removeGrass(){
        for (AbstractMapElement element : this.elementsOnTile)
            if (element instanceof Grass){
                this.map.remove(element);
                return;
            }
    }

    private void spawnChild(Animal parent1, Animal parent2){
        int energyTransfer1 = parent1.getEnergy()/4;
        int energyTransfer2 = parent2.getEnergy()/4;
        parent1.appendEnergy(-energyTransfer1);
        parent2.appendEnergy(-energyTransfer2);
        int childEnergy = energyTransfer1 + energyTransfer2;
        Vector2d childPosition = this.tilePosition.add(MapDirection.generateRandomDirection().toUnitVector());
        Animal child = new Animal(this.map, childPosition);
//        Animal child = new Animal(this.map, this.tilePosition);
        child.setEnergy(childEnergy);
    }
}
