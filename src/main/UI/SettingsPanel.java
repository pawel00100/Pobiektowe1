package main.UI;

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.image.BufferedImage;

class SettingsPanel extends JPanel {
    private JSONObject parameters;

    private JLabel label;
    private JSlider slider;

    SettingsPanel(JSONObject parameters){
        super();
        this.parameters = parameters;
        this.parameters.put("runSpeed",1.0);
        this.parameters.put("isRunning",true);

        JButton runButton=new JButton("run");
        JButton stopButton=new JButton("stop");


        this.slider = new JSlider(0,100,0);
        this.label = new JLabel(sliderValue());


        this.slider.setMinorTickSpacing(10);
        this.slider.addChangeListener(this::stateChanged);
        runButton.addActionListener(e -> this.parameters.put("isRunning",true));
        stopButton.addActionListener(e -> this.parameters.put("isRunning",false));
//        slider.addChangeListener(e -> label.setText( sliderValue((JSlider)e.getSource())));

        this.add(runButton);
        this.add(stopButton);
        this.add(this.slider);
        this.add(this.label);
        this.setPreferredSize(new Dimension(400,50));
    }

    private double sliderDoubleValue(){
        int input = this.slider.getValue(); //input 0 to 100
        return Math.pow(10, ( (double) input / 100.0 * 4)); //logarithmic slider 1 to 1000
    }

    private String sliderValue(){
        return String.format("%.1f", sliderDoubleValue());
    }

    private void stateChanged(ChangeEvent e)
    {
        this.label.setText( sliderValue());
        this.parameters.put("runSpeed", sliderDoubleValue());
    }

    private static ImageIcon createImageIcon(Color color, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect (0, 0, width, height);
        return new ImageIcon(image);
    }



}
