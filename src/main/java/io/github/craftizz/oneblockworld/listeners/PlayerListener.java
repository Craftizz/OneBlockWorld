package io.github.craftizz.oneblockworld.listeners;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.managers.OneBlockManager;
import io.github.craftizz.oneblockworld.managers.UserManager;
import io.github.craftizz.oneblockworld.user.User;
import io.github.craftizz.oneblockworld.user.UserProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerListener implements Listener {

    private final UserManager userManager;
    private final OneBlockManager oneBlockManager;

    public PlayerListener(final @NotNull OneBlockWorld plugin) {
        this.userManager = plugin.getUserManager();
        this.oneBlockManager = plugin.getOneBlockManager();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {

        final User user = userManager.getUser(event.getPlayer());
        final UserProfile userProfile = user.getPrimaryProfile();

        if (userProfile != null) {
            oneBlockManager.loadOneBlock(userProfile.getUniqueId(), false);
        }

    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerChangeWorldEvent(final PlayerChangedWorldEvent event) {

    }

}
