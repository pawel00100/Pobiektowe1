package main.UI;

import main.map.Redrawable;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
    public Future<Void> redraw() {
        if (mapSnapshotHolder.getMapSnapshot().getChosenAnimal() != null) {
            animalsOnMapLabel.setText("Energy: " + mapSnapshotHolder.getMapSnapshot().getChosenAnimal().getEnergy());
            numberOfChildrenLabel.setText("Children: " + mapSnapshotHolder.getMapSnapshot().getChosenAnimal().getNumberOfChildrenSinceChoosing());
            numberOfDescendantsLabel.setText("Descendants: " + mapSnapshotHolder.getMapSnapshot().getChosenAnimal().getNumberOfDescendants());
            epochOfDeathLabel.setText("Time of Death: " + mapSnapshotHolder.getMapSnapshot().getEpochOfDeath());
        }
        return CompletableFuture.completedFuture(null);
    }
}
