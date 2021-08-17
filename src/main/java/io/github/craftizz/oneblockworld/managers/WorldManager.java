package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.oneblock.generators.OneBlockGenerator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

public class WorldManager {

    private static final OneBlockGenerator generator = new OneBlockGenerator();

    private static final String uuidPattern = "/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/";
    private static final Pattern pattern = Pattern.compile(uuidPattern);

    private final OneBlockWorld plugin;

    public WorldManager(OneBlockWorld plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a world if the {@param uniqueId} does not exists, else it will
     * load the world. This will also use the {@link OneBlockGenerator}
     *
     * @param uniqueId is the unique id of the world
     * @return the created or loaded OneBlock world
     */
    public World createOrLoadWorld(final @NotNull UUID uniqueId) {
        return new WorldCreator(uniqueId.toString())
                .generator(generator)
                .generateStructures(false)
                .createWorld();
    }

    /**
     * Creates the an empty world using {@link OneBlockGenerator} with
     * the default blocks.
     *
     * @param uniqueId is the unique id of the world
     * @return the new oneBlockWorld
     */
    public World createOneBlockWorld(final @NotNull UUID uniqueId) {

        // Create World
        final World world = createOrLoadWorld(uniqueId);

        // Get Default Block Location
        final Location location = new Location(world, 0, 32, 0);

        // Load Chunk Asynchronously and Set Default Blocks
        world.getChunkAtAsync(location).thenAccept(chunk -> {

            final Block oneBlock = location.getBlock();
            final Block bedrockBlock = oneBlock.getRelative(BlockFace.DOWN);

            oneBlock.setType(Material.GRASS_BLOCK);
            bedrockBlock.setType(Material.BEDROCK);
        });

        return world;
    }

    /**
     * Unloads the world from the server
     *
     * @param uniqueId is the unique id of the world to be unloaded
     */
    public boolean unloadWorld(final @NotNull UUID uniqueId,
                               final @NotNull Boolean shouldSave) {
        return plugin.getServer().unloadWorld(uniqueId.toString(), shouldSave);
    }

    /**
     * Checks if the {@param world} is a OneBlock world. It uses
     * {@link Pattern} to check if the world name is a UUID.
     *
     * @param world is the world to be check
     * @return if the world is a oneBlockWorld
     */
    public static boolean isOneBlockWorld(final @NotNull World world) {
        return pattern.matcher(world.getName()).find();
    }
}
