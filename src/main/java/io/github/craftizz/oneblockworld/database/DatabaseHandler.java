package io.github.craftizz.oneblockworld.database;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.oneblock.Member;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import io.github.craftizz.oneblockworld.user.User;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DatabaseHandler {

    private static final String userDataPath = "plugins/OneBlockWorld/data/users/";
    private static final String oneBlockDataPath = "plugins/OneBlockWorld/data/oneblock/";

    private final UserDataHelper userDataHelper;
    private final OneBlockDataHelper oneBlockDataHelper;

    private final HashMap<UUID, Json> userFiles;
    private final HashMap<UUID, Json> oneBlockFiles;

    private final OneBlockWorld plugin;

    public DatabaseHandler(final @NotNull OneBlockWorld plugin) {
        this.userFiles = new HashMap<>();
        this.oneBlockFiles = new HashMap<>();
        this.userDataHelper = new UserDataHelper();
        this.oneBlockDataHelper = new OneBlockDataHelper();
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
     * Loads oneblock in the main thread from the databaase
     *
     * @param uniqueId the uniqueId of the OneBlock to be loaded
     * @return the loaded OneBlock
     */
    public OneBlock loadOneBlockUrgently(final @NotNull UUID uniqueId) {
        return oneBlockDataHelper.from(uniqueId, getOrLoadOneBlockFile(uniqueId));
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
     * Loads OneBlock async from the database
     *
     * @param uniqueId uniqueId the uniqueId of the OneBlock to be loaded
     * @return the loaded OneBlock
     */
    public OneBlock loadOneBlockAsync(final @NotNull UUID uniqueId) {
        try {
            return CompletableFuture.supplyAsync(() -> oneBlockDataHelper.from(uniqueId, getOrLoadOneBlockFile(uniqueId))).get(5, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return loadOneBlockUrgently(uniqueId);
    }

    /**
     * Saves a user to the database, either async or sync
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
     * Saves a OneBlock to the database, either async or sync
     *
     * @param oneBlock the OneBlock to be saved to the database
     * @param async if the saving should be async or not
     */
    public void saveOneBlock(final @NotNull OneBlock oneBlock,
                             final @NotNull Boolean async) {
        if (async) {
            try {
                CompletableFuture.runAsync(() -> oneBlockDataHelper.to(oneBlock, getOrLoadOneBlockFile(oneBlock.getUniqueId())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            oneBlockDataHelper.to(oneBlock, getOrLoadOneBlockFile(oneBlock.getUniqueId()));
        }
    }

    /**
     * Unloads the user
     *
     * @param user the user to be unloaded
     */
    public void unloadUser(final @NotNull User user) {
        userFiles.remove(user.getUniqueId());
    }

    /**
     * Unloads the OneBlock
     *
     * @param oneBlock the OneBlock to be unloaded
     */
    public void unloadOneBlock(final @NotNull OneBlock oneBlock) {
        unloadOneBlock(oneBlock.getUniqueId());
    }

    public void unloadOneBlock(final @NotNull UUID uniqueId) {
        oneBlockFiles.remove(uniqueId);
    }

    /**
     * Deletes and unload the OneBlock file
     *
     * @param uniqueId the OneBlock to be deleted
     */
    public void deleteOneBlock(final @NotNull UUID uniqueId) {
        unloadOneBlock(uniqueId);
        try {
            FileUtils.deleteDirectory(new File(uniqueId.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            plugin.getLogger().warning("Deleted OneBlock (" + uniqueId + ") Data File");
        }
    }

    public void deleteUserOneBlock(final @NotNull OneBlock oneBlock) {
        for (final Member member : oneBlock.getMembers()) {
            userDataHelper.deleteOneBlock(
                    member.getUniqueId(),
                    oneBlock.getUniqueId(),
                    getOrLoadUserFile(member.getUniqueId()));
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
                    .fromPath(uniqueId.toString(), userDataPath)
                    .createJson();
            userFiles.putIfAbsent(uniqueId, loadedUser);
            return loadedUser;
        }
    }

    /**
     * Loads OneBlock Json file
     *
     * @param uniqueId the uniqueId of the OneBlock
     * @return the JSON file of the OneBlock
     */
    public Json getOrLoadOneBlockFile(final @NotNull UUID uniqueId) {
        final Json oneBlockFile = oneBlockFiles.get(uniqueId);
        if (oneBlockFile != null) {
            return oneBlockFile;
        } else {
            final Json loadedOneBlock = LightningBuilder
                    .fromPath(uniqueId.toString(), oneBlockDataPath)
                    .createJson();
            oneBlockFiles.putIfAbsent(uniqueId, loadedOneBlock);
            return loadedOneBlock;
        }
    }

}
