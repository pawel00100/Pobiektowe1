package main.map.snapshots;

import main.mapElements.Genome;

public class AnimalSnapshot {

    private final int energy;
    private final int numberOfChildrenSinceChoosing;
    private final int numberOfDescendants;
    private final Genome genome;

    public AnimalSnapshot(int energy, int numberOfChildrenSinceChoosing, int numberOfDescendants, Genome genome) {
        this.energy = energy;
        this.numberOfChildrenSinceChoosing = numberOfChildrenSinceChoosing;
        this.numberOfDescendants = numberOfDescendants;
        this.genome = genome;
    }

    public int getEnergy() {
        return energy;
    }
    public int getNumberOfChildrenSinceChoosing() {
        return numberOfChildrenSinceChoosing;
    }
    public int getNumberOfDescendants() {
        return numberOfDescendants;
    }

    public Genome getGenome() {
        return genome;
    }
}
