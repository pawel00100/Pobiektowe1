package main;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

class CompareByX implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(!(o1 instanceof Vector2d) || !(o2 instanceof Vector2d))
            throw new IllegalArgumentException("comparing not map element");
        Vector2d vector1 = (Vector2d) o1;
        Vector2d vector2 = (Vector2d) o2;
        int result = Integer.compare(vector1.x, vector2.x);
        if(result != 0)
            return result;
        return Integer.compare(vector1.y, vector2.y);
    }
}

class CompareByY implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(!(o1 instanceof Vector2d) || !(o2 instanceof Vector2d))
            throw new IllegalArgumentException("comparing not map element");
        Vector2d vector1 = (Vector2d) o1;
        Vector2d vector2 = (Vector2d) o2;
        int result = Integer.compare(vector1.y, vector2.y);
        if(result != 0)
            return result;
        return Integer.compare(vector1.x, vector2.x);
    }
}

public class MapBoundary implements IPositionChangeObserver{

    private SortedSet<Vector2d> sortedByXAxis = new TreeSet<Vector2d>(new CompareByX());
    private SortedSet<Vector2d> sortedByYAxis = new TreeSet<Vector2d>(new CompareByY());
    private int numOfElements = 0;

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        sortedByXAxis.remove(oldPosition);
        sortedByXAxis.add(newPosition);

        sortedByYAxis.remove(oldPosition);
        sortedByYAxis.add(newPosition);
    }

    public void addElement(Vector2d vector){
        sortedByXAxis.add(vector);
        sortedByYAxis.add(vector);
        numOfElements++;
    }

    public Boolean isEmpty(){
        return numOfElements == 0;
    }

    public Vector2d getUpperBoundary(){
        return new Vector2d(sortedByXAxis.last().x, sortedByYAxis.last().y);
    }

    public Vector2d getLowerBoundary(){
        return new Vector2d(sortedByXAxis.first().x, sortedByYAxis.first().y);
    }

}

