package main;

public class World {
    public static void main(String[] Argc) {
        try {
            Vector2d position1 = new Vector2d(1, 2);
            System.out.println(position1);
            Vector2d position2 = new Vector2d(-2, 1);
            System.out.println(position2);
            System.out.println(position1.add(position2));

            AbstractWorldMap map = new RectangularMap(4, 4);
            Animal animal = new Animal(map);
            System.out.println(animal);
            animal.move(MoveDirection.RIGHT);
            animal.move(MoveDirection.FORWARD);
            animal.move(MoveDirection.FORWARD);
            animal.move(MoveDirection.FORWARD);
            System.out.println(animal);


            String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            map = new RectangularMap(10, 5);
            new Animal(map);
            new Animal(map, 3, 4);
            System.out.println(map);




            GrassField map1 = new GrassField(5);
            new Animal(map1);
            new Animal(map1, 3, 4);
            new Animal(map1, 2, 8);
            new Animal(map1, 5, 1);
            new Animal(map1, 4, 3);
            System.out.println(map1.boundary.getLowerBoundary());
            System.out.println(map1.boundary.getUpperBoundary());

            System.out.println(map1);
        }
        catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}
