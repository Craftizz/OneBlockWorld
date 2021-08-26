package io.github.craftizz.oneblockworld.managers;

import io.github.craftizz.oneblockworld.OneBlockWorld;
import io.github.craftizz.oneblockworld.database.DatabaseHandler;
import io.github.craftizz.oneblockworld.oneblock.Member;
import io.github.craftizz.oneblockworld.oneblock.OneBlock;
import io.github.craftizz.oneblockworld.user.User;
import io.github.craftizz.oneblockworld.user.UserData;
import io.github.craftizz.oneblockworld.user.UserProfile;
import io.github.craftizz.oneblockworld.user.UserType;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class OneBlockManager {

    private final OneBlockWorld plugin;
    private final WorldManager worldManager;
    private final UserManager userManager;
    private final DatabaseHandler databaseHandler;

    private final HashMap<UUID, OneBlock> oneBlocks;

    public OneBlockManager(final @NotNull OneBlockWorld plugin) {
        this.oneBlocks = new HashMap<>();
        this.plugin = plugin;
        this.worldManager = plugin.getWorldManager();
        this.userManager = plugin.getUserManager();
        this.databaseHandler = plugin.getDatabaseHandler();
    }

    /**
     * Returns a OneBlock with the corresponding uuid
     *
     * @param uniqueId the uuid of the OneBlock
     * @return OneBlock or null
     */
    public OneBlock getOneBlock(final @NotNull UUID uniqueId) {
        return oneBlocks.get(uniqueId);
    }

    /**
     * Creates a block block using the unique id of the creator
     * The {@param user} uuid will not match the uniqueId of the
     * OneBlock world
     *
     * @param user the creator of the OneBlock
     * @return a new one block
     */
    public OneBlock createOneBlock(final @NotNull User user) {

        // Create World
        final UUID uniqueId = UUID.randomUUID();
        worldManager.createOneBlockWorld(uniqueId);

        // Save Profile
        final Integer slot = user.getNextEmptyProfile();
        user.addProfile(new UserProfile(slot, uniqueId));

        final OneBlock oneBlock = new OneBlock(uniqueId);
        saveOneBlock(oneBlock, true);

        return oneBlock;
    }

    /**
     * Deletes oneBlock from the database of user and oneBlock,
     * and also the world
     *
     * @param oneBlock the oneBlock to be removed
     */
    public void deleteOneBlock(final @NotNull OneBlock oneBlock) {
        try {
            CompletableFuture.runAsync(() -> {
                databaseHandler.deleteUserOneBlock(oneBlock);
                databaseHandler.deleteOneBlock(oneBlock.getUniqueId());
            });
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            worldManager.deleteWorld(oneBlock.getUniqueId());
        }
    }

    /**
     * loads OneBlock from the database
     *
     * @param uniqueId the uuid of the OneBlock
     * @param async if should run sync/async
     * @return the loaded OneBlock
     */
    public OneBlock loadOneBlock(final @NotNull UUID uniqueId,
                                 final @NotNull Boolean async) {
        final OneBlock oneBlock;

        if (async) {
            oneBlock = databaseHandler.loadOneBlockAsync(uniqueId);
        } else {
            oneBlock = databaseHandler.loadOneBlockUrgently(uniqueId);
        }

        worldManager.createOrLoadWorld(uniqueId);
        oneBlocks.put(uniqueId, oneBlock);
        return oneBlock;
    }

    /**
     * Saves OneBlock to the database
     *
     * @param oneBlock the oneBlock to be saved
     * @param async if should run sync/async
     */
    public void saveOneBlock(final @NotNull OneBlock oneBlock,
                             final @NotNull Boolean async) {
        databaseHandler.saveOneBlock(oneBlock, async);
    }

    /**
     * Saves all loaded OneBlock to the database
     */
    public void saveAllOneBlock() {

        plugin.getLogger().warning("Saving OneBlocks data...");

        final Iterator<OneBlock> oneBlockIterator = oneBlocks.values().iterator();

        while (oneBlockIterator.hasNext()) {

            final OneBlock oneBlock = oneBlockIterator.next();
            databaseHandler.saveOneBlock(oneBlock, false);

            boolean shouldUnload = true;

            for (final Member member : oneBlock.getMembers()) {
                if (Bukkit.getPlayer(member.getUniqueId()) != null) {
                    shouldUnload = false;
                }
            }

            if (shouldUnload) {
                worldManager.unloadWorld(oneBlock.getUniqueId(), true);
                oneBlockIterator.remove();
            }

        }

    }

    public void addUserToOneBlock(final @NotNull User user,
                                  final @NotNull OneBlock oneBlock,
                                  final @NotNull UserType userType) {

        final UUID uniqueId = oneBlock.getUniqueId();
        oneBlock.addMember(uniqueId, userType);

        if (userType == UserType.OWNER) {
            user.addProfile(new UserProfile(user.getNextEmptyProfile(), uniqueId));
        } else {
            user.addUserData(new UserData(uniqueId, userType));
        }

    }

    public void removeUserFromOneBlock(final @NotNull User user,
                                       final @NotNull OneBlock oneBlock) {
        user.getUserData(oneBlock.getUniqueId()).ifPresent(
                userData -> {
                    if (userData.getMemberType() == UserType.OWNER) {
                        throw new IllegalArgumentException("You cannot remove an Owner from a OneBlock!");
                    } else {
                        user.removeUserData(oneBlock.getUniqueId());
                        oneBlock.removeMember(oneBlock.getUniqueId());
                    }
        });
    }

    public void setUserTypeOnOneBlock(final @NotNull User user,
                                      final @NotNull OneBlock oneBlock,
                                      final @NotNull UserType userType) {

        final UUID userUniqueId = user.getUniqueId();

        if (oneBlock.isMember(userUniqueId)) {

            final Member member = oneBlock.getMember(userUniqueId);

            if (member != null) {
                member.setUserType(userType);
                user.setUserTypeOfUserData(oneBlock.getUniqueId(), userType);
            }

        } else {
            addUserToOneBlock(user, oneBlock, userType);
        }

    }
}
