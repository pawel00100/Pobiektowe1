package main.UI;

import main.map.RectangularMap;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SettingsPanel extends JPanel {
    private RectangularMap map;

    private JLabel label;
    private JSlider slider;

    SettingsPanel(RectangularMap map){
        super();
        this.map = map;

        createComponents();

        this.add(this.slider);
        this.add(this.label);
        this.setPreferredSize(new Dimension(400,50));
    }

    private void createComponents(){
        createNewButton("run", e -> map.isRunning = true);
        createNewButton("stop", e -> map.isRunning = false);
        createNewButton("save", e -> map.mapStatistics.writeFile());
        createNewButton("Show most frequent", this::onClick);

        this.slider = new JSlider(0,100,0);
        this.label = new JLabel(sliderValue());

        this.slider.setMinorTickSpacing(10);
        this.slider.addChangeListener(this::stateChanged);
    }

    private void createNewButton(String text, ActionListener l){
        JButton button = new JButton(text);
        button.addActionListener(l);
        this.add(button);
    }

    private double sliderDoubleValue(){
        int input = this.slider.getValue(); //input 0 to 100
        return Math.pow(10, ( (double) input / 100.0 * 5)); //logarithmic slider 1 to 1000
    }

    private String sliderValue(){
        return String.format("%.1f", sliderDoubleValue());
    }

    private void stateChanged(ChangeEvent e)
    {
        this.label.setText( sliderValue());
        this.map.runSpeed = sliderDoubleValue();
    }

    private void onClick(ActionEvent e) {
        this.map.showMostFrequent = !this.map.showMostFrequent;
        this.map.mapStateChanged();

    }

}
