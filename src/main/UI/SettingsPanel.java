package main.UI;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

class SettingsPanel extends JPanel {
    SettingsPanel(JSONObject parameters){
        super();
        JButton runButton=new JButton("run");
        JButton stopButton=new JButton("stop");
        JSlider slider = new JSlider(1,100,50);
        JLabel label = new JLabel(sliderValue(slider));


        slider.setMinorTickSpacing(10);
        slider.addChangeListener(e -> label.setText( sliderValue((JSlider)e.getSource())));

        this.add(runButton);
        this.add(stopButton);
        this.add(slider);
        this.add(label);
        this.setPreferredSize(new Dimension(300,50));
    }

    private double silderDoubleValue(JSlider slider){
        int input = slider.getValue(); //input 0 to 100
        return Math.pow(10, ( (double) input / 50 - 1)); //logarithmic slider 0.1 to 10
    }

    private String sliderValue(JSlider slider){
        return String.format("%.1f", silderDoubleValue(slider));

    }


}
