package me.spook.raidevents.obj;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Represents a participator in a raid event, this is normally a team-like entity
 * However, it is structured in a way where it is applicable for solos as-well without the use of
 * a faction plugin
 */

public interface IParticipator {

    // -------------------------------------------- //
    // IDENTIFICATION
    // -------------------------------------------- //

    String getId();

    String getName();

    // -------------------------------------------- //
    // OTHER
    // -------------------------------------------- //

    long getJoinedAtMillis();

    boolean isInTeam(Player player);

    void setWarp(Location location, String name);

    Location getWarp(String name);

    Location getLastKnownTntLocation();

    void setLastKnownTntLocation(Location location);

    /**
     * Gets the percentage until the raid event is breached.
     * <p>
     * This is calculated by the distance between the last known TNT location and the center of the raid event.
     * </p>
     *
     * @param raidEvent the raid event to get the percentage for
     * @return the percentage until the raid event is breached
     */

    default double getPercentageUntilBreached(IBoxHolder raidEvent) {
        final Location center = raidEvent.getCenter();
        final Location tntLocation = getLastKnownTntLocation();

        if (center == null || tntLocation == null) return 0.0;

        final double distance = center.distance(tntLocation);
        final double radius = raidEvent.getCurrentWidth() / 2.0;

        return Math.round(Math.min(1.0, distance / radius) * 100.0);
    }

    void removeWarp(String name);
}
