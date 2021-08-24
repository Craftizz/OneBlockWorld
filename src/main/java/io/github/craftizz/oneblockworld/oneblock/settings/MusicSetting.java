package io.github.craftizz.oneblockworld.oneblock.settings;

import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class MusicSetting extends Setting {

    enum MusicType {

        DISC_13(Material.MUSIC_DISC_13, Key.key("music_disc_13")),
        DISC_CAT(Material.MUSIC_DISC_CAT, Key.key("music_disc_cat")),
        DISC_BLOCKS(Material.MUSIC_DISC_BLOCKS, Key.key("music_disc_blocks")),
        DISC_CHRIP(Material.MUSIC_DISC_CHIRP, Key.key("music_disc_chirp")),
        DISC_FAR(Material.MUSIC_DISC_FAR, Key.key("music_disc_far")),
        DISC_MALL(Material.MUSIC_DISC_MALL, Key.key("music_disc_mall")),
        DISC_MELLOHI(Material.MUSIC_DISC_MELLOHI, Key.key("music_disc_mellohi")),
        DISC_STAL(Material.MUSIC_DISC_STAL, Key.key("music_disc_stal")),
        DISC_STRAD(Material.MUSIC_DISC_STRAD, Key.key("music_disc_strad")),
        DISC_WARD(Material.MUSIC_DISC_WARD, Key.key("music_disc_ward")),
        DISC_11(Material.MUSIC_DISC_11, Key.key("music_disc_11")),
        DISC_WAIT(Material.MUSIC_DISC_WAIT, Key.key("music_disc_wait")),
        DISC_PIGSTEP(Material.MUSIC_DISC_PIGSTEP, Key.key("music_disc_pigstep"));

        final Material material;
        final Key key;

        MusicType(final @NotNull Material material,
                  final @NotNull Key soundKey) {
            this.material = material;
            this.key = soundKey;
        }

        /**
         * @return the MaterialType for the Disc
         */
        public Material getMaterial() {
            return material;
        }

        public Key getKey() {
            return key;
        }
    }

    private final MusicType musicType;

    public MusicSetting(final @NotNull Double cost,
                        final @NotNull Integer level,
                        final @NotNull MusicType musicType) {
        super(cost, level);
        this.musicType = musicType;


    }

    public MusicType getMusicType() {
        return musicType;
    }
}
