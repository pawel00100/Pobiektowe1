package main;

import main.map.RectangularMap;
import main.mapElements.Animal;
import main.UI.UI;
import main.runner.Run;

public class World {
    public static void main(String[] Argc) {
        try {

            RectangularMap map = new RectangularMap(30, 20, 20);
            new Animal(map, 13, 16);
            new Animal(map, 5, 4);
//            System.out.println(map);

//            RectangularMap map = new RectangularMap(60, 40, 10);
//            new Animal(map);
//            new Animal(map, 3, 4);
//            new Animal(map, 40, 16);
//            new Animal(map, 13, 26);

            new UI(map);

            new Run(map);

            System.out.println("aaaa");

        }
        catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

    }
}


