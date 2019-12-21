package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Genome;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class GenomePanel extends JPanel implements IMapStateChangeObserver {
    private RectangularMap map;
    private Color[] geneColors = {Color.YELLOW, Color.BLUE,Color.GREEN, Color.GRAY, Color.RED, Color.WHITE, Color.ORANGE, Color.CYAN};
    private int height = 550;
    private int width = 400;


    GenomePanel(RectangularMap map){
        super();
        this.map = map;
        this.map.addObserver(this);
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel(g);
    }

    private void drawPanel(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;
        List<Genome> l = new ArrayList<>();

        for (Animal animal : this.map.animalList)
            l.add(animal.getGenome());

        l.sort(Genome::compareTo);

        for (int i = 0; i < this.map.mapStatistics.getNumberOfAnimals(); i++)
            for (int j = 0; j < 32; j++){
                int gene = l.get(i).getGene(j);
                drawSquare(g, new Vector2d(j,i), this.geneColors[gene], new Vector2d(this.width/32,this.height/this.map.mapStatistics.getNumberOfAnimals()));
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
