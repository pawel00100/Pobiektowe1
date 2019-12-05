package main.UI;

import main.Vector2d;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {

    public static void main(String[] args){
        new UI(new Vector2d(0,0), new Vector2d(20,10));
    }


    public UI(Vector2d lowerLeft, Vector2d upperRight){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("BoxLayout Example X_AXIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        SettingsPanel settingsPanel = new SettingsPanel();
        panel.add(settingsPanel);
        MapPanel mapPanel = new MapPanel(lowerLeft, upperRight);
        panel.add(mapPanel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }
//    public UI(){
//        //window properties
//        JFrame frame = new JFrame("");
//        frame.setSize(500, 500);
//        frame.setLayout(new GridLayout(0, 1));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        SettingsPanel settingsPanel = new SettingsPanel();
//        frame.add(settingsPanel);
//
//        MapPanel mapPanel = new MapPanel(new Vector2d(0,0), new Vector2d(10,10));
//        frame.add(mapPanel);
//
//        frame.pack();
//        frame.setVisible(true);
//    }


}
