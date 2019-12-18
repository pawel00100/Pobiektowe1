package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;

class GenomePanel extends JPanel implements IMapStateChangeObserver {
    RectangularMap map;
    Color[] geneColors = {Color.YELLOW, Color.BLUE,Color.GREEN, Color.GRAY, Color.RED, Color.WHITE, Color.ORANGE, Color.CYAN};
    int height = 250;
    int width = 500;


    GenomePanel(RectangularMap map){
        this.map = map;
        this.map.addObserver(this);
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

//    @Override
    public void paint(Graphics G){
        G.clearRect(0,0,600,600);
        drawPanel(G);
    }

    private void drawPanel(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;
        for (int i = 0; i < this.map.getNumberOfAnimals(); i++)
            for (int j = 0; j < 32; j++){
                int gene = map.getAnimalByNumber(i).getGenome().getGene(j);
                drawSquare(g, new Vector2d(j,i), this.geneColors[gene], new Vector2d(this.width/32,this.height/this.map.getNumberOfAnimals()));
            }
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color, Vector2d size) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * size.x;
        int yWindowPosition = tilePosition.y * size.y;
        g.fillRect(xWindowPosition, yWindowPosition, size.x, size.y);
    }

    @Override
    public void mapStateChanged() {
        repaint();
    }
}
