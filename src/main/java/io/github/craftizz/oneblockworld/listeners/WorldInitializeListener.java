package io.github.craftizz.oneblockworld.listeners;

import io.github.craftizz.oneblockworld.managers.WorldManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldInitializeListener implements Listener {

    /**
     * Listens to {@link WorldInitEvent} to make sure that the
     * spawn chunks will not be loaded
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldInitializeCancelChunkLoad(final WorldInitEvent event) {
        final World world = event.getWorld();
        if (WorldManager.isOneBlockWorld(world)) {
            world.setKeepSpawnInMemory(false);
        }
    }

}
