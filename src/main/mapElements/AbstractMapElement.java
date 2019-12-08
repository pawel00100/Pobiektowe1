package main.mapElements;


import main.map.IWorldMap;

public abstract class AbstractMapElement {
    Vector2d position;
    IWorldMap map;

    AbstractMapElement(IWorldMap map){
        this.map = map;
    }

    public Vector2d getPosition() {
        return new Vector2d(this.position);
    }



}

