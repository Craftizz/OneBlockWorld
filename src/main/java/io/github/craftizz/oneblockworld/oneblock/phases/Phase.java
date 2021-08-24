package io.github.craftizz.oneblockworld.oneblock.phases;

import io.github.craftizz.oneblockworld.oneblock.spawnable.Spawnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Phase {

    private final PhaseType phaseType;
    private final List<Spawnable> spawnables;
    private final Random random;

    private int totalSum;

    public Phase(final @NotNull PhaseType phaseType) {
        this.phaseType = phaseType;
        this.spawnables = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Adds a spawnable to the spawnable list and computes
     * the new total sum for probability
     *
     * @param spawnable the spawnable to be added
     */
    public void addSpawnable(final @NotNull Spawnable spawnable) {
        totalSum += spawnable.getProbability();
        spawnables.add(spawnable);
    }

    /**
     * Get a random spawnable respecting the probability
     *
     * @return a spawnable to be spawned
     */
    public Spawnable getRandomSpawnable() {

        int index = random.nextInt(totalSum);

        int sum = 0;
        int i = 0;

        while (sum < index) {
            sum = sum + spawnables.get(i++).getProbability();
        }

        return spawnables.get(Math.max(0, i-1));
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }
}
