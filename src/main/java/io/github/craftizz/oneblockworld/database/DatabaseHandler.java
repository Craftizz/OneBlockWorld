package io.github.craftizz.oneblockworld.database;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DatabaseHandler {

    private static final String dataPath = "plugins/OneBlockWorld/data/users/";

    private final UserDataHelper userDataHelper;

    private final HashMap<UUID, Json> userFiles;
    private final OneBlockWorld plugin;

    public DatabaseHandler(final @NotNull OneBlockWorld plugin) {
        this.userFiles = new HashMap<>();
        this.userDataHelper = new UserDataHelper();
        this.plugin = plugin;
    }

    /**
     * Loads user in the main thread from the database
     *
     * @param uniqueId the uniqueId of the user to be loaded
     * @return the loaded user
     */
    public User loadUserUrgently(final @NotNull UUID uniqueId) {
        return userDataHelper.from(uniqueId, getOrLoadUserFile(uniqueId));
    }

    /**
     * Loads user async from the database
     *
     * @param uniqueId uniqueId the uniqueId of the user to be loaded
     * @return the loaded user
     */
    public User loadUserAsync(final @NotNull UUID uniqueId) {
        try {
            return CompletableFuture.supplyAsync(() -> userDataHelper.from(uniqueId, getOrLoadUserFile(uniqueId))).get(5, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return loadUserUrgently(uniqueId);
    }

    /**
     * Saves a user to the database, either async or not async
     *
     * @param user the user to be saved to the database
     * @param async if saving should be async or not
     */
    public void saveUser(final @NotNull User user,
                         final @NotNull Boolean async) {
        if (async) {
            try {
                CompletableFuture.runAsync(() -> userDataHelper.saveTo(user, getOrLoadUserFile(user.getUniqueId())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            userDataHelper.saveTo(user, getOrLoadUserFile(user.getUniqueId()));
        }
    }

    /**
     * Loads user Json file
     *
     * @param uniqueId the uniqueId of the user
     * @return the JSON file of the user
     */
    public Json getOrLoadUserFile(final @NotNull UUID uniqueId) {
        final Json userFile = userFiles.get(uniqueId);
        if (userFile != null) {
            return userFile;
        } else {
            final Json loadedUser = LightningBuilder
                    .fromPath(uniqueId.toString(), dataPath)
                    .createJson();
            userFiles.putIfAbsent(uniqueId, loadedUser);
            return loadedUser;
        }
    }
}
