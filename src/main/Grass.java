package main;

public class Grass extends AbstractMapElement{

    Grass(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        return "*";
    }

}
