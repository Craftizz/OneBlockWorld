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

    private final OneBlockWorld plugin;
    private final DatabaseHandler databaseHandler;

    private final HashMap<UUID, User> loadedUsers;

    public UserManager(final @NotNull OneBlockWorld plugin) {
        this.loadedUsers = new HashMap<>();
        this.plugin = plugin;
        this.databaseHandler = plugin.getDatabaseHandler();
    }

    /**
     * Get the user. If missing from the loadedUsers map,
     * it will proceed to load the user in non-async
     *
     * @param uniqueId the uuid of the user
     * @return the user instance
     */
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

    /**
     * Loads user from the database, can be sync or async.
     * This will put the user into the loadedUsers
     *
     * @param uniqueId the uniqueId of user
     * @param async if should run async
     * @return the newly loaded user.
     */
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

    /**
     * Saves user to the database, either sync or async
     *
     * @param user the user to be saved
     * @param async if async or not async
     */
    public void saveUser(final @NotNull User user,
                         final @NotNull Boolean async) {
        databaseHandler.saveUser(user, async);
    }

    /**
     * Saves all the users in the loadedUsers to the database
     * This will also unload users that are not online anymore
     */
    public void saveAllUser() {

        plugin.getLogger().warning("Saving Users Data...");

        final Iterator<User> userIterator = loadedUsers.values().iterator();

        while (userIterator.hasNext()) {

            final User user = userIterator.next();
            databaseHandler.saveUser(user, false);

            if (Bukkit.getPlayer(user.getUniqueId()) == null) {
                databaseHandler.unloadUser(user);
                userIterator.remove();
            }
        }

    }

}
