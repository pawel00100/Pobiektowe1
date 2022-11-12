package main.UI;

import main.map.Redrawable;
import main.map.RectangularMap;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements Redrawable {
    private JLabel animalsOnMapLabel;
    private JLabel plantsOnMapLabel;
    private JLabel epochLabel;
    private JLabel averageEnergyLabel;
    private JLabel averageLifespanLabel;
    private JLabel averageNumberOfChildrenLabel;
    private RectangularMap map;

    StatusPanel(MapSnapshotHolder mapSnapshotHolder){
        super();
        map = mapSnapshotHolder.uiState.map;

        createLabels();

        GridLayout grid = new GridLayout(2, 0);
        setLayout(grid);
    }

    private void createLabels(){
        animalsOnMapLabel = createLabel("Animals: 0");
        plantsOnMapLabel = createLabel("       Plants: 0");
        epochLabel = createLabel("       Epoch: 0");
        averageEnergyLabel = createLabel("       Average Energy: 0");
        averageLifespanLabel = createLabel("       Average Lifespan: 0");
        averageNumberOfChildrenLabel = createLabel("       Average Children: 0");
    }

    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        add(label);
        return label;
    }

    @Override
    public void redraw() {
        animalsOnMapLabel.setText("Animals: " + map.mapStatistics.getNumberOfAnimals());
        plantsOnMapLabel.setText("       Plants: " + map.mapStatistics.getNumberOfPlants());
        epochLabel.setText("       Epoch: " + map.mapStatistics.getEpoch());
        averageEnergyLabel.setText("       Average Energy: " + map.mapStatistics.getAverageEnergy());
        averageLifespanLabel.setText("       Average Lifespan: " + map.mapStatistics.getAverageLifespan());
        averageNumberOfChildrenLabel.setText("       Average Children: " + formatToString(map.mapStatistics.getAverageNumberOfChildren()));
    }

    private String formatToString(double input){
        return String.format("%.2f", input);
    }
}
