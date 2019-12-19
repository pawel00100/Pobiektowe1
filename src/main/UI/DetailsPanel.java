package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;

import javax.swing.*;

public class DetailsPanel extends JPanel implements IMapStateChangeObserver {
    private JLabel animalsOnMapLabel;
    private JLabel numberOfChildrenLabel;
    private JLabel numberOfDescendantsLabel;
    private JLabel epochOfDeathLabel;
    private RectangularMap map;

    DetailsPanel(RectangularMap map){
        super();

        this.map = map;
        this.map.addObserver(this);

        this.animalsOnMapLabel = new JLabel("Energy: 0");
        this.numberOfChildrenLabel = new JLabel("       Children: 0");
        this.numberOfDescendantsLabel = new JLabel("        Descendants: 0");
        this.epochOfDeathLabel = new JLabel("        Time of Death: 0");
        this.add(animalsOnMapLabel);
        this.add(numberOfChildrenLabel);
        this.add(numberOfDescendantsLabel);
        this.add(epochOfDeathLabel);

    }

    @Override
    public void mapStateChanged() {
        if(this.map.chosenAnimal != null) {
            this.animalsOnMapLabel.setText("Energy: " + this.map.chosenAnimal.getEnergy());
            this.numberOfChildrenLabel.setText("Children: " + this.map.chosenAnimal.getNumberOfChildrenSinceChoosing());
            this.numberOfDescendantsLabel.setText("Descendants: " + this.map.chosenAnimal.getNumberOfDescendants());
            this.epochOfDeathLabel.setText("Time of Death: " + this.map.epochOfDeath);
        }
    }
}
