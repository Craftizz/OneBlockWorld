package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserData {

    private final UUID uniqueId;
    private UserType memberType;

    public UserData(final @NotNull UUID uniqueId,
                    final @NotNull UserType memberType) {
        this.uniqueId = uniqueId;
        this.memberType = memberType;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setMemberType(UserType memberType) {
        this.memberType = memberType;
    }

    public UserType getMemberType() {
        return memberType;
    }
}
