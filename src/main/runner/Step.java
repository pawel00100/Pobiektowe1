package main.runner;

import main.map.RectangularMap;

class Step {
//    RectangularMap map;

    Step(RectangularMap map){
//        this.map = map;

        map.run();
//        System.out.println(map);

        /*
        map.updateEnergies()
        map.run()
        map.eatAndReproduce()
        map.generateGrass()
        */
    }
}
