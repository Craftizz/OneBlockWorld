package io.github.craftizz.oneblockworld;

import io.github.craftizz.oneblockworld.listeners.WorldInitializeListener;
import io.github.craftizz.oneblockworld.managers.WorldManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OneBlockWorld extends JavaPlugin {

    // Managers
    private WorldManager worldManager;

    @Override
    public void onEnable() {

        // Managers
        this.worldManager = new WorldManager(this);

        // Listeners
        final PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new WorldInitializeListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
