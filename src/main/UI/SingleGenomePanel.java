package main.UI;

import main.map.Redrawable;
import main.map.RectangularMap;
import main.map.snapshots.MapSnapshotHolder;
import main.mapElements.Genome;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public   class SingleGenomePanel extends JPanel implements Redrawable {
    private RectangularMap map; //can be removed if observer changed
    private Genome genome;
    private Color[] geneColors = {Color.YELLOW, Color.BLUE,Color.GREEN, Color.GRAY, Color.RED, Color.WHITE, Color.ORANGE, Color.CYAN};
    private int height = 20;
    private int width = 400;
    private Supplier<Genome> genomeGetter;

    SingleGenomePanel(MapSnapshotHolder mapSnapshotHolder, Genome genome, Supplier<Genome> genomeGetter){
        super();
        map = mapSnapshotHolder.uiState.map;
        this.genome = genome;
        this.genomeGetter = genomeGetter;
        setPreferredSize(new Dimension(width, height+10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel(g);
    }

    private void drawPanel(Graphics gAbstract){
        Graphics2D g = (Graphics2D) gAbstract;

        if(genome == null)
            fill(g, Color.blue);
        else
            drawGenome(g);
    }

    private void drawGenome(Graphics2D g){
        for (int j = 0; j < 32; j++) {
            int gene = genome.getGene(j);
            drawSquare(g, new Vector2d(j, 0), geneColors[gene], new Vector2d(width / 32, height));
        }
    }

    private void fill(Graphics2D g, Color color){
        g.fillRect(0, 0, width, height);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color, Vector2d size) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * size.x;
        int yWindowPosition = tilePosition.y * size.y;
        g.fillRect(xWindowPosition, yWindowPosition, size.x, size.y);
    }

    @Override
    public Future<Void> redraw() {
        genome = genomeGetter.get();
        repaint();
        return CompletableFuture.completedFuture(null);
    }
}
