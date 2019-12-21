package main.UI;

import main.map.RectangularMap;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    RectangularMap map;
    JSONObject parameters;
    MapPanel mapPanel;
    public UI(RectangularMap map, JSONObject parameters){
        this.map = map;
        this.parameters = parameters;

//        JFrame.setDefaultLookAndFeelDecorated(true);
        try{UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());}
        catch (Exception ignored){};

        JFrame frame = new JFrame("Animal World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        frame.add(singleRunPanel());
        frame.pack();
        frame.setVisible(true);

    }


    private JPanel singleRunPanel(){
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        panel.add(new SettingsPanel(map));
        panel.add(new MapPanel(map));
        panel.add(new StatusPanel(map));
        panel.add(new SingleGenomePanel(map, null,() -> map.mapStatistics.mostFrequentGenome()));

        panel.add(new GenomePanel(map));
        panel.add(new DetailsPanel(map));

        return panel;
    }

    public void mapRefresh(){
        //this.mapPanel
    }

}
