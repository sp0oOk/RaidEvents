package me.spook.raidevents.obj;

import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;

public class RaidEvent implements IBoxHolder {

    // -------------------------------------------- //
    // FIELDS & CONSTRUCT
    // -------------------------------------------- //

    private static final int BUFFER_ZONE = 32; // TODO: move to MConf

    private @Setter RaidPhase raidPhase = RaidPhase.WAITING; // TODO: add usages
    private final long elapsedTimeMillis = System.currentTimeMillis();
    private final Location center;
    private final List<Reference<IParticipator>> participants = MUtil.list();

    private int width = 32;
    private int height = 32;

    /**
     * Constructs a raid event.
     *
     * @param center the center of the raid event box
     */

    public RaidEvent(Location center) {
        this.center = center;
        createBox(width, height, Material.OBSIDIAN);
        addWalls(2, Material.OBSIDIAN, true);
    }

    /**
     * Adds a participator to the raid event.
     *
     * @param participator the participator to add
     */

    public void join(IParticipator participator) {
        participants.add(new WeakReference<>(participator));
    }


    /**
     * Attempts to find an available center for a raid box aligned for the raid box.
     *
     * @return the location of the center of the box if available, null otherwise
     */

    public Location findNextAvailableCenterForBox() {
        int boxWidth = getCurrentWidth();

        Location[] potentialCenters = new Location[]{
                center.clone().add(boxWidth + BUFFER_ZONE + 2, 0, 0), // East
                center.clone().add(-(boxWidth + BUFFER_ZONE + 2), 0, 0), // West
                center.clone().add(0, 0, boxWidth + BUFFER_ZONE + 2), // South
                center.clone().add(0, 0, -(boxWidth + BUFFER_ZONE + 2)) // North
        };

        for (Location potentialCenter : potentialCenters) {
            boolean isAvailable = true;
            for (Reference<IParticipator> participantRef : participants) {
                IParticipator participant = participantRef.get();
                if (participant instanceof IBoxHolder) {
                    IBoxHolder boxHolder = (IBoxHolder) participant;
                    if (boxHolder.isInsideBox(potentialCenter)) {
                        isAvailable = false;
                        break;
                    }
                }
            }
            if (isAvailable) {
                return potentialCenter;
            }
        }

        return null;
    }

    /**
     * Gets the formal time elapsed since the raid event started.
     * <p>
     * e.g. 2m 30s
     *
     * @return the formal time elapsed
     */

    public String getTimeElapsed() {
        return TimeDiffUtil.getFormalTimeFromMillis(System.currentTimeMillis() - elapsedTimeMillis, true);
    }

    /**
     * Gets a leaderboard position based on who is the closest to breaching the raid event box.
     * if all participants are at 0% then the first person to join will be at the top of the leaderboard.
     *
     * @param position the position to get
     * @return the participator at the given position
     */

    public String getLeaderboardParticipator(int position) {
        participants.sort((o1, o2) -> {
            IParticipator p1 = o1.get();
            IParticipator p2 = o2.get();
            if (p1 == null || p2 == null) return 0;
            return Double.compare(p1.getPercentageUntilBreached(this), p2.getPercentageUntilBreached(this));
        });

        if (position < 0 || position >= participants.size()) return null;

        IParticipator participator = participants.get(position).get();
        return participator == null ? null : participator.getName() + " " + ChatColor.GRAY + "(" + participator.getPercentageUntilBreached(this) + "%)";
    }

    /**
     * Gets a raid participator by a player on their team.
     * <p>
     * Info: In the base functionality/implementation this gets the team associated with a faction as per
     * the {@link me.spook.raidevents.obj.impl.UUIDRaidParticipator} class.
     *
     * @param player the player to get the participator for
     * @return the participator if found, empty otherwise
     */

    public Optional<IParticipator> getParticipator(Player player) {
        for (Reference<IParticipator> reference : participants) {
            IParticipator participator = reference.get();

            if (participator == null) {
                continue;
            }

            if (participator.isInTeam(player)) {
                return Optional.of(participator);
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the center of the raid event. This is the location where the raid event box is centered.
     *
     * @return the center of the raid event
     */

    @Override
    public Location getCenter() {
        return center;
    }

    /**
     * Current width of the raid event box.
     *
     * @return the current width of the raid event box
     */

    @Override
    public int getCurrentWidth() {
        return this.width;
    }

    /**
     * Current height of the raid event box.
     *
     * @return the current height of the raid event box
     */

    @Override
    public int getCurrentHeight() {
        return this.height;
    }

    /**
     * Sets the current width of the raid event box.
     *
     * @param width the new width of the raid event box
     */

    @Override
    public void setCurrentWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the current height of the raid event box.
     *
     * @param height the new height of the raid event box
     */

    @Override
    public void setCurrentHeight(int height) {
        this.height = height;
    }
}