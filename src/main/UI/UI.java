package main.UI;

import main.Vector2d;

import javax.swing.*;

public class UI {

    public static void main(String[] args){
        new UI();
    }


    public UI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("BoxLayout Example X_AXIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        SettingsPanel settingsPanel = new SettingsPanel();
        panel.add(settingsPanel);
        MapPanel mapPanel = new MapPanel(new Vector2d(0,0), new Vector2d(10,10));
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
