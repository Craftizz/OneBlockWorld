package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.database.DatabaseHandler;
import io.github.craftizz.oneblockworld.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class UserManager {

    private final DatabaseHandler databaseHandler;

    private final HashMap<UUID, User> loadedUsers;

    public UserManager(final @NotNull OneBlockWorld plugin) {
        this.loadedUsers = new HashMap<>();
        this.databaseHandler = plugin.getDatabaseHandler();
    }

    public User getUser(final @NotNull UUID uniqueId) {
        final User user = loadedUsers.get(uniqueId);

        if (user != null) {
            return user;
        } else {
            return loadUser(uniqueId, false);
        }
    }

    public User getUser(final @NotNull OfflinePlayer offlinePlayer) {
        return getUser(offlinePlayer.getUniqueId());
    }

    public User getUser(final @NotNull Player player) {
        return getUser(player.getUniqueId());
    }

    public User loadUser(final @NotNull UUID uniqueId,
                         final @NotNull Boolean async) {
        final User user;

        if (async) {
            user = databaseHandler.loadUserAsync(uniqueId);
        } else {
            user = databaseHandler.loadUserUrgently(uniqueId);
        }

        loadedUsers.putIfAbsent(uniqueId, user);
        return user;
    }

    public User loadUser(final @NotNull OfflinePlayer offlinePlayer,
                         final @NotNull Boolean async) {
        return loadUser(offlinePlayer.getUniqueId(), async);
    }

    public User loadUser(final @NotNull Player player,
                         final @NotNull Boolean async) {
        return loadUser(player.getUniqueId(), async);
    }

    public void saveAllUser() {
        final Iterator<User> userIterator = loadedUsers.values().iterator();

        while (userIterator.hasNext()) {

            final User user = userIterator.next();
            databaseHandler.saveUser(user, false);

            if (Bukkit.getPlayer(user.getUniqueId()) == null) {
                userIterator.remove();
            }
        }
    }
}
