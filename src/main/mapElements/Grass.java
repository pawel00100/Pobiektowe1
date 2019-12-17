package main.mapElements;

import main.map.RectangularMap;

public class Grass extends AbstractMapElement{


    public Grass(RectangularMap map, Vector2d position) {
        super(map);
        this.position = position;
        map.place(this);
    }

    public String toString() {
        return "*";
    }

}
