package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class MapPanel extends JPanel implements IMapStateChangeObserver {

    class myButton extends JButton{
        public final Vector2d mapPosition;
        myButton(Vector2d mapPosition){
            super();
            this.mapPosition = mapPosition;
        }
    }

    private int widthInTiles;
    private int heightInTiles;
    private int tileSize;
    private Color gravel = new Color(200, 150, 100);
    private Color gravel2 = new Color(194, 140, 86);
    private Color jungle1 = new Color(136, 161, 73);
    private Color jungle2 = new Color(130, 156, 71);
    private Color animalColor = Color.DARK_GRAY;
    private Color grassColor = new Color(2, 172, 24);
    private Color chosenAnimalColor = Color.RED;
    private RectangularMap map;

    RepaintManager r = new RepaintManager();
    private ArrayList<ArrayList<myButton>> buttons = new ArrayList<>();

    MapPanel(RectangularMap map) {
        super();
        this.map = map;
        this.map.addObserver(this);

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

        GridLayout grid = new GridLayout(this.heightInTiles, this.widthInTiles);
        this.setLayout(grid);

        createButtons();
        repaint();

    }

    private void createButtons(){
        for (int i = 0; i < this.heightInTiles; i++){
            this.buttons.add(new ArrayList<>());
            for (int j = 0; j < this.widthInTiles; j++) {
                myButton b = new myButton(new Vector2d(j, (this.heightInTiles - i - 1)));
                b.setPreferredSize(new Dimension(this.tileSize,this.tileSize));
                b.setBorder(null);
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.addActionListener(this::onClick);
                this.buttons.get(i).add(b);
                this.add(b);
            }
        }
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
            if(this.map.chosenAnimal != null && tilePosition.equals(this.map.chosenAnimal.getPosition()))
                color = this.chosenAnimalColor;
            else if (map.isAnimalOnTile(tilePosition))
                color = animalColor;
            else if (map.isGrassOnTile(tilePosition))
                color = grassColor;
            else
                color = Color.RED;
        }
        else if(this.map.isTileJungle(tilePosition)){
            if ((tilePosition.x + tilePosition.y) % 2 == 0)
                color = this.jungle1;
            else
                color = this.jungle2;
        }
        else{
            if ((tilePosition.x + tilePosition.y) % 2 == 0)
                color = this.gravel;
            else
                color = this.gravel2;
        }

        drawSquare(g, tilePosition, color);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * this.tileSize;
        int yWindowPosition = (this.heightInTiles - tilePosition.y - 1) * this.tileSize; //window is drawn from top to bottom, while tile position is Cartesian
        g.fillRect(xWindowPosition, yWindowPosition, this.tileSize, this.tileSize);
    }

    private void onClick(ActionEvent e){
        Vector2d mapPosition = ((myButton) e.getSource()).mapPosition;
        if(!this.map.isAnimalOnTile(mapPosition)){
            this.map.chosenAnimal = null;
            return;
        }
        Animal chosenAnimal = (Animal) this.map.getTile(mapPosition).getElementsByEnergy().get(0);
        this.map.chosenAnimal = chosenAnimal;
        this.map.isChosenAnimalAlive = true;

        mapStateChanged();
    }


    public void paint(Graphics G) {
        drawMap(G);
//        G.fillRect(0, 0 , width, height);
    }

    @Override
    public void mapStateChanged() {
        repaint();
    }
}
