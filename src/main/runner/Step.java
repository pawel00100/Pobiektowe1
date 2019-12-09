package main.runner;

import main.map.RectangularMap;

class Step {
//    RectangularMap map;

    Step(RectangularMap map, int stepSize, int step){
        for (int i = 0; i < stepSize; i++) {
            map.updateEnergies();
            map.run();
            map.eatAndReproduce();
            map.placePlants();
        }
        System.out.println(step);
        map.mapStateChanged();
    }
}
