package io.github.craftizz.oneblockworld.database;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.FileData;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.craftizz.oneblockworld.oneblock.Member;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import io.github.craftizz.oneblockworld.oneblock.settings.SettingType;
import io.github.craftizz.oneblockworld.user.UserType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OneBlockDataHelper {

    public OneBlock from(final @NotNull UUID uniqueId,
                         final @NotNull Json userFile) {

        final OneBlock oneBlock = new OneBlock(uniqueId);

        // Get Sections
        final FlatFileSection settingSection = userFile.getSection(uniqueId + ".settings");

        // Load Settings
        for (final SettingType settingType : SettingType.values()) {
            oneBlock.setSettings(settingType, settingSection.getInt(settingType.getConfigName()));
        }

        // Load members
        for (final String memberKey : userFile.singleLayerKeySet(uniqueId + ".members")) {
            oneBlock.addMember(UUID.fromString(memberKey), userFile.getEnum(uniqueId + ".members." + memberKey, UserType.class));
        }

        // Load Upgrades

        return oneBlock;
    }

    public void to(final @NotNull OneBlock oneBlock,
                   final @NotNull Json userFile) {

        final UUID uniqueId = oneBlock.getUniqueId();
        final FileData fileData = userFile.getFileData();

        // Save settings
        for (final SettingType settingType : SettingType.values()) {
            fileData.insert(uniqueId + ".settings." + settingType.getConfigName(), oneBlock.getSetting(settingType));
        }

        // Save weak member list
        for (final Member member : oneBlock.getMembers()) {
            fileData.insert(uniqueId + ".members." + member.getUniqueId(), member.getUserType().toString());

        }

        // Save upgrades


        userFile.write();
    }


}
