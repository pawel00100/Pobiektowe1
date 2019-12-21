package main.runner;

import main.map.RectangularMap;
import org.json.JSONObject;

public class Run {

    public Run(RectangularMap map1, RectangularMap map2, JSONObject parameters) throws InterruptedException {
        long lastStepTIme = System.currentTimeMillis();
        while (true) {
            if ((boolean) parameters.get("isRunning")) {
                double runSpeed = (double) parameters.get("runSpeed");
                int stepSize = (runSpeed > 40) ? (int)runSpeed / 20 : 1;

                step(map1, stepSize);
                step(map2, stepSize);


                while (System.currentTimeMillis() - (long) (1000.0 / runSpeed * stepSize) < lastStepTIme) {
                    Thread.sleep(1);
                }

            } else {
                Thread.sleep(50);
            }
            lastStepTIme = System.currentTimeMillis();
        }

    }

    private void step(RectangularMap map, int stepSize){
        for (int i = 0; i < stepSize; i++) {
            map.updateEnergies();
            map.run();
            map.eatAndReproduce();
            map.placePlants();
            map.mapStatistics.updateEpoch();
        }
        map.mapStateChanged();
    }



}
