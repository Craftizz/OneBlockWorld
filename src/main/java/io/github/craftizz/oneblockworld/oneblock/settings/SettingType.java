package io.github.craftizz.oneblockworld.oneblock.settings;

import org.jetbrains.annotations.NotNull;

public enum SettingType {

    DIFFICULTY(DifficultySetting.class, "difficulty"),
    MUSIC(MusicSetting.class, "music"),
    TIME(TimeSetting.class, "time"),
    WEATHER(WeatherSetting.class, "weather");

    private final Class<?> settingClass;
    private final String configName;

    SettingType(final @NotNull Class<?> settingClass,
                final @NotNull String configName) {
        this.settingClass = settingClass;
        this.configName = configName;
    }

    public Class<?> getSettingClass() {
        return settingClass;
    }

    public String getConfigName() {
        return configName;
    }
}
