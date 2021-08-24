package io.github.craftizz.oneblockworld.oneblock.settings;

public enum SettingType {

    DIFFICULTY(DifficultySetting.class),
    MUSIC(MusicSetting.class),
    TIME(TimeSetting.class),
    WEATHER(WeatherSetting.class);

    private Class<?> settingClass;

    SettingType(Class<?> settingClass) {
        this.settingClass = settingClass;
    }

    public Class<?> getSettingClass() {
        return settingClass;
    }
}
