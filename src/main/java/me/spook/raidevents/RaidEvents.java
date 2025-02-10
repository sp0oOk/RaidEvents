package me.spook.raidevents;

import com.massivecraft.massivecore.MassivePlugin;
import me.spook.raidevents.cmd.CmdRaidEvents;
import me.spook.raidevents.engine.EngineRaidEvents;
import me.spook.raidevents.entity.MConfColl;
import me.spook.raidevents.expansions.RaidEventExpansion;
import me.spook.raidevents.obj.RaidEvent;

@SuppressWarnings("all")
public final class RaidEvents extends MassivePlugin {

    // -------------------------------------------- //
    // FIELDS & CONSTRUCT
    // -------------------------------------------- //

    private static RaidEvents i;
    private RaidEventExpansion expansion;
    public RaidEvent activeRaidEvent;

    public static RaidEvents get() {
        return i;
    }

    /**
     * Constructs the plugin.
     */

    public RaidEvents() {
        i = this;
        setVersionSynchronized(false);
    }

    /**
     * Emitted when the plugin is enabled.
     */

    @Override
    public void onEnableInner() {
        super.onEnableInner();

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")
                && (expansion = new RaidEventExpansion()).register()) {
            log("PlaceholderAPI expansion enabled.");
        } else {
            log("PlaceholderAPI expansion not enabled.");
        }

        activate(
                MConfColl.class,
                CmdRaidEvents.class,
                EngineRaidEvents.class
        );
    }

    /**
     * Emitted when the plugin is disabled.
     */

    @Override
    public void onDisable() {
        super.onDisable();
        if (expansion != null) {
            expansion.unregister();
        }

        i = null;
    }

    /**
     * Returns the active raid event.
     *
     * @return the active raid event or null if there is none.
     */

    public RaidEvent getActiveRaidEvent() {
        return activeRaidEvent;
    }

    /**
     * Sets the active raid event.
     *
     * @param activeRaidEvent the raid event to set as active.
     */

    public void setActiveRaidEvent(RaidEvent activeRaidEvent) {
        this.activeRaidEvent = activeRaidEvent;
    }
}
