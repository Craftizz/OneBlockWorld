package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

public enum MemberType {

    OWNER(100),
    MEMBER(75),
    TRUSTED(50),
    VISITOR(25),
    NONE(0);

    private final Integer level;

    MemberType(final @NotNull Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
