package io.github.craftizz.oneblockworld.tasks;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.managers.WorldManager;
import io.github.craftizz.oneblockworld.tasks.tasktypes.TimedTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class WorldTickerTask extends TimedTask {

    private final WorldManager worldManager;

    public WorldTickerTask(final @NotNull OneBlockWorld plugin) {
        super(1);
        this.worldManager = plugin.getWorldManager();
    }

    @Override
    public void compute() {

        for (final World world : Bukkit.getWorlds()) {

            if (!worldManager.inOneBlock(world)) {
                continue;
            }

            world.getTime();

        }

    }



}
