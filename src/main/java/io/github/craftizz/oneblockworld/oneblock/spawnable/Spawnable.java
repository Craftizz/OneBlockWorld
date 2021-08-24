package io.github.craftizz.oneblockworld.oneblock.spawnable;

import org.jetbrains.annotations.NotNull;

public abstract class Spawnable {

    private final Integer probability;

    public Spawnable(@NotNull final Integer probability) {
        this.probability = probability;
    }

    /**
     * @return the probability of spawning the spawnable
     */
    public final Integer getProbability() {
        return probability;
    }

}
