package main.runner;

import main.map.RectangularMap;

class Step {
    RectangularMap map;

    Step(RectangularMap map){
        this.map = map;
        System.out.println("aa");
        /*
        map.updateEnergies()
        map.run()
        map.eatAndReproduce()
        map.generateGrass()
        */
    }
}
