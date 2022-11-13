package main.map;

import java.util.concurrent.Future;

public interface Redrawable {
    Future<Void> redraw();
}
