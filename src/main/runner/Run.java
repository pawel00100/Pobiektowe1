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
                new Step(map, stepSize, i);

                while (System.currentTimeMillis() - (long) (250.0 / (double) parameters.get("runSpeed")) < lastStepTIme) {
                    Thread.sleep(10);
                }

                lastStepTIme = System.currentTimeMillis();

                i++;
            } else
                Thread.sleep(50);

        }

    }

}
