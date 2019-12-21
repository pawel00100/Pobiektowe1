package main;

import main.UI.UI;
import main.map.RectangularMap;
import org.json.JSONObject;

public class Run implements Runnable{
    RectangularMap map;


    public Run(RectangularMap map)  throws InterruptedException {
        this.map = map;

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


    @Override
    public void run() {
        try {
            new UI(map);
            long lastStepTIme = System.currentTimeMillis();
            while (true) {
                if (this.map.isRunning) {
                    double runSpeed = this.map.runSpeed;
                    int stepSize = (runSpeed > 40) ? (int) runSpeed / 10 : 1;

                    step(map, stepSize);


                    while (System.currentTimeMillis() - (long) (1000.0 / runSpeed * stepSize) < lastStepTIme) {
                        Thread.sleep(1);
                    }

                } else {
                    Thread.sleep(50);
                }

                lastStepTIme = System.currentTimeMillis();
            }
        }
        catch (InterruptedException ignored){
        };
    }

}
