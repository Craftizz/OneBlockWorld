package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class User {

    private final UUID uniqueId;

    private final HashSet<UserData> userDataSet;
    private final HashMap<Integer, UserProfile> profiles;

    public User(final @NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.userDataSet = new HashSet<>();
        this.profiles = new HashMap<>();
    }

    /**
     * @return the UniqueId of the player. This will
     * match the uniqueId of {@link org.bukkit.entity.Player}
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Adds a userprofile to the profiles hashmap
     *
     * @param userProfile the user profile to be added
     */
    public void addProfile(final @NotNull UserProfile userProfile) {
        profiles.put(userProfile.getSlot(), userProfile);
    }

    /**
     * Get the next empty profile of the user's profile
     *
     * @return a int slot
     */
    public Integer getNextEmptyProfile() {
        return Collections.min(profiles.keySet());
    }

    /**
     * Adds a userdata to the userdata set
     *
     * @param userData the userdata to be added
     */
    public void addUserData(final @NotNull UserData userData) {
        this.userDataSet.add(userData);
    }

    /**
     * Removes a userdata from the userdata set
     *
     * @param uniqueId the uniqueId of the userData
     */
    public void removeUserData(final @NotNull UUID uniqueId) {
        this.userDataSet.removeIf(userData -> userData.getUniqueId().equals(uniqueId));
    }

    /**
     * @return the primary {@link UserProfile} of the user
     */
    public UserProfile getPrimaryProfile() {
        return profiles.get(0);
    }

    /**
     * @param slot the slot of the user profile
     * @return the {@link UserProfile} corresponding to {@param slot}
     */
    public UserProfile getUserProfile(final @NotNull Integer slot) {
        return profiles.get(slot);
    }

    /**
     * @return all the OneBlocks that the user is a member on
     */
    public HashSet<UserData> getUserData() {
        return userDataSet;
    }

    /**
     * @return all the {@link UserProfile} of the user
     */
    public HashMap<Integer, UserProfile> getProfiles() {
        return profiles;
    }

    /**
     * Checks if the user is a member of a OneBlock
     *
     * @param uniqueId the uniqueId of the OneBlock
     * @return the UserData Type
     */
    public Optional<UserData> getUserData(final @NotNull UUID uniqueId) {
        for (final UserData userData : userDataSet) {
            if (userData.getUniqueId().equals(uniqueId)) {
                return Optional.of(userData);
            }
        }
        return Optional.empty();
    }

    /**
     * Sets the UserType of userData
     *
     * @param uniqueId the uuid of the OneBlock
     * @param userType the type of user
     */
    public void setUserTypeOfUserData(final @NotNull UUID uniqueId,
                                      final @NotNull UserType userType) {
        for (final UserData userData : userDataSet) {
            if (userData.getUniqueId().equals(uniqueId)) {
                userData.setMemberType(userType);
                break;
            }
        }
    }

}
