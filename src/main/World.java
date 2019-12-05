package main;

public class World {
    public static void main(String[] Argc) {
        try {

            RectangularMap map = new RectangularMap(10, 5, 3);
            new Animal(map);
            new Animal(map, 3, 4);
            System.out.println(map);

        }
        catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}
