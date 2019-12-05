package main.UI;

import main.Vector2d;

import javax.swing.*;
import java.awt.*;

class MapPanel extends JPanel {
    private int maxSize = 400;
    private int widthInTiles;
    private int heightInTiles;
    private int tileSize;
    private Color gravel = new Color(200,150,100);
    private Color gravel2 = new Color(194,140,86);

    MapPanel(Vector2d lowerLeft, Vector2d upperRight){
        super();

        //this section in code makes sure that each tile is square and neither width or height is above maxSize
        this.widthInTiles = upperRight.x - lowerLeft.x + 1;
        this.heightInTiles = upperRight.y - lowerLeft.y + 1;
        double heightToWidthRatio = (double) heightInTiles / widthInTiles;

        if(heightToWidthRatio <= 1)
            this.tileSize = this.maxSize/ widthInTiles;
        else
            this.tileSize = this.maxSize/ heightInTiles;

        int width = widthInTiles * tileSize;
        int height = heightInTiles * tileSize;

        this.setPreferredSize(new Dimension(width, height));

//        repaint();

    }

    private void drawMap(Graphics gAbstract){
        Graphics2D g = (Graphics2D) gAbstract;
        drawBackground(g);
    }

    private void drawBackground(Graphics2D g){
        for (int i = 0; i < this.heightInTiles; i++)
            for (int j = 0; j< this.widthInTiles; j++){
                Color color;
                if( (i+j) % 2 == 0 )
                    color = this.gravel;
                else
                    color = this.gravel2;
                drawSquare(g, new Vector2d(this.tileSize*j, this.tileSize*i), this.tileSize, color);
            }
    }

    private void drawSquare(Graphics2D g, Vector2d position, int size, Color color){ //position of upper left corner
        g.setColor(color);
        g.fillRect(position.x, position.y ,size, size);
    }

    public void paint(Graphics G){
        drawMap(G);
//        G.fillRect(0, 0 , width, height);
    }

}
