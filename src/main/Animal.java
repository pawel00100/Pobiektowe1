package main;


public class Animal extends AbstractMapElement{
    private MapDirection currentDirection = MapDirection.NORTH;

    private IWorldMap map;

    public Animal(IWorldMap map) {
        this.position = new Vector2d(2, 2);
        this.map = map;
        map.place(this);
    }

    public Animal(IWorldMap map, int x, int y) {
        this.position = new Vector2d(x, y);
        this.map = map;
        map.place(this);
    }

    public String toString() {
        return currentDirection.toShortString();
    }

    public MapDirection getDirection() {
        return this.currentDirection;
    }

    public void move(MoveDirection direction) {
        Vector2d futureVector;
        switch (direction) {
            case LEFT:
                this.currentDirection = this.currentDirection.previous();
                return;
            case RIGHT:
                this.currentDirection = this.currentDirection.next();
                return;

            case FORWARD:
            case BACKWARD:
                if (direction == MoveDirection.FORWARD)
                    futureVector = this.position.add(this.currentDirection.toUnitVector());
                else
                    futureVector = this.position.subtract(this.currentDirection.toUnitVector());

                if (this.map.canMoveTo(futureVector)){
                    positionChanged(this.position, futureVector);
                    this.position = futureVector;
                }
                return;
            default:
                throw new IllegalArgumentException("Wrong enum");
        }
    }


}
