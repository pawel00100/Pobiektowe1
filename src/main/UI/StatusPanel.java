package main.UI;

import main.map.Redrawable;
import main.map.RectangularMap;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class StatusPanel extends JPanel implements Redrawable {
    private JLabel animalsOnMapLabel;
    private JLabel plantsOnMapLabel;
    private JLabel epochLabel;
    private JLabel rateLabel;
    private JLabel averageEnergyLabel;
    private JLabel averageLifespanLabel;
    private JLabel averageNumberOfChildrenLabel;
    private MapSnapshotHolder mapSnapshotHolder;
    private static final DecimalFormatSymbols decimalFormat = createDecimalFormatSymbols();

    StatusPanel(MapSnapshotHolder mapSnapshotHolder){
        super();
        this.mapSnapshotHolder = mapSnapshotHolder;

        createLabels();

        GridLayout grid = new GridLayout(2, 0);
        setLayout(grid);
    }

    private void createLabels(){
        animalsOnMapLabel = createLabel("Animals: 0");
        plantsOnMapLabel = createLabel("       Plants: 0");
        epochLabel = createLabel("       Epoch: 0");
        rateLabel = createLabel("       Rate: 0 / s");
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
    public Future<Void> redraw() {
        animalsOnMapLabel.setText("Animals: " + mapSnapshotHolder.getMapSnapshot().getStatistics().getNumberOfAnimals());
        plantsOnMapLabel.setText("       Plants: " + mapSnapshotHolder.getMapSnapshot().getStatistics().getNumberOfPlants());
        epochLabel.setText("       Epoch: " + getEpoch());
        rateLabel.setText("       Rate: " + (int) mapSnapshotHolder.uiState.getRunsPerSecondMeasurement() + " / s");
        averageEnergyLabel.setText("       Average Energy: " + mapSnapshotHolder.getMapSnapshot().getStatistics().getAverageEnergy());
        averageLifespanLabel.setText("       Average Lifespan: " + mapSnapshotHolder.getMapSnapshot().getStatistics().getAverageLifespan());
        averageNumberOfChildrenLabel.setText("       Average Children: " + formatToString(mapSnapshotHolder.getMapSnapshot().getStatistics().getAverageNumberOfChildren()));
        return CompletableFuture.completedFuture(null);
    }

    private String formatToString(double input){
        return String.format("%.2f", input);
    }

    private String getEpoch() {
        int epoch = mapSnapshotHolder.getMapSnapshot().getStatistics().getEpoch();
        return new DecimalFormat("###,###", decimalFormat).format(epoch);
    }

    private static DecimalFormatSymbols createDecimalFormatSymbols() {
        var decimalFormat = new DecimalFormatSymbols(Locale.FRENCH);
        decimalFormat.setGroupingSeparator(' ');
        return decimalFormat;
    }
}
