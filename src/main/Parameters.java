package main;

import org.json.JSONObject;

import java.io.IOException;

public class Parameters {
    private Parameters() {
    }

    public static final int STARTING_ENERGY = getObjInt("startEnergy");
    public static final int MOVE_ENERGY = getObjInt("moveEnergy");
    public static final int PLANTS_PER_TICK = getObjInt("plantsPerTick");
    public static final boolean WRITE_STATISTICS = getObj().getBoolean("saveHistory");

    private static int getObjInt(String name)  {
        return getObj().getInt(name);
    }

    private static JSONObject getObj()  {
        try {
            return new Parser().obj;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
