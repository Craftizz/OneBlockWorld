package io.github.craftizz.oneblockworld.oneblock.settings;

import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;

public class DifficultySetting extends Setting {

    private final Difficulty difficulty;

    public DifficultySetting(final @NotNull Double cost,
                                final @NotNull Integer level,
                                final @NotNull Difficulty difficulty) {
        super(cost, level);
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
