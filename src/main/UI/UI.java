package main.UI;

import main.map.RectangularMap;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    private RectangularMap map;
    public UI(RectangularMap map){
        this.map = map;

        try{UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());}
        catch (Exception ignored){}

        JFrame frame = new JFrame("Animal World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(singleRunPanel());
        frame.pack();
        frame.setVisible(true);

    }

    private JPanel leftPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        panel.add(new SettingsPanel(this.map));
        panel.add(new MapPanel(this.map));
        panel.add(new StatusPanel(this.map));
        panel.add(new SingleGenomePanel(this.map, null,() -> this.map.mapStatistics.mostFrequentGenome()));

        return panel;
    }

    private JPanel rightPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));

        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        panel.add(new GenomePanel(this.map));
        panel.add(new DetailsPanel(this.map));

        return panel;
    }

    private JPanel singleRunPanel(){
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        panel.setLayout(boxlayout);

        panel.add(leftPanel());
        panel.add(rightPanel());

        return panel;
    }

}
