package io.github.craftizz.oneblockworld.oneblock.upgrades;

import org.jetbrains.annotations.NotNull;

public abstract class Upgrade {

    private final int level;
    private final double requirement;

    protected Upgrade(final @NotNull Integer level,
                      final @NotNull Double requirement) {
        this.level = level;
        this.requirement = requirement;
    }

    /**
     * @return the level of the upgrade
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the requirement for the upgrade. This
     * can be money or exp.
     */
    public double getRequirement() {
        return requirement;
    }
}
