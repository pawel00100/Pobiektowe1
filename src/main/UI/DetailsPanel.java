package main.UI;

import main.map.Redrawable;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;

public class DetailsPanel extends JPanel implements Redrawable {
    private JLabel animalsOnMapLabel;
    private JLabel numberOfChildrenLabel;
    private JLabel numberOfDescendantsLabel;
    private JLabel epochOfDeathLabel;
    private MapSnapshotHolder mapSnapshotHolder;

    DetailsPanel(MapSnapshotHolder mapSnapshotHolder){
        super();

        this.mapSnapshotHolder = mapSnapshotHolder;

        createLabels();

        JPanel labelPanel = createLabelPanel();

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
        add(labelPanel);
        add(new SingleGenomePanel(mapSnapshotHolder, null, () -> (mapSnapshotHolder.uiState.getChosenAnimal() != null) ? mapSnapshotHolder.uiState.getChosenAnimal().getGenome() : null));

    }

    private void createLabels() {
        animalsOnMapLabel = new JLabel("Energy: 0");
        numberOfChildrenLabel = new JLabel("       Children: 0");
        numberOfDescendantsLabel = new JLabel("        Descendants: 0");
        epochOfDeathLabel = new JLabel("        Time of Death: 0");
    }

    private JPanel createLabelPanel() {
        JPanel panel = new JPanel();

        panel.add(animalsOnMapLabel);
        panel.add(numberOfChildrenLabel);
        panel.add(numberOfDescendantsLabel);
        panel.add(epochOfDeathLabel);

        return panel;
    }

    @Override
    public void redraw() {
        if(mapSnapshotHolder.uiState.getChosenAnimal() != null) {
            animalsOnMapLabel.setText("Energy: " + mapSnapshotHolder.uiState.getChosenAnimal().getEnergy());
            numberOfChildrenLabel.setText("Children: " + mapSnapshotHolder.uiState.getChosenAnimal().getNumberOfChildrenSinceChoosing());
            numberOfDescendantsLabel.setText("Descendants: " + mapSnapshotHolder.uiState.getChosenAnimal().getNumberOfDescendants());
            epochOfDeathLabel.setText("Time of Death: " + mapSnapshotHolder.uiState.getEpochOfDeath());
        }
    }
}
