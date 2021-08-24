package io.github.craftizz.oneblockworld.database;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.FileData;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.craftizz.oneblockworld.user.User;
import io.github.craftizz.oneblockworld.user.UserData;
import io.github.craftizz.oneblockworld.user.UserProfile;
import io.github.craftizz.oneblockworld.user.UserType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserDataHelper {

    /**
     * Loads userdata from files and returns the user's instance
     *
     * @param uniqueId the uniqueId of the user to be loaded
     * @param userFile the file where the data will be loaded
     * @return user instance
     */
    public User from(final @NotNull UUID uniqueId,
                     final @NotNull Json userFile) {

        final User user = new User(uniqueId);

        // Get Sections
        final FlatFileSection profileSection = userFile.getSection(uniqueId + ".profiles");
        final FlatFileSection dataSection = userFile.getSection(uniqueId + ".user-data");

        // Load Profiles
        for (final String key : profileSection.singleLayerKeySet()) {
            user.addProfile(new UserProfile(Integer.parseInt(key),
                    UUID.fromString(profileSection.getString(key))
            ));
        }

        // Load User Data
        for (final String key : dataSection.singleLayerKeySet()) {
            user.addUserData(new UserData(UUID.fromString(key),
                    UserType.valueOf(profileSection.getString(key))
            ));
        }

        return user;
    }

    /**
     * Saves user data to userFile
     *
     * @param user the user to be saved
     * @param userFile the file where data will be saved
     */
    public void saveTo(final @NotNull User user,
                       final @NotNull Json userFile) {

        final UUID uniqueId = user.getUniqueId();
        final FileData fileData = userFile.getFileData();

        // Save user profiles
        for (final UserProfile userProfile : user.getProfiles().values()) {
            fileData.insert(uniqueId + ".profiles." + userProfile.getSlot(), userProfile.getUniqueId());
        }

        // Save user profiles
        for (final UserData userData : user.getUserData()) {
            fileData.insert(uniqueId + ".user-data." + userData.getUniqueId(), userData.getMemberType().toString());
        }

        // Write
        userFile.write();
    }

}
