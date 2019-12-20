package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements IMapStateChangeObserver {
    private JLabel animalsOnMapLabel;
    private JLabel plantsOnMapLabel;
    private JLabel epochLabel;
    private JLabel averageEnergyLabel;
    private JLabel averageLifespanLabel;
    private JLabel averageNumberOfChildrenLabel;
    private RectangularMap map;

    StatusPanel(RectangularMap map){
        super();
        this.map = map;
        this.map.addObserver(this);

        this.animalsOnMapLabel = new JLabel("Animals: 0");
        this.plantsOnMapLabel = new JLabel("       Plants: 0");
        this.epochLabel = new JLabel("       Epoch: 0");
        this.averageEnergyLabel = new JLabel("       Average Energy: 0");
        this.averageLifespanLabel = new JLabel("       Average Lifespan: 0");
        this.averageNumberOfChildrenLabel = new JLabel("       Average Children: 0");

        GridLayout grid = new GridLayout(2, 0);
        this.setLayout(grid);

        this.add(animalsOnMapLabel);
        this.add(plantsOnMapLabel);
        this.add(epochLabel);
        this.add(averageEnergyLabel);
        this.add(averageLifespanLabel);
        this.add(averageNumberOfChildrenLabel);
    }

    @Override
    public void mapStateChanged() {
        this.animalsOnMapLabel.setText("Animals: " + this.map.mapStatistics.getNumberOfAnimals());
        this.plantsOnMapLabel.setText("       Plants: " + this.map.mapStatistics.getNumberOfPlants());
        this.epochLabel.setText("       Epoch: " + this.map.mapStatistics.getEpoch());
        this.averageEnergyLabel.setText("       Average Energy: " + this.map.mapStatistics.getAverageEnergy());
        this.averageLifespanLabel.setText("       Average Lifespan: " + this.map.mapStatistics.getAverageLifespan());
        this.averageNumberOfChildrenLabel.setText("       Average Children: " + formatToString(this.map.mapStatistics.getAverageNumberOfChildren()));
    }

    private String formatToString(double input){
        return String.format("%.2f", input);
    }
}
