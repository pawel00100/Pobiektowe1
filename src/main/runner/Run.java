package main.runner;

import main.map.RectangularMap;

public class Run {
    RectangularMap map;

    public Run(RectangularMap map){
        this.map = map;

        while (true){
            new Step(map);
            try{
                Thread.sleep(500);
            }
            catch (Exception ignored){}
        }

    }

}
