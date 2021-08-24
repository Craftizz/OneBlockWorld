package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

public enum UserType {

    OWNER(100),
    MEMBER(75),
    TRUSTED(50),
    VISITOR(25),
    NONE(0);

    private final Integer level;

    UserType(final @NotNull Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
