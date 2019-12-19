package main.runner;

import main.map.RectangularMap;
import org.json.JSONObject;

public class Run {
    private int i = 0;

    public Run(RectangularMap map, JSONObject parameters) throws InterruptedException {
        long lastStepTIme = System.currentTimeMillis();
        while (true) {
            if ((boolean) parameters.get("isRunning")) {
                int stepSize = ((double) parameters.get("runSpeed") > 50) ? 5 : 1;

                step(map, stepSize, i);

                while (System.currentTimeMillis() - (long) (250.0 / (double) parameters.get("runSpeed")) < lastStepTIme) {
                    Thread.sleep(10);
                }

                lastStepTIme = System.currentTimeMillis();

                i++;
            } else
                Thread.sleep(50);

        }

    }

    private void step(RectangularMap map, int stepSize, int step){
        for (int i = 0; i < stepSize; i++) {
            map.updateEnergies();
            map.run();
            map.eatAndReproduce();
            map.placePlants();
            map.updateEpoch();
        }
        map.mapStateChanged();
    }



}
