package main.map.snapshots;

import main.UI.UIState;
import main.map.RectangularMap;

public class MapSnapshotHolder {

    RectangularMap map;
    public UIState uiState;


    public MapSnapshotHolder(RectangularMap map, Runnable redraw) {
        this.map = map;
        uiState = new UIState(map, redraw::run);
    }


}
