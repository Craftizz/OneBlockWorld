package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.oneblock.phases.Phase;
import io.github.craftizz.oneblockworld.oneblock.phases.PhaseType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class PhaseManager {

    private final EnumMap<PhaseType, Phase> phases;

    private PhaseType currentPhase;

    public PhaseManager() {
        this.phases = new EnumMap<>(PhaseType.class);
    }

    /**
     * Adds a phase to the enum map of phases
     *
     * @param phase the base to be added
     */
    public void addPhase(final @NotNull Phase phase) {
        phases.put(phase.getPhaseType(), phase);
    }

    public Phase getCurrentPhase() {
        return phases.get(PhaseType.SUMMER);
    }



}
