package main.mapElements;


import main.map.RectangularMap;

public abstract class AbstractMapElement {
    Vector2d position;
    RectangularMap map;

    AbstractMapElement(RectangularMap map){
        this.map = map;
    }

    public Vector2d getPosition() {
        return new Vector2d(position);
    }



}

