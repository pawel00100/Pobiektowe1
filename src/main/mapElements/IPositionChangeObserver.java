package main.mapElements;

import main.mapElements.AbstractMapElement;
import main.mapElements.Vector2d;

public interface IPositionChangeObserver {
    void elementPositionToBeChangedTo(AbstractMapElement element, Vector2d futurePosition);
}
