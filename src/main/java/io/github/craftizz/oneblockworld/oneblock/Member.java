package io.github.craftizz.oneblockworld.oneblock;

import io.github.craftizz.oneblockworld.user.UserType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Member {

    private final UUID uniqueId;
    private UserType userType;

    public Member(final @NotNull UUID uniqueId,
                  final @NotNull UserType userType) {
        this.uniqueId = uniqueId;
        this.userType = userType;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(final @NotNull UserType userType) {
        this.userType = userType;
    }
}
