package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class OneBlockManager {

    private final WorldManager worldManager;
    // private final UserManager userManager;
    // private final DatabaseManager databaseManager;

    private final HashMap<UUID, OneBlock> oneBlocks;

    public OneBlockManager(final @NotNull OneBlockWorld plugin) {
        this.worldManager = plugin.getWorldManager();
        this.oneBlocks = new HashMap<>();
    }

    public OneBlock getOneBlock(final @NotNull UUID uniqueId) {
        return oneBlocks.get(uniqueId);
    }

    public OneBlock createOneBlock() {

        final UUID uniqueId = UUID.randomUUID();
        worldManager.createOneBlockWorld(uniqueId);

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
