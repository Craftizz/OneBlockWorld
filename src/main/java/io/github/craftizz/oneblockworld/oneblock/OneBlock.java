package io.github.craftizz.oneblockworld.oneblock;

import io.github.craftizz.oneblockworld.oneblock.settings.SettingType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.UUID;

public class OneBlock {

    private final UUID uniqueId;

    private final EnumSet<SettingType> settings;
    private final HashSet<UUID> weakMemberList;

    public OneBlock(final @NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.weakMemberList = new HashSet<>();
        this.settings = EnumSet.allOf(SettingType.class);
    }

    /**
     * @return the uniqueId of this
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * @return the instance of the world REMOVE LATER.
     */
    public World getWorld() {
        return Bukkit.getWorld(uniqueId.toString());
    }

    public HashSet<UUID> getWeakMemberList() {
        return weakMemberList;
    }
}
