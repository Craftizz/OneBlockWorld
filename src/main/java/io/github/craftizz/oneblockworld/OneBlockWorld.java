package io.github.craftizz.oneblockworld;

import io.github.craftizz.oneblockworld.commands.TestCommand;
import io.github.craftizz.oneblockworld.configuration.ConfigurationHandler;
import io.github.craftizz.oneblockworld.database.DatabaseHandler;
import io.github.craftizz.oneblockworld.listeners.BlockListener;
import io.github.craftizz.oneblockworld.listeners.WorldListener;
import io.github.craftizz.oneblockworld.managers.OneBlockManager;
import io.github.craftizz.oneblockworld.managers.PhaseManager;
import io.github.craftizz.oneblockworld.managers.UserManager;
import io.github.craftizz.oneblockworld.managers.WorldManager;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OneBlockWorld extends JavaPlugin {

    // Handlers
    private ConfigurationHandler configurationHandler;
    private DatabaseHandler databaseHandler;

    // Managers
    private WorldManager worldManager;
    private PhaseManager phaseManager;
    private UserManager userManager;
    private OneBlockManager oneBlockManager;

    private CommandManager commandManager;

    @Override
    public void onEnable() {

        // Register Handlers
        this.configurationHandler = new ConfigurationHandler(this);
        this.databaseHandler = new DatabaseHandler(this);

        // Register Managers
        this.worldManager = new WorldManager(this);
        this.phaseManager = new PhaseManager();
        this.userManager = new UserManager(this);
        this.oneBlockManager = new OneBlockManager(this);

        // Start Loading Configurations
        configurationHandler.loadPhases();


        // Register Listeners
        final PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new WorldListener(this), this);
        manager.registerEvents(new BlockListener(this), this);

        // Register Commands
        this.commandManager = new CommandManager(this);
        commandManager.register(new TestCommand(this));





    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public OneBlockManager getOneBlockManager() {
        return oneBlockManager;
    }

    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }


}
