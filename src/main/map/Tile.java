package main.map;

import main.mapElements.AbstractMapElement;
import main.mapElements.Animal;
import main.mapElements.Grass;

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

    public void eatAndReproduce(){
        if(numberOfAnimals >= 1 && isGrassOnTile()){
            ((Animal)(getElementsByEnergy().get(0))).appendEnergy(50);
            removeGrass();
        }

        if(numberOfAnimals >= 2){

        }
    }

    public void putOnTile(AbstractMapElement element){
        this.elementsOnTile.add(element);
        if (element instanceof Animal)
            numberOfAnimals++;
        numberOfElements++;
    }

    public void removeFromTile(AbstractMapElement element){
        this.elementsOnTile.remove(element);
        if (element instanceof Animal)
            numberOfAnimals--;
        numberOfElements--;
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
        List<AbstractMapElement> sortedList = new LinkedList<>(elementsOnTile);
        sortedList.sort(new CompareByEnergy());
        return sortedList;
    }

    private void removeGrass(){
        for (AbstractMapElement element : elementsOnTile)
            if (element instanceof Grass){
                removeFromTile(element);
                return;
            }
    }
}
