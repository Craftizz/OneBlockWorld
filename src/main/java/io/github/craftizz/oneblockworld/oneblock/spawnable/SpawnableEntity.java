package io.github.craftizz.oneblockworld.oneblock.spawnable;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class SpawnableEntity extends Spawnable {

    private final EntityType entityType;

    public SpawnableEntity(final @NotNull Integer probability,
                           final @NotNull EntityType entityType) {
        super(probability);
        this.entityType = entityType;
    }

    /**
     * @return the entityType to spawn
     */
    public final EntityType getEntityType() {
        return entityType;
    }
}
