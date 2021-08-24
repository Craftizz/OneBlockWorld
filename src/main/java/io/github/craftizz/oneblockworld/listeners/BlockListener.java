package io.github.craftizz.oneblockworld.listeners;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.managers.PhaseManager;
import io.github.craftizz.oneblockworld.managers.WorldManager;
import io.github.craftizz.oneblockworld.oneblock.phases.Phase;
import io.github.craftizz.oneblockworld.oneblock.spawnable.Spawnable;
import io.github.craftizz.oneblockworld.oneblock.spawnable.SpawnableBlock;
import io.github.craftizz.oneblockworld.oneblock.spawnable.SpawnableEntity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockListener implements Listener {

    private final WorldManager worldManager;
    private final PhaseManager phaseManager;

    public BlockListener(final @NotNull OneBlockWorld plugin) {
        this.worldManager = plugin.getWorldManager();
        this.phaseManager = plugin.getPhaseManager();
    }

    /**
     * Listens to the BlockBreakEvent with the priority
     * as highest. This will cancel the event to handle
     * the block break
     *
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(final BlockBreakEvent event) {

        final Block block = event.getBlock();
        final Player player = event.getPlayer();

        if (worldManager.inOneBlock(block) && worldManager.isOneBlock(block)) {
            event.setCancelled(true);
            process(block, player);
        }
    }

    /**
     * Method to process overall the block break events
     *
     * @param block the OneBlock block
     * @param player the player who broke the block
     */
    public void process(final @NotNull Block block,
                        final @NotNull Player player) {

        final Phase phase = phaseManager.getCurrentPhase();
        final Spawnable spawnable = phase.getRandomSpawnable();

        if (spawnable instanceof SpawnableBlock spawnableBlock) {
            setBlock(block, spawnableBlock);
        } else if (spawnable instanceof SpawnableEntity spawnableEntity) {
            spawnMob(block, spawnableEntity);
        }

    }

    /**
     * helper method to set naturally break a block and set the material
     *
     * @param block the OneBlock block
     * @param spawnableBlock the block to be set
     */
    public void setBlock(final @NotNull Block block,
                         final @NotNull SpawnableBlock spawnableBlock) {
        block.breakNaturally();
        block.setType(spawnableBlock.getMaterial());
    }

    /**
     * helper method to spawn an entity
     *
     * @param block the OneBlock block
     * @param spawnableEntity the entity to be spawned
     */
    public void spawnMob(final @NotNull Block block,
                         final @NotNull SpawnableEntity spawnableEntity) {
        final Location location = block.getLocation().add(0, 2, 0);
        location.getWorld().spawnEntity(location, spawnableEntity.getEntityType());
    }
}
