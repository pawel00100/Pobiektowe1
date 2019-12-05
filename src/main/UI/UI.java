package main.UI;

import main.map.RectangularMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {

    public UI(RectangularMap map){
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

        SettingsPanel settingsPanel = new SettingsPanel();
        panel.add(settingsPanel);
        MapPanel mapPanel = new MapPanel(map);
        panel.add(mapPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

}
