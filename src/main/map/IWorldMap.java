package main.map;

import main.mapElements.AbstractMapElement;
import main.mapElements.MoveDirection;
import main.mapElements.Vector2d;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Position and MoveDirection classes are defined.
 *
 * @author apohllo
 */
public interface IWorldMap {

    /**
     * Place a car on the map.
     *
     * @param element The car to place on the map.
     * @return True if the car was placed. The car cannot be placed if the map is already occupied.
     */
    void place(AbstractMapElement element);

    void remove(AbstractMapElement element);
    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the car
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isTileOccupied(Vector2d position);



}
