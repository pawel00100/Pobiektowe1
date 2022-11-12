package main.UI;

import main.map.RectangularMap;
import main.map.snapshots.MapSnapshotHolder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class SettingsPanel extends JPanel {
    private MapSnapshotHolder mapSnapshotHolder;
    private UIState uiState;


    private JLabel label;
    private JSlider slider;

    SettingsPanel(MapSnapshotHolder mapSnapshotHolder, UIState uiState) {
        super();
        this.mapSnapshotHolder = mapSnapshotHolder;
        this.uiState = uiState;

        createComponents();

        add(slider);
        add(label);
        setPreferredSize(new Dimension(500,50));
    }

    private void createComponents() {
        createNewButton("run", e -> uiState.setSimulationRunning(true));
        createNewButton("stop", e -> uiState.setSimulationRunning(false));
        createNewButton("save", e -> uiState.writeFile());
        createNewButton("Show most frequent", this::onMostFrequentButtonClick);

        slider = new JSlider(0,100,0);
        label = new JLabel(sliderValue());

        slider.setMinorTickSpacing(1);
        slider.addChangeListener(this::stateChanged);
    }

    private void createNewButton(String text, ActionListener l){
        JButton button = new JButton(text);
        button.addActionListener(l);
        add(button);
    }

    private double sliderDoubleValue(){
        int input = slider.getValue(); //input 0 to 100
        return Math.pow(10, ( (double) input / 100.0 * 6)); //logarithmic slider 1 to 100000
    }

    private String sliderValue(){
        return String.format("%.1f", sliderDoubleValue());
    }

    private void stateChanged(ChangeEvent e)
    {
        label.setText( sliderValue());
        uiState.setRunSpeed(sliderDoubleValue());
    }

    private void onMostFrequentButtonClick(ActionEvent e) {
        uiState.clickedOnMostFrequentButton();

    }

}
