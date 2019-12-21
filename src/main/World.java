package main;

import main.map.RectangularMap;
import main.mapElements.Animal;
import main.UI.UI;
import main.mapElements.Genome;
import main.mapElements.Vector2d;
import main.parser.Parser;
import main.runner.Run;
import org.json.JSONObject;

import java.io.IOException;

public class World { ;
    public static void main(String[] Argc) {
        try {
            JSONObject parameters = new Parser().obj;

            RectangularMap map1 = new RectangularMap(parameters);
            RectangularMap map2 = new RectangularMap(parameters);

            generateAnimals(parameters.getInt("initialAnimals"), map1, map2);

            new UI(map1);
            new UI(map2);


            Thread t1 = new Thread(new Run(map1));
            Thread t2 = new Thread(new Run(map2));
            t1.start();
            t2.start();

        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }


    private static void generateAnimals(int number, RectangularMap map1, RectangularMap map2){
        for (int i = 0; i < number; i++) {
            Vector2d position = generateRandomPosition(map1);
            Genome genome = new Genome();
            new Animal(map1, position, genome);
            new Animal(map2, position, genome);
        }
    }

    private static Vector2d generateRandomPosition(RectangularMap map){
        int xOffset = (int) ((map.upperBoundary.x - map.lowerBoundary.x + 1) * Math.random());
        int yOffset = (int) ((map.upperBoundary.y - map.lowerBoundary.y + 1) * Math.random());
        return new Vector2d(xOffset + map.lowerBoundary.x, yOffset + map.lowerBoundary.y);
    }
}


