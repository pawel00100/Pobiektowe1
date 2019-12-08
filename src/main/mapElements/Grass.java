package main.mapElements;

import main.map.IWorldMap;

public class Grass extends AbstractMapElement{


    public Grass(IWorldMap map, Vector2d position) {
        super(map);
        this.position = position;
        map.place(this);
    }

    public String toString() {
        return "*";
    }

}
