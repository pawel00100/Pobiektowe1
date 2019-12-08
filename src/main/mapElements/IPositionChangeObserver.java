package main.mapElements;


public interface IPositionChangeObserver {
    void elementPositionToBeChangedTo(AbstractMapElement element, Vector2d futurePosition);
}
