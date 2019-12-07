package main.UI;

import main.mapElements.Animal;
import main.mapElements.Grass;
import main.map.RectangularMap;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;

class MapPanel extends JPanel {
    private int widthInTiles;
    private int heightInTiles;
    private int tileSize;
    private Color gravel = new Color(200, 150, 100);
    private Color gravel2 = new Color(194, 140, 86);
    private Color animalColor = Color.DARK_GRAY;
    private Color grassColor = new Color(48, 159, 53);
    private RectangularMap map;

    MapPanel(RectangularMap map) {
        super();
        this.map = map;

        //this section in code makes sure that each tile is square and neither width or height is above maxSize
        this.widthInTiles = this.map.upperBoundary.x - this.map.lowerBoundary.x + 1;
        this.heightInTiles = this.map.upperBoundary.y - this.map.lowerBoundary.y + 1;
        double heightToWidthRatio = (double) this.heightInTiles / this.widthInTiles;

        int maxWindowSize = Math.min(360 + Math.max(widthInTiles, heightInTiles)*6, 1200); //scaling window size to the number of tiles with max limit
        if (heightToWidthRatio <= 1)
            this.tileSize = maxWindowSize / this.widthInTiles;
        else
            this.tileSize = maxWindowSize / this.heightInTiles;

        int width = this.widthInTiles * this.tileSize;
        int height = this.heightInTiles * this.tileSize;
        this.setPreferredSize(new Dimension(width, height));

//        repaint();

    }

    private void drawMap(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;
        drawBackground(g);
    }

    private void drawBackground(Graphics2D g) {
        for (int i = 0; i < this.heightInTiles; i++)
            for (int j = 0; j < this.widthInTiles; j++)
                drawObject(g, new Vector2d(j, i));
    }

    private void drawObject(Graphics2D g, Vector2d tilePosition) {
        Color color;
        if(map.isTileOccupied(tilePosition)){
            if (map.isAnimalOnTile(tilePosition))
                color = animalColor;
            else if (map.isGrassOnTile(tilePosition))
                color = grassColor;
            else
                color = Color.RED;
        }
        else if ((tilePosition.x + tilePosition.y) % 2 == 0)
            color = this.gravel;
        else
            color = this.gravel2;
        drawSquare(g, tilePosition, color);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * this.tileSize;
        int yWindowPosition = (this.heightInTiles - tilePosition.y - 1) * this.tileSize; //window is drawn from top to bottom, while tile position is Cartesian
        g.fillRect(xWindowPosition, yWindowPosition, this.tileSize, this.tileSize);
    }


    public void paint(Graphics G) {
        drawMap(G);
//        G.fillRect(0, 0 , width, height);
    }

}
