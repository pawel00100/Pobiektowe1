package main.UI;

import main.map.Redrawable;
import main.map.RectangularMap;
import main.map.snapshots.MapSnapshotHolder;
import main.mapElements.Animal;
import main.mapElements.Genome;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class GenomePanel extends JPanel implements Redrawable {
    private Color[] geneColors = {Color.YELLOW, Color.BLUE,Color.GREEN, Color.GRAY, Color.RED, Color.WHITE, Color.ORANGE, Color.CYAN};
    private int height = 550;
    private int width = 400;
    private MapSnapshotHolder mapSnapshotHolder;

    private CompletableFuture<Void>  completableFuture;

    GenomePanel(MapSnapshotHolder mapSnapshotHolder){
        super();
        this.mapSnapshotHolder = mapSnapshotHolder;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel(g);
        if ( completableFuture != null) {
            completableFuture.complete(null);
        }
    }

    private void drawPanel(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;

        var snapshot = mapSnapshotHolder.getMapSnapshot();

        List<Genome> genomes = snapshot.getSortedGenomeList();

        for (int i = 0; i < snapshot.getNumberOfAnimals(); i++)
            for (int j = 0; j < 32; j++) {
                int gene = genomes.get(i).getGene(j);
                drawSquare(g, new Vector2d(j, i), geneColors[gene], new Vector2d(width / 32, height / snapshot.getNumberOfAnimals()));
            }
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color, Vector2d size) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * size.x;
        int yWindowPosition = tilePosition.y * size.y;
        g.fillRect(xWindowPosition, yWindowPosition, size.x, size.y);
    }

    @Override
    public Future<Void> redraw() {
        repaint();
        completableFuture = new CompletableFuture<>();
        return completableFuture;
    }
}
