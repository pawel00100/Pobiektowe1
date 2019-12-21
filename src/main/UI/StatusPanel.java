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

        createLabels();

        GridLayout grid = new GridLayout(2, 0);
        this.setLayout(grid);
    }

    private void createLabels(){
        this.animalsOnMapLabel = createLabel("Animals: 0");
        this.plantsOnMapLabel = createLabel("       Plants: 0");
        this.epochLabel = createLabel("       Epoch: 0");
        this.averageEnergyLabel = createLabel("       Average Energy: 0");
        this.averageLifespanLabel = createLabel("       Average Lifespan: 0");
        this.averageNumberOfChildrenLabel = createLabel("       Average Children: 0");
    }

    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        this.add(label);
        return label;
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
