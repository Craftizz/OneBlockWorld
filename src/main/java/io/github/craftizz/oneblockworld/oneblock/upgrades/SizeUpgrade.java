package io.github.craftizz.oneblockworld.oneblock.upgrades;

import org.jetbrains.annotations.NotNull;

public class SizeUpgrade extends Upgrade {

    private final int size;

    public SizeUpgrade(final @NotNull Integer level,
                       final @NotNull Double requirement,
                       final @NotNull Integer size) {
        super(level, requirement);
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
