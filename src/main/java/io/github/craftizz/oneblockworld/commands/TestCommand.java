package io.github.craftizz.oneblockworld.commands;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Command("oneblock")
public class TestCommand extends CommandBase {

    private final OneBlockWorld plugin;

    public TestCommand(final @NotNull OneBlockWorld plugin) {
        this.plugin = plugin;
    }

    private UUID uniqueId;

    @SubCommand("create")
    public void onCreateCommand(final Player player) {

        final OneBlock oneBlock = plugin.getOneBlockManager().createOneBlock(null);
        this.uniqueId = oneBlock.getUniqueId();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            final Location location = new Location(oneBlock.getWorld(), 0, 35, 0);
            oneBlock.getWorld().getChunkAtAsync(location).thenAccept(chunk -> {
                chunk.setForceLoaded(true);
                player.teleport(location);
            });

        }, 80);
    }

    @SubCommand("load")
    public void onLoadCommand(final Player player, String worldName) {


        final long startTime = System.currentTimeMillis();

        plugin.getWorldManager().createOrLoadWorld(UUID.fromString(worldName));

        plugin.getLogger().warning("OneBlock world created/loaded in " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @SubCommand("tp")
    public void onTpCommand(final Player player, String worldName) {

        World world = plugin.getWorldManager().createOrLoadWorld(UUID.fromString(worldName));
        player.teleport(new Location(world, 0, 35, 0));
    }

    @SubCommand("delete")
    public void delete(final Player player) {
        plugin.getWorldManager().deleteWorld(uniqueId);
    }

}
