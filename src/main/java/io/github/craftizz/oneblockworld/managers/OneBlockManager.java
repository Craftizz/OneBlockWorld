package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import io.github.craftizz.oneblockworld.user.User;
import io.github.craftizz.oneblockworld.user.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class OneBlockManager {

    private final WorldManager worldManager;
    private final UserManager userManager;
    // private final DatabaseManager databaseManager;

    private final HashMap<UUID, OneBlock> oneBlocks;

    public OneBlockManager(final @NotNull OneBlockWorld plugin) {
        this.worldManager = plugin.getWorldManager();
        this.userManager = plugin.getUserManager();
        this.oneBlocks = new HashMap<>();
    }

    /**
     * Returns a OneBlock with the corresponding uuid
     *
     * @param uniqueId the uuid of the OneBlock
     * @return OneBlock or null
     */
    public OneBlock getOneBlock(final @NotNull UUID uniqueId) {
        return oneBlocks.get(uniqueId);
    }

    /**
     * Creates a block block using the unique id of the creator
     * The {@param user} uuid will not match the uniqueId of the
     * OneBlock world
     *
     * @param user the creator of the OneBlock
     * @return a new one block
     */
    public OneBlock createOneBlock(final @NotNull User user) {

        // Create World
        final UUID uniqueId = UUID.randomUUID();
        worldManager.createOneBlockWorld(uniqueId);

        // Save Profile
        final Integer slot = user.getNextEmptyProfile();
        user.addProfile(new UserProfile(slot, uniqueId));

        // Save OneBlock

        return new OneBlock(uniqueId);
    }

    public void deleteOneBlock() {

    }

    public void loadOneBlock() {

    }

    public void unloadOneBlock() {

    }

    public void saveOneBlock() {

    }

    public void saveAllOneBlock() {

    }

}
