package io.github.craftizz.oneblockworld.oneblock;

import io.github.craftizz.oneblockworld.oneblock.settings.SettingType;
import io.github.craftizz.oneblockworld.oneblock.upgrades.UpgradeType;
import io.github.craftizz.oneblockworld.user.UserType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OneBlock {

    private final UUID uniqueId;

    private final Set<Member> members;
    private final EnumMap<SettingType, Integer> settings;
    private final EnumMap<UpgradeType, Integer> upgrades;

    public OneBlock(final @NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.members = new HashSet<>();
        this.settings = new EnumMap<>(SettingType.class);
        this.upgrades = new EnumMap<>(UpgradeType.class);
    }

    /**
     * @return the uniqueId of this
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * @return the members of the OneBlock
     */
    public Set<Member> getMembers() {
        return members;
    }

    /**
     * Adds a new member to OneBlock. Make sure to modify the data
     * of the {@link io.github.craftizz.oneblockworld.user.UserData}
     *
     * @param uniqueId the uuid of the user
     * @param userType the type of user
     */
    public void addMember(final @NotNull UUID uniqueId,
                          final @NotNull UserType userType) {
        members.add(new Member(uniqueId, userType));
    }

    /**
     * Removes a member to the OneBlock Data. Make sure to modify the
     * data of the {@link io.github.craftizz.oneblockworld.user.UserData}
     *
     * @param uniqueId the uuid of the user
     */
    public void removeMember(final @NotNull UUID uniqueId) {
        members.removeIf(member -> member.getUniqueId().equals(uniqueId));
    }

    public Member getMember(final @NotNull UUID uniqueId) {
        for (final Member member : members) {
            if (member.getUniqueId().equals(uniqueId)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Checks if the user with the corresponding uuid is already a member
     *
     * @param uniqueId the uuid of the user
     * @return true if member, false if not
     */
    public boolean isMember(final @NotNull UUID uniqueId) {
        for (final Member member : members) {
            if (member.getUniqueId().equals(uniqueId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a specific setting to a setting level
     *
     * @param settingType the type of setting to be modified
     * @param integer the level of setting
     */
    public void setSettings(final @NotNull SettingType settingType,
                            final @NotNull Integer integer) {
        settings.put(settingType, integer);
    }

    public Integer getSetting(final @NotNull SettingType settingType) {
        final Integer settingLevel = settings.get(settingType);
        if (settingLevel != null) {
            return settingLevel;
        } else {
            return 0;
        }
    }

    public EnumMap<SettingType, Integer> getSettings() {
        return settings;
    }
}
