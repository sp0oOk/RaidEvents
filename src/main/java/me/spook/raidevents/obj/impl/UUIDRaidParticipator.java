package me.spook.raidevents.obj.impl;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import me.spook.raidevents.entity.MConf;
import me.spook.raidevents.obj.IBoxHolder;
import me.spook.raidevents.obj.IParticipator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UUIDRaidParticipator implements IParticipator, IBoxHolder {

    // -------------------------------------------- //
    // FIELDS & CONSTRUCT
    // -------------------------------------------- //

    private final String id;
    private final Location center;
    private int width = MConf.get().cannonBoxWidth;
    private int height = MConf.get().cannonBoxHeight;
    private Location lastKnownTntLocation = null;
    private final long joinedAtMillis = System.currentTimeMillis();

    /**
     * Constructs a UUID raid participator.
     * <p>
     * UUID is referring to this implementation being used for FactionsUUID
     * {@link Factions} and {@link FPlayer} are from FactionsUUID
     *
     * @param id     the faction id
     * @param center the center of the raid box
     */

    public UUIDRaidParticipator(String id, Location center) {
        this.id = id;
        this.center = center;
        createBox(width, height, Material.OBSIDIAN);
        addWalls(2, Material.OBSIDIAN, true);
    }

    /**
     * Returns the id of the faction.
     *
     * @return the id of the faction
     */

    @Override
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the faction.
     *
     * @return the name of the faction
     */

    @Override
    public String getName() {
        return Factions
                .getInstance()
                .getFactionById(getId())
                .getTag();
    }

    /**
     * Returns the time the faction joined the raid.
     *
     * @return the time the faction joined the raid
     */

    @Override
    public long getJoinedAtMillis() {
        return joinedAtMillis;
    }

    /**
     * Checks if a player is in the faction.
     *
     * @param player the player to check
     * @return true if the player is in the faction, false otherwise
     */

    @Override
    public boolean isInTeam(Player player) {
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if (fPlayer == null || !fPlayer.hasFaction()) return false;

        return fPlayer.getFaction().getId().equals(getId());
    }

    /**
     * Set the warp for the faction.
     *
     * @param center the center of the box
     * @param name   the name of the warp
     */

    @Override
    public void setWarp(Location center, String name) {

    }

    /**
     * Get the warp for the faction.
     *
     * @param name the name of the warp
     * @return the location of the warp
     */

    @Override
    public Location getWarp(String name) {
        return center;
    }

    /**
     * Get the last known TNT location.
     *
     * @return the last known TNT location
     */

    @Override
    public Location getLastKnownTntLocation() {
        return lastKnownTntLocation;
    }

    /**
     * Set the last known TNT location.
     *
     * @param location the location of the TNT
     */

    @Override
    public void setLastKnownTntLocation(Location location) {
        this.lastKnownTntLocation = location;
    }

    /**
     * Remove the warp for the faction.
     *
     * @param name the name of the warp
     */

    @Override
    public void removeWarp(String name) {

    }

    /**
     * Get the center of the box.
     *
     * @return the center of the box
     */

    @Override
    public Location getCenter() {
        return center;
    }

    // -------------------------------------------- //
    // GETTERS & SETTERS
    // -------------------------------------------- //

    @Override
    public int getCurrentWidth() {
        return width;
    }

    @Override
    public int getCurrentHeight() {
        return height;
    }

    @Override
    public void setCurrentWidth(int width) {
        this.width = width;
    }

    @Override
    public void setCurrentHeight(int height) {
        this.height = height;
    }
}
