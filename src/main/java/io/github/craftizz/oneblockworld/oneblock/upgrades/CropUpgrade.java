package io.github.craftizz.oneblockworld.oneblock.upgrades;

import org.jetbrains.annotations.NotNull;

public class CropUpgrade extends Upgrade {

    private final int limit;

    public CropUpgrade(final @NotNull Integer level,
                          final @NotNull Double requirement,
                          final @NotNull Integer limit) {
        super(level, requirement);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
