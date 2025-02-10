package me.spook.raidevents.engine;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinMessage;
import me.spook.raidevents.RaidEvents;
import me.spook.raidevents.entity.MConf;
import me.spook.raidevents.obj.IBoxHolder;
import me.spook.raidevents.obj.IParticipator;
import me.spook.raidevents.obj.RaidEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class EngineRaidEvents extends Engine {

    private static EngineRaidEvents i = new EngineRaidEvents();

    public static EngineRaidEvents get() {
        return i;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        // -- Player and raid event -- //
        final Player player = event.getPlayer();
        final RaidEvent raidEvent;

        // -- If player is not in our raid event world or there isn't an active raid event, return -- //
        if (!Objects.equals(player.getWorld().getName(), MConf.get().raidEventsWorld)
                || (raidEvent = RaidEvents.get().getActiveRaidEvent()) == null) return;

        // -- Get participator and ensure it's a box holder -- //
        final IParticipator participator = raidEvent.getParticipator(player).orElse(null);
        if (!(participator instanceof IBoxHolder)) return;
        final IBoxHolder boxHolder = (IBoxHolder) participator;

        // -- If we are placing a block not inside our box, cancel the event and send a message -- //
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !boxHolder.isInsideBox(event.getClickedBlock().getLocation())) {
            event.setCancelled(true);
            MixinMessage.get().msgOne(player, MConf.get().cannotPlaceBlocksHereMessage);
        }

    }

}
