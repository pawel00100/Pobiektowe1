package main.mapElements;

public class Grass extends AbstractMapElement{

    public Grass(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "*";
    }

}
