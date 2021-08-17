package io.github.craftizz.oneblockworld.oneblock.generators;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OneBlockGenerator extends ChunkGenerator {

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world,
                                                @NotNull Random random, int x, int z,
                                                @NotNull BiomeGrid biome) {
        return createChunkData(world);
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return Collections.emptyList();
    }


    @Override
    public boolean canSpawn(@NotNull World world,
                            int x, int z) {
        return true;
    }
}
