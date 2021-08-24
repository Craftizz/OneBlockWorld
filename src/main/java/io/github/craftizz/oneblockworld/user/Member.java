package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Member {

    private final UUID uniqueId;
    private final MemberType memberType;

    public Member(final @NotNull UUID uniqueId,
                  final @NotNull MemberType memberType) {
        this.uniqueId = uniqueId;
        this.memberType = memberType;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public MemberType getMemberType() {
        return memberType;
    }
}
