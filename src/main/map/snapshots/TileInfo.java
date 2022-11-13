package main.map.snapshots;

public interface TileInfo {

    public static class AnimalOnTile implements TileInfo {
        final int energy;

        public AnimalOnTile(int energy) {
            this.energy = energy;
        }

        public int getEnergy() {
            return energy;
        }
    }

    public static class JungleTile implements TileInfo {
    }

    public static class GrassTile implements TileInfo {
    }

    public static class GravelTile implements TileInfo {
    }

    public static class ChosenAnimalTile implements TileInfo {
    }

    public static class MostFrequentGenome implements TileInfo {
    }

    public static final JungleTile JUNGLE_TILE = new JungleTile();
    public static final GrassTile GRASS_TILE = new GrassTile();
    public static final GravelTile GRAVEL_TILE = new GravelTile();
    public static final ChosenAnimalTile CHOSEN_ANIMAL_TILE = new ChosenAnimalTile();
    public static final MostFrequentGenome MOST_FREQUENT_GENOME = new MostFrequentGenome();
}
