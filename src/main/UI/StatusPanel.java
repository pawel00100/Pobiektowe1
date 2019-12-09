package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;

import javax.swing.*;

public class StatusPanel extends JPanel implements IMapStateChangeObserver {
    private JLabel animalsOnMapLabel;
    private JLabel plantsOnMapLabel;
    private RectangularMap map;

    StatusPanel(RectangularMap map){
        super();
        this.map = map;
        this.map.addObserver(this);

        this.animalsOnMapLabel = new JLabel("Animals: 0");
        this.plantsOnMapLabel = new JLabel("       Plants: 0");
        this.add(animalsOnMapLabel);
        this.add(plantsOnMapLabel);
    }

    @Override
    public void mapStateChanged() {
        this.animalsOnMapLabel.setText("Animals: " + this.map.numberOfAnimals);
        this.plantsOnMapLabel.setText("       Plants: " + this.map.numberOfPlants);
    }
}
