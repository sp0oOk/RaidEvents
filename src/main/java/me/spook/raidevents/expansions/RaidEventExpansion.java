package me.spook.raidevents.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.spook.raidevents.RaidEvents;
import me.spook.raidevents.obj.RaidEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RaidEventExpansion extends PlaceholderExpansion {

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public @NotNull String getIdentifier() {
        return "raidevents";
    }

    @Override
    public @NotNull String getAuthor() {
        return "spook";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    /**
     * Returns the placeholder value.
     *
     * @param player The player to parse the placeholder for
     * @param params The placeholder to parse
     * @return The placeholder value
     */

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        final RaidEvent event;
        if ((event = RaidEvents.get().getActiveRaidEvent()) == null) return "";

        if (params.matches("position_\\d+")) {
            final String pos = event.getLeaderboardParticipator(Integer.parseInt(params.split("_")[1]));
            return (pos == null ? "N/A" : pos);
        }

        if (params.equalsIgnoreCase("time_elapsed")) {
            return event.getTimeElapsed();
        }

        return "";
    }
}
