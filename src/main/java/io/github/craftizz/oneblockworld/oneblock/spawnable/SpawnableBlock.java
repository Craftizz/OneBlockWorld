package io.github.craftizz.oneblockworld.oneblock.spawnable;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SpawnableBlock extends Spawnable {

    private final Material material;

    public SpawnableBlock(final @NotNull Integer probability,
                          final @NotNull Material material) {
        super(probability);
        this.material = material;
    }

    /**
     * @return the material to set on a block
     */
    public Material getMaterial() {
        return material;
    }
}
