package main.map;

import main.mapElements.AbstractMapElement;
import main.mapElements.Vector2d;

public interface IPositionChangeObserver {
    void elementPositionToBeChanged(AbstractMapElement element, Vector2d futurePosition);
}
