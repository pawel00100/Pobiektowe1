package main.UI;

import main.map.RectangularMap;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    RectangularMap map1, map2;
    JSONObject parameters;
    MapPanel mapPanel;
    public UI(RectangularMap map1, RectangularMap map2, JSONObject parameters){
        this.map1 = map1;
        this.map2 = map2;
        this.parameters = parameters;

//        JFrame.setDefaultLookAndFeelDecorated(true);
        try{UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());}
        catch (Exception ignored){};

        JFrame frame = new JFrame("Animal World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        frame.add(dualRunPanel());
        frame.pack();
        frame.setVisible(true);

    }

    private JPanel dualRunPanel(){
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxlayout);

        panel.add(singleRunPanel(map1));
        panel.add(singleRunPanel(map2));

        return panel;
    }

    private JPanel singleRunPanel(RectangularMap map){
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        panel.add(new SettingsPanel(this.parameters, map));
        panel.add(new MapPanel(map));
        panel.add(new StatusPanel(map));
        panel.add(new GenomePanel(map));
        panel.add(new DetailsPanel(map));

        return panel;
    }

    public void mapRefresh(){
        //this.mapPanel
    }

}
