package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Genome;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;

import java.util.function.Supplier;

public   class SingleGenomePanel extends JPanel implements IMapStateChangeObserver{
    RectangularMap map; //can be removed if observer changed
    Genome genome;
    Color[] geneColors = {Color.YELLOW, Color.BLUE,Color.GREEN, Color.GRAY, Color.RED, Color.WHITE, Color.ORANGE, Color.CYAN};
    int height = 20;
    int width = 400;
    Supplier<Genome> genomeGetter;

    SingleGenomePanel(RectangularMap map, Genome genome, Supplier<Genome> genomeGetter){
        super();
        this.map = map;
        this.map.addObserver(this);
        this.genome = genome;
        this.genomeGetter = genomeGetter;
        this.setPreferredSize(new Dimension(this.width, this.height+10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel(g);
    }

    private void drawPanel(Graphics gAbstract){
        Graphics2D g = (Graphics2D) gAbstract;

        if(this.genome == null)
            fill(g, Color.blue);
        else
            drawGenome(g);
    }

    private void drawGenome(Graphics2D g){
        for (int j = 0; j < 32; j++) {
            int gene = this.genome.getGene(j);
            drawSquare(g, new Vector2d(j, 0), this.geneColors[gene], new Vector2d(this.width / 32, this.height));
        }
    }

    private void fill(Graphics2D g, Color color){
        g.fillRect(0, 0, this.width, this.height);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color, Vector2d size) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * size.x;
        int yWindowPosition = tilePosition.y * size.y;
        g.fillRect(xWindowPosition, yWindowPosition, size.x, size.y);
    }

    @Override
    public void mapStateChanged() {
        this.genome = genomeGetter.get();
        repaint();
    }
}
