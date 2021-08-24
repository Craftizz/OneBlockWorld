package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserProfile {

    private final Integer slot;
    private final UUID oneBlockUniqueId;

    public UserProfile(final @NotNull Integer slot,
                       final @NotNull UUID oneBlockUniqueId) {
        this.slot = slot;
        this.oneBlockUniqueId = oneBlockUniqueId;
    }

    public Integer getSlot() {
        return slot;
    }

    public UUID getOneBlockUniqueId() {
        return oneBlockUniqueId;
    }
}
