package main;

import main.UI.UI;

public class World {
    public static void main(String[] Argc) {
        try {

            RectangularMap map = new RectangularMap(30, 20, 3);
            new Animal(map);
            new Animal(map, 3, 4);
            System.out.println(map);

//            RectangularMap map = new RectangularMap(60, 40, 10);
//            new Animal(map);
//            new Animal(map, 3, 4);
//            new Animal(map, 40, 16);
//            new Animal(map, 13, 26);

            new UI(map);

        }
        catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}


