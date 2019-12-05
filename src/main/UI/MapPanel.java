package main.UI;

import main.Vector2d;

import javax.swing.*;
import java.awt.*;

class MapPanel extends JPanel {
    MapPanel(Vector2d lowerLeft, Vector2d upperRight){
        super();
        repaint();
        this.setPreferredSize(new Dimension(440,440));

    }

    public void paint(Graphics G){
        G.fillRect(20,20,400,400);
    }
}
