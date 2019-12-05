package main.UI;

import javax.swing.*;
import java.awt.*;

class SettingsPanel extends JPanel {
    SettingsPanel(){
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

    private String sliderValue(JSlider slider){
        return Integer.toString(slider.getValue());
    }


}
