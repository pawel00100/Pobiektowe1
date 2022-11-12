package main;

import main.UI.UI;
import main.UI.UIState;
import main.map.RectangularMap;
import org.json.JSONObject;

public class Run implements Runnable{
    RectangularMap map;
    UIState uiState;


    public Run(RectangularMap map) {
        this.map = map;
    }

    private void step(RectangularMap map, int stepSize){
        for (int i = 0; i < stepSize; i++) {
            map.updateEnergies();
            map.run();
            map.eatAndReproduce();
            map.placePlants();
            map.mapStatistics.updateEpoch();

            if (this.map.parameters.getBoolean("saveHistory")) {
                uiState.mapStatisticsTextHistory.updateEpoch(map.mapStatistics);
            }
        }
        map.mapStateChanged();
    }


    @Override
    public void run() {
        try {
            var ui = new UI(map);
            uiState = ui.uiState;
            long lastStepTIme = System.currentTimeMillis();
            while (true) {
                if (uiState.isRunning()) {
                    double runSpeed = uiState.getRunSpeed();
                    int stepSize = (runSpeed > 4000) ? (int) runSpeed / 1000 : 1;

                    step(map, stepSize);


                    while (System.currentTimeMillis() - (long) (1000.0 / runSpeed * stepSize) < lastStepTIme) {
                        Thread.sleep(1);
                    }

                } else {
                    Thread.sleep(50);
                }

                lastStepTIme = System.currentTimeMillis();
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

}
