package io.github.craftizz.oneblockworld.configuration;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.managers.PhaseManager;
import io.github.craftizz.oneblockworld.oneblock.phases.Phase;
import io.github.craftizz.oneblockworld.oneblock.phases.PhaseType;
import io.github.craftizz.oneblockworld.oneblock.spawnable.SpawnableBlock;
import io.github.craftizz.oneblockworld.oneblock.spawnable.SpawnableEntity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ConfigurationHandler {

    private final OneBlockWorld plugin;

    public ConfigurationHandler(OneBlockWorld plugin) {
        this.plugin = plugin;
    }

    public void loadPhases() {

        final PhaseManager phaseManager = plugin.getPhaseManager();

        for (final PhaseType phaseType : PhaseType.values()) {

            // Get Phase Configuration
            final Yaml phaseFile = LightningBuilder
                    .fromPath(phaseType.getDisplayName(), plugin.getDataFolder().getAbsolutePath())
                    .addInputStreamFromResource(phaseType.getResourceName())
                    .createYaml();

            // Get Sections
            final FlatFileSection blockSection = phaseFile.getSection(phaseType.getDisplayName() + ".blocks");
            final FlatFileSection entitySection = phaseFile.getSection(phaseType.getDisplayName() + ".entities");

            // Create a phase
            final Phase phase = new Phase(phaseType);

            // Load Block Spawnables
            for (final String blockKey : blockSection.singleLayerKeySet()) {

                final Material material = Material.valueOf(blockKey);
                final Integer chance = blockSection.getInt(blockKey);

                phase.addSpawnable(new SpawnableBlock(chance, material));
            }

            // Load Entity Spawnables
            for (final String entityKey : entitySection.singleLayerKeySet()) {

                final EntityType entityType = EntityType.valueOf(entityKey);
                final Integer chance = entitySection.getInt(entityKey);

                phase.addSpawnable(new SpawnableEntity(chance, entityType));
            }

            // Add Phases
            phaseManager.addPhase(phase);

            plugin.getLogger().warning("Loaded " + phaseType.getDisplayName() + " phase");
        }
    }

}
