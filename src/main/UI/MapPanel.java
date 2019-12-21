package main.UI;

import main.map.IMapStateChangeObserver;
import main.map.RectangularMap;
import main.mapElements.Animal;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

class MapPanel extends JPanel implements IMapStateChangeObserver {

    private Vector2d sizeInTiles;
    private int tileSize;
    private RectangularMap map;

    RepaintManager r = new RepaintManager();
    private ArrayList<ArrayList<MyButton>> buttons = new ArrayList<>();

    static class MyButton extends JButton {
        final Vector2d mapPosition;

        MyButton(Vector2d mapPosition) {
            super();
            this.mapPosition = mapPosition;
        }
    }

    MapPanel(RectangularMap map) {
        super();
        this.map = map;
        this.map.addObserver(this);

        setDimensions();

        this.setLayout(new GridLayout(this.sizeInTiles.y, this.sizeInTiles.x));

        createButtons();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
    }

    @Override
    public void mapStateChanged() {
        repaint();
    }

    private void createButtons() {
        for (int i = 0; i < this.sizeInTiles.y; i++) {
            this.buttons.add(new ArrayList<>());
            for (int j = 0; j < this.sizeInTiles.x; j++) {
                MyButton button = createButton(new Vector2d(j, (this.sizeInTiles.y - i - 1)));
                this.buttons.get(i).add(button);
                this.add(button); //adds to panel
            }
        }
    }

    private MyButton createButton(Vector2d position){
        MyButton button = new MyButton(position);
        button.setPreferredSize(new Dimension(this.tileSize, this.tileSize));
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this::onClick);
        return button;
    }

    private void setDimensions(){ //makes sure that each tile is square and neither width or height is above maxSize

        int widthInTiles = this.map.upperBoundary.x - this.map.lowerBoundary.x + 1;
        int heightInTiles = this.map.upperBoundary.y - this.map.lowerBoundary.y + 1;
        this.sizeInTiles = new Vector2d(widthInTiles, heightInTiles);

        double heightToWidthRatio = (double) this.sizeInTiles.y / this.sizeInTiles.x;

        int maxWindowSize = Math.min(360 + Math.max(this.sizeInTiles.x, this.sizeInTiles.y) * 6, 750); //scaling window size to the number of tiles with max limit
        if (heightToWidthRatio <= 1)
            this.tileSize = maxWindowSize / this.sizeInTiles.x;
        else
            this.tileSize = maxWindowSize / this.sizeInTiles.y;

        int width = this.sizeInTiles.x * this.tileSize;
        int height = this.sizeInTiles.y * this.tileSize;
        this.setPreferredSize(new Dimension(width, height));
    }

    private void drawMap(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;
        drawBackground(g);
    }

    private void drawBackground(Graphics2D g) {
        for (int i = 0; i < this.sizeInTiles.y; i++)
            for (int j = 0; j < this.sizeInTiles.x; j++)
                drawObject(g, new Vector2d(j, i));
    }

    private void drawObject(Graphics2D g, Vector2d tilePosition) {
        Color color = chooseColor(tilePosition);

        drawSquare(g, tilePosition, color);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * this.tileSize;
        int yWindowPosition = (this.sizeInTiles.y - tilePosition.y - 1) * this.tileSize; //window is drawn from top to bottom, while tile position is Cartesian
        g.fillRect(xWindowPosition, yWindowPosition, this.tileSize, this.tileSize);
    }

    private void onClick(ActionEvent e) {
        Vector2d mapPosition = ((MyButton) e.getSource()).mapPosition;
        if (!this.map.isAnimalOnTile(mapPosition)) {
            this.map.chosenAnimal = null;
            return;
        }
        Animal chosenAnimal = this.map.getTile(mapPosition).getStrongestAnimal();
        chosenAnimal.setTracked();
        this.map.chosenAnimal = chosenAnimal;
        this.map.epochOfDeath = 0;
        this.map.isChosenAnimalAlive = true;

        this.map.mapStateChanged();
    }

    private boolean hasMostFrequentGenome(Vector2d tilePosition) {
        return this.map.mapStatistics.isMostFrequentGenome( this.map.getTile(tilePosition).getStrongestAnimal().getGenome());
    }

    private Color chooseColor(Vector2d tilePosition) {
        Color color;
        if (this.map.isTileOccupied(tilePosition)) {
            if (this.map.chosenAnimal != null && this.map.isChosenAnimalAlive && tilePosition.equals(this.map.chosenAnimal.getPosition()))
                color = ColorScheme.chosenAnimalColor;

            else if (this.map.isAnimalOnTile(tilePosition)) {
                if (this.map.showMostFrequent && hasMostFrequentGenome(tilePosition))
                    color = ColorScheme.mostFrequentGenome;
                else
                    color = colorBasedOnEnergy(this.map.getTile(tilePosition).getStrongestAnimal());
            } else if (this.map.isGrassOnTile(tilePosition))
                color = ColorScheme.grassColor;
            else
                color = Color.RED;
        } else if (this.map.isTileJungle(tilePosition)) {
            if ((tilePosition.x + tilePosition.y) % 2 == 0)
                color = ColorScheme.jungle1;
            else
                color = ColorScheme.jungle2;
        } else {
            if ((tilePosition.x + tilePosition.y) % 2 == 0)
                color = ColorScheme.gravel;
            else
                color = ColorScheme.gravel2;
        }

        return color;
    }

    private Color colorBasedOnEnergy(Animal animal) {
        int energy = animal.getEnergy();
        int color = (int) (energy * 2.5);
        int color2 = energy / 2;
        return new Color(color2, color2, color);
    }

}
