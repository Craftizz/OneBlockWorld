package io.github.craftizz.oneblockworld.user;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class User {

    private final UUID uniqueId;

    private final HashSet<Member> members;
    private final HashMap<Integer, UserProfile> profiles;

    public User(final @NotNull UUID uniqueId,
                final @NotNull HashSet<Member> members,
                final @NotNull HashMap<Integer, UserProfile> profiles) {

        this.uniqueId = uniqueId;
        this.members = members;
        this.profiles = profiles;

    }

    /**
     * @return the UniqueId of the player. This will
     * match the uniqueId of {@link org.bukkit.entity.Player}
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * @return all the {@link UserProfile} of the user
     */
    public HashMap<Integer, UserProfile> getProfiles() {
        return profiles;
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
    public HashSet<Member> getMembers() {
        return members;
    }

    /**
     * @param uniqueId the uniqueId of the OneBlock
     * @return the Member Type
     */
    public Optional<Member> getMemberOf(final @NotNull UUID uniqueId) {
        return members.stream().filter(member -> member.getUniqueId().equals(uniqueId)).findFirst();
    }
}
