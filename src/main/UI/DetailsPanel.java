package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;

import javax.swing.*;

public class DetailsPanel extends JPanel implements IMapStateChangeObserver {
    private JLabel animalsOnMapLabel;
    private RectangularMap map;

    DetailsPanel(RectangularMap map){
        super();

        this.map = map;
        this.map.addObserver(this);

        this.animalsOnMapLabel = new JLabel("Energy: 0");
        this.add(animalsOnMapLabel);

    }

    @Override
    public void mapStateChanged() {
        if(this.map.chosenAnimal != null) {
            this.animalsOnMapLabel.setText("Energy: " + this.map.chosenAnimal.getEnergy());
        }
    }
}
