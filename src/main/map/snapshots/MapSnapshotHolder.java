package main.map.snapshots;

import main.UI.UIState;
import main.map.RectangularMap;
import main.map.statistics.MapInformation;

public class MapSnapshotHolder {

    RectangularMap map;
    public UIState uiState;
    MapInformation mapInformation;
    volatile MapSnapshot mapSnapshotReceived = null;
    volatile MapSnapshot mapSnapshot = null;


    public MapSnapshotHolder(RectangularMap map, Runnable redraw) {
        this.map = map;
        uiState = new UIState(map, () -> this.redraw(redraw));
        mapInformation = new MapInformation(map);
    }

    public void createSnapshot() {
        mapSnapshotReceived = MapSnapshot.fromMap(map, mapInformation, uiState);
        if (mapSnapshot == null) {
            mapSnapshot = mapSnapshotReceived;
        }
        uiState.signalReadyToDraw();
    }


    public MapInformation getMapInformation() {
        return mapInformation;
    }

    public MapSnapshot getMapSnapshot() {
        while (mapSnapshot == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return mapSnapshot;
    }

    public void redraw(Runnable redraw) {
        mapSnapshot = mapSnapshotReceived;
        redraw.run();
    }

}
