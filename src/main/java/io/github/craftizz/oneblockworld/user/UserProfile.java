package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserProfile {

    private final Integer slot;
    private final UUID uniqueId;

    public UserProfile(final @NotNull Integer slot,
                       final @NotNull UUID uniqueId) {
        this.slot = slot;
        this.uniqueId = uniqueId;
    }

    public Integer getSlot() {
        return slot;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
