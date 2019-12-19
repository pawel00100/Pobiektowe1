package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;

import javax.swing.*;

public class StatusPanel extends JPanel implements IMapStateChangeObserver {
    private JLabel animalsOnMapLabel;
    private JLabel plantsOnMapLabel;
    private JLabel epochLabel;
    private JLabel averageEnergyLabel;
    private JLabel averageLifespan;
    private RectangularMap map;

    StatusPanel(RectangularMap map){
        super();
        this.map = map;
        this.map.addObserver(this);

        this.animalsOnMapLabel = new JLabel("Animals: 0");
        this.plantsOnMapLabel = new JLabel("       Plants: 0");
        this.epochLabel = new JLabel("       Epoch: 0");
        this.averageEnergyLabel = new JLabel("       Average Energy: 0");
        this.averageLifespan = new JLabel("       Average Lifespan: 0");
        this.add(animalsOnMapLabel);
        this.add(plantsOnMapLabel);
        this.add(epochLabel);
        this.add(averageEnergyLabel);
        this.add(averageLifespan);
    }

    @Override
    public void mapStateChanged() {
        this.animalsOnMapLabel.setText("Animals: " + this.map.getNumberOfAnimals());
        this.plantsOnMapLabel.setText("       Plants: " + this.map.getNumberOfPlants());
        this.epochLabel.setText("       Epoch: " + this.map.getEpoch());
        this.averageEnergyLabel.setText("       Average Energy: " + this.map.getAverageEnergy());
        this.averageLifespan.setText("       Average Lifespan: " + this.map.getAverageLifespan());
    }
}
