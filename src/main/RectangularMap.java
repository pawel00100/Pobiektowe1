package main;


public class RectangularMap extends AbstractWorldMap {
    private Vector2d upperBoundary;
    private Vector2d lowerBoundary;

    public RectangularMap(int x, int y) {
        this.upperBoundary = new Vector2d(x, y);
        this.lowerBoundary = new Vector2d(0,0);
    }

    @Override
    public String toString() {
        if (upperBoundary == null || lowerBoundary == null) return "no items";
        return new MapVisualizer(this).draw(this.lowerBoundary, this.upperBoundary);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return vectorInBoundary(position) && super.canMoveTo(position);
    }

    private Boolean vectorInBoundary(Vector2d vector) {
        return (vector.follows(this.lowerBoundary) && vector.precedes( this.upperBoundary));
    }

}