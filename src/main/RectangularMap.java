package main;


public class RectangularMap extends AbstractWorldMap {
    private Vector2d upperBoundary;
    private Vector2d lowerBoundary = new Vector2d(0,0);

    public RectangularMap(int x, int y, int number) {
        this.upperBoundary = new Vector2d(x, y);

        for (int i = 0; i < number; i++)
            place(new Grass(generateRandomPos(number)));
    }

    public RectangularMap(int x, int y){
        this(x, y, 0);
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

    private Vector2d generateRandomPos(int number) {
        Vector2d vec;
        do{
            double maxPos = Math.sqrt(number * 20);
            vec = new Vector2d((int) (maxPos * Math.random()), (int) (maxPos * Math.random()));
        }
        while(!this.canMoveTo(vec));
        return vec;
    }

}