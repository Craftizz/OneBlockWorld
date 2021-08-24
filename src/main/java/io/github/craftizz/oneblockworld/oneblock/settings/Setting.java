package io.github.craftizz.oneblockworld.oneblock.settings;

import org.jetbrains.annotations.NotNull;

public abstract class Setting {

    private final double cost;
    private final int level;

    protected Setting(final @NotNull Double cost,
                      final @NotNull Integer level) {
        this.cost = cost;
        this.level = level;
    }

    /**
     * @return the cost of the current setting
     */
    public double getCost() {
        return cost;
    }

    /**
     * @return the level of the setting, 0 means locked.
     */
    public int getLevel() {
        return level;
    }
}
