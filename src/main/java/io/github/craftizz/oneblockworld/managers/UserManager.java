package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.user.User;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    private final HashMap<UUID, User> loadedUsers;

    public UserManager() {
        this.loadedUsers = new HashMap<>();
    }

    public void getOrLoadUser(final @NotNull UUID uniqueId) {

    }

    public void loadUser(final @NotNull UUID uniqueId) {

    }

    public void loadUser(final @NotNull OfflinePlayer offlinePlayer) {

    }

    public void unloadUser(final @NotNull UUID uniqueId) {

    }

    public void unloadUser(final @NotNull OfflinePlayer offlinePlayer) {

    }

}
