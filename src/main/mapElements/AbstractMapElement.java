package main.mapElements;

import main.map.IPositionChangeObserver;
import main.Vector2d;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMapElement {
    Vector2d position;
    private List<IPositionChangeObserver> observers = new LinkedList<>();

    public Vector2d getPosition() {
        return new Vector2d(this.position);
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

}

