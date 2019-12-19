package main;

import main.map.RectangularMap;
import main.mapElements.Animal;
import main.UI.UI;
import main.parser.Parser;
import main.runner.Run;
import org.json.JSONObject;

import java.io.IOException;

public class World {
    public static void main(String[] Argc) {
        try {
            JSONObject parameters = new Parser().obj;

            RectangularMap map1 = new RectangularMap(parameters);
            RectangularMap map2 = new RectangularMap(parameters);

            new UI(map1, map2, parameters);

            new Run(map1, map2, parameters);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
}


