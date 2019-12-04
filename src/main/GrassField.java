package main;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap {

    MapBoundary boundary = new MapBoundary();

    public GrassField(int number) {

        for (int i = 0; i < number; i++)
            place(new Grass(generateRandomPos(number)));

    }

    @Override
    public String toString() {
        if (boundary.isEmpty()) return "no items";
        return new MapVisualizer(this).draw(this.boundary.getLowerBoundary(), this.boundary.getUpperBoundary());
    }

    @Override
    public boolean place(AbstractMapElement element) {
        if (super.place(element)) {
            boundary.addElement(element.getPosition());
            element.addObserver(this.boundary);
            return true;
        }
        return false;
    }


    private Vector2d generateRandomPos(int number) {
        Vector2d vec;
        do{
            double maxPos = Math.sqrt(number * 10);
            vec = new Vector2d((int) (maxPos * Math.random()), (int) (maxPos * Math.random()));
        }
        while(this.isOccupied(vec));
        return vec;
    }
}
