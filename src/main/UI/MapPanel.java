package main.UI;

import main.map.Redrawable;
import main.map.snapshots.MapSnapshotHolder;
import main.map.snapshots.TileInfo;
import main.mapElements.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class MapPanel extends JPanel implements Redrawable {

    private Vector2d sizeInTiles;
    private int tileSize;
    private MapSnapshotHolder mapSnapshotHolder;
    private UIState uiState;

    private CompletableFuture<Void> completableFuture;

    private ArrayList<ArrayList<MyButton>> buttons = new ArrayList<>();

    static class MyButton extends JButton {
        final Vector2d mapPosition;

        MyButton(Vector2d mapPosition) {
            super();
            this.mapPosition = mapPosition;
        }
    }

    MapPanel(MapSnapshotHolder mapSnapshotHolder, UIState uiState) {
        super();
        this.mapSnapshotHolder = mapSnapshotHolder;
        this.uiState = uiState;
        setDimensions();

        setLayout(new GridLayout(sizeInTiles.y, sizeInTiles.x));

        createButtons();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            drawMap(g);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (completableFuture != null) {
            completableFuture.complete(null);
        }
    }

    @Override
    public Future<Void> redraw() {
        repaint();

        completableFuture = new CompletableFuture<>();
        return completableFuture;
    }

    private void createButtons() {
        for (int i = 0; i < sizeInTiles.y; i++) {
            buttons.add(new ArrayList<>());
            for (int j = 0; j < sizeInTiles.x; j++) {
                MyButton button = createButton(new Vector2d(j, (sizeInTiles.y - i - 1)));
                buttons.get(i).add(button);
                add(button); //adds to panel
            }
        }
    }

    private MyButton createButton(Vector2d position) {
        MyButton button = new MyButton(position);
        button.setPreferredSize(new Dimension(tileSize, tileSize));
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this::onClick);
        return button;
    }

    private void setDimensions() { //makes sure that each tile is square and neither width or height is above maxSize

        var upperBoundary = mapSnapshotHolder.getMapInformation().getUpperBoundary();
        var lowerBoundary = mapSnapshotHolder.getMapInformation().getLowerBoundary();
        int widthInTiles = upperBoundary.x - lowerBoundary.x + 1;
        int heightInTiles = upperBoundary.y - lowerBoundary.y + 1;
        sizeInTiles = new Vector2d(widthInTiles, heightInTiles);

        double heightToWidthRatio = (double) sizeInTiles.y / sizeInTiles.x;

        int maxWindowSize = Math.min(360 + Math.max(sizeInTiles.x, sizeInTiles.y) * 6, 850); //scaling window size to the number of tiles with max limit
        if (heightToWidthRatio <= 1)
            tileSize = maxWindowSize / sizeInTiles.x;
        else
            tileSize = maxWindowSize / sizeInTiles.y;

        int width = sizeInTiles.x * tileSize;
        int height = sizeInTiles.y * tileSize;
        setPreferredSize(new Dimension(width, height));
    }

    private void drawMap(Graphics gAbstract) {
        Graphics2D g = (Graphics2D) gAbstract;
        drawBackground(g);
    }

    private void drawBackground(Graphics2D g) {
        for (int i = 0; i < sizeInTiles.y; i++)
            for (int j = 0; j < sizeInTiles.x; j++)
                drawObject(g, new Vector2d(j, i));
    }

    private void drawObject(Graphics2D g, Vector2d tilePosition) {
        Color color = chooseColor(tilePosition);

        drawSquare(g, tilePosition, color);
    }

    private void drawSquare(Graphics2D g, Vector2d tilePosition, Color color) {
        g.setColor(color);
        int xWindowPosition = tilePosition.x * tileSize;
        int yWindowPosition = (sizeInTiles.y - tilePosition.y - 1) * tileSize; //window is drawn from top to bottom, while tile position is Cartesian
        g.fillRect(xWindowPosition, yWindowPosition, tileSize, tileSize);
    }

    private void onClick(ActionEvent e) {
        Vector2d mapPosition = ((MyButton) e.getSource()).mapPosition;
        uiState.clicked(mapPosition);
    }


    private Color chooseColor(Vector2d tilePosition) {
        Color color = ColorScheme.red;

        TileInfo tileInfo = mapSnapshotHolder.getMapSnapshot().getTileInfoMap().get(tilePosition);

        if (tileInfo instanceof TileInfo.ChosenAnimalTile) {
            color = ColorScheme.chosenAnimalColor;
        } else if (tileInfo instanceof TileInfo.AnimalOnTile a) {
            color = colorBasedOnEnergy(a.getEnergy());
        } else if (tileInfo instanceof TileInfo.MostFrequentGenome) {
            color = ColorScheme.mostFrequentGenome;
        } else if (tileInfo instanceof TileInfo.GrassTile) {
            color = ColorScheme.grassColor;
        } else if (tileInfo instanceof TileInfo.JungleTile) {
            if ((tilePosition.x + tilePosition.y) % 2 == 0) {
                color = ColorScheme.jungle1;
            } else {
                color = ColorScheme.jungle2;
            }
        } else if (tileInfo instanceof TileInfo.GravelTile) {
            if ((tilePosition.x + tilePosition.y) % 2 == 0) {
                color = ColorScheme.gravel;
            } else {
                color = ColorScheme.gravel2;
            }
        }
        return color;
    }

    private Color colorBasedOnEnergy(int energy) {
        int color = Math.min(energy * 2, 255);
        int color2 = color / 5;
        return new Color(color2, color2, color);
    }

}
