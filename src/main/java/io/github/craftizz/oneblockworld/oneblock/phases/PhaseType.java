package io.github.craftizz.oneblockworld.oneblock.phases;

import org.jetbrains.annotations.NotNull;

public enum PhaseType {
    SUMMER("Summer", "phases/summer.yml"),
    AUTUMN("Autumn", "phases/autumn.yml"),
    SPRING("Spring", "phases/spring.yml"),
    WINTER("Winter", "phases/winter.yml");

    final String displayName;
    final String resourceName;

    PhaseType(final @NotNull String displayName,
              final @NotNull String resourceName) {
        this.displayName = displayName;
        this.resourceName = resourceName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
