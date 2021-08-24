package io.github.craftizz.oneblockworld.oneblock;

import io.github.craftizz.oneblockworld.oneblock.settings.SettingType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class OneBlock {

    private final UUID uniqueId;

    private final Set<UUID> weakMemberList;

    private final EnumMap<SettingType, Integer> settings;

    public OneBlock(final @NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.weakMemberList = new HashSet<>();
        this.settings = new EnumMap<>(SettingType.class);
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

    public Set<UUID> getWeakMemberList() {
        return weakMemberList;
    }
}
