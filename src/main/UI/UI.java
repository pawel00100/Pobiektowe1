package main.UI;

import main.map.RectangularMap;
import main.map.Redrawable;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UI {
    private RectangularMap map;

    public MapSnapshotHolder mapSnapshotHolder;
    public UIState uiState;
    private final List<Redrawable> redrawables = new ArrayList<>();


    public UI(RectangularMap map) {
        mapSnapshotHolder = new MapSnapshotHolder(map, this::redraw);
        uiState = mapSnapshotHolder.uiState;

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JFrame frame = new JFrame("Animal World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(singleRunPanel());
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel leftPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        var settingsPanel = new SettingsPanel(mapSnapshotHolder, uiState);
        var mapPanel = new MapPanel(mapSnapshotHolder, uiState);
        var statusPanel = new StatusPanel(mapSnapshotHolder);
        var singleGenomePanel = new SingleGenomePanel(mapSnapshotHolder, null, () -> mapSnapshotHolder.getMapSnapshot().mapStatistics.mostFrequentGenome());

        panel.add(settingsPanel);
        panel.add(mapPanel);
        panel.add(statusPanel);
        panel.add(singleGenomePanel);

        redrawables.addAll(List.of(statusPanel, mapPanel, singleGenomePanel));

        return panel;
    }

    private JPanel rightPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        var genomePanel = new GenomePanel(mapSnapshotHolder);
        var detailsPanel = new DetailsPanel(mapSnapshotHolder);

        panel.add(genomePanel);
        panel.add(detailsPanel);

        redrawables.addAll(List.of(genomePanel, detailsPanel));

        return panel;
    }

    private JPanel singleRunPanel() {
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        panel.setLayout(boxlayout);

        panel.add(leftPanel());
        panel.add(rightPanel());

        return panel;
    }

    public void redraw() {
        redrawables.stream()
                .map(Redrawable::redraw)
                .forEach(f -> {
                    try {
                        f.get();
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
