package main.UI;
import main.map.RectangularMap;
import main.map.statistics.MapStatisticsTextHistory;
import main.mapElements.Animal;
import main.mapElements.Vector2d;

import java.util.concurrent.atomic.AtomicBoolean;

public class UIState {
    RectangularMap map;
    Runnable redraw;

    private boolean isRunning = true;
    private double runSpeed = 10;
    private double runsPerSecondMeasurement = 0;
    public final MapStatisticsTextHistory mapStatisticsTextHistory = new MapStatisticsTextHistory();
    private final AtomicBoolean drawing = new AtomicBoolean(false);
    private final AtomicBoolean readyForDraw = new AtomicBoolean(false);
    private volatile long lastDraw = 0;
    private final Object monitor = new Object();

    private boolean showMostFrequent = false;
    private Animal chosenAnimal = null;
    private boolean isChosenAnimalAlive = false; //not sure if needed
    private int epochOfDeath = 0;

    public UIState(RectangularMap map, Runnable redraw) {
        this.map = map;
        this.redraw = redraw;
        map.setOnAnimalDeath(this::onAnimalDeath);
        map.uiStateTemp = this;
    }

    public void clicked(Vector2d position){
        if (!map.isAnimalOnTile(position)) {
            chosenAnimal = null;
            return;
        }
        chosenAnimal = map.getTile(position).getStrongestAnimal();
        chosenAnimal.setTracked();
        epochOfDeath = 0;
        isChosenAnimalAlive = true;

        //redraw(); //TODO: temp disabling but will need fixing to work when paused
    }

    public void clickedOnMostFrequentButton(){
        showMostFrequent = !showMostFrequent;
        //redraw(); //TODO: temp disabling but will need fixing to work when paused
    }

    public void setSimulationRunning(boolean newValue) {
        isRunning = newValue;
    }

    public void writeFile(){
        mapStatisticsTextHistory.writeFile();
    }

    public void setRunSpeed(double runSpeed) {
        this.runSpeed = runSpeed;
    }

    public Animal getChosenAnimal() {
        return chosenAnimal;
    }

    public boolean isShowMostFrequent() {
        return showMostFrequent;
    }

    public boolean isChosenAnimalAlive() {
        return isChosenAnimalAlive;
    }

    public int getEpochOfDeath() {
        return epochOfDeath;
    }

    public double getRunSpeed() {
        return runSpeed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void onAnimalDeath(Animal animal) {
        if(chosenAnimal == animal){
            isChosenAnimalAlive = false;
            epochOfDeath = map.mapStatistics.getEpoch();
        }
    }

    public double getRunsPerSecondMeasurement() {
        return runsPerSecondMeasurement;
    }

    public void setRunsPerSecondMeasurement(double runsPerSecondMeasurement) {
        this.runsPerSecondMeasurement = runsPerSecondMeasurement;
    }

    public boolean readyForNextFrame() {
        synchronized (monitor) {
            monitor.notifyAll();
        }
        return !drawing.get() && (System.currentTimeMillis() - lastDraw) > (1000/60);
    }

    public void signalReadyToDraw() {
        readyForDraw.set(true);
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public void redraw() {
        drawing.set(true);

        redraw.run();

        drawing.set(false);
        lastDraw = System.currentTimeMillis();

        synchronized (monitor) {
            while (!readyForDraw.get()) {
                try {
                    if (lastDraw != 0){
                        monitor.wait();
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            readyForDraw.set(false);
            monitor.notifyAll();
        }
    }
}
