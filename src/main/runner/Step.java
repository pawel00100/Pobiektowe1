package main.runner;

import main.map.RectangularMap;

class Step {
//    RectangularMap map;

    Step(RectangularMap map){

        map.updateEnergies();
        map.run();
        map.eatAndReproduce();
        map.placePlants();
        map.mapStateChanged();

    }
}
