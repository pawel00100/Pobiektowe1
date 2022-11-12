package main.UI;
import main.map.RectangularMap;
import main.map.statistics.MapStatisticsTextHistory;
import main.mapElements.Animal;
import main.mapElements.Vector2d;

public class UIState {
    RectangularMap map;
    Runnable redraw;

    private boolean isRunning = true;
    private double runSpeed = 10;
    public final MapStatisticsTextHistory mapStatisticsTextHistory = new MapStatisticsTextHistory();

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

    public void redraw() {
        redraw.run();
    }
}
