package main.UI;

import main.map.RectangularMap;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    MapPanel mapPanel;
    public UI(RectangularMap map, JSONObject parameters){
//        JFrame.setDefaultLookAndFeelDecorated(true);
        try{UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e){};

        JFrame frame = new JFrame("Animal World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        SettingsPanel settingsPanel = new SettingsPanel(parameters);
        panel.add(settingsPanel);
        this.mapPanel = new MapPanel(map);
        panel.add(this.mapPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    public void mapRefresh(){
        //this.mapPanel
    }

}
