package main;

import main.UI.UI;
import main.UI.UIState;
import main.map.RectangularMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Run implements Runnable {
    private static final int MEASUREMENT_PERIOD_MILLIS = 500;

    RectangularMap map;
    UIState uiState;

    ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

    public Run(RectangularMap map) {
        this.map = map;
    }

    private void step(RectangularMap map, UI ui, int stepSize){
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
        if (uiState.readyForNextFrame()) {
            ui.mapSnapshotHolder.createSnapshot();
        }
    }


    @Override
    public void run() {
        scheduledExecutor.scheduleAtFixedRate(new PerformanceDiagnosis(), 0, MEASUREMENT_PERIOD_MILLIS, TimeUnit.MILLISECONDS);

        try {
            var ui = new UI(map);
            uiState = ui.uiState;
            long lastStepTIme = System.currentTimeMillis();
            step(map, ui, 1);
            ui.mapSnapshotHolder.createSnapshot();
            new Thread(new UIDrawerThread()).start();
            while (true) {
                if (uiState.isRunning()) {
                    double runSpeed = uiState.getRunSpeed();
                    int stepSize = (runSpeed > 4000) ? (int) runSpeed / 1000 : 1;

                    step(map, ui, stepSize);


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

    private class UIDrawerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                uiState.redraw();
            }
        }
    }

    private class PerformanceDiagnosis implements Runnable {
        private long lastEpoch = 0;

        @Override
        public void run() {
            if (lastEpoch == 0) {
                lastEpoch = map.mapStatistics.getEpoch();
            } else {
                long currentEpoch = map.mapStatistics.getEpoch();
                double measurementsPerSecond = (double) 1000 / MEASUREMENT_PERIOD_MILLIS;
                uiState.setRunsPerSecondMeasurement((currentEpoch - lastEpoch) * measurementsPerSecond);
                lastEpoch = currentEpoch;
            }

        }
    }
}
