package me.spook.raidevents.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.util.MUtil;
import me.spook.raidevents.RaidEvents;
import me.spook.raidevents.cmd.req.ReqHasFaction;
import me.spook.raidevents.obj.IBoxHolder;
import me.spook.raidevents.obj.IParticipator;
import me.spook.raidevents.obj.RaidEvent;

public class CmdTeleport extends RaidEventsCommand {

    public CmdTeleport() {
        aliases = MUtil.list("teleport", "tp");
        addRequirements(ReqHasFaction.get());
    }

    @Override
    public void perform() throws MassiveException {
        final IParticipator participator;
        final RaidEvent event;

        if ((event = RaidEvents.get().getActiveRaidEvent()) == null
                || (participator = event.getParticipator(me).orElse(null)) == null) {
            throw new MassiveException().addMessage("You are not in a raid event, or the event is not active.");
        }

        ((IBoxHolder) participator).teleport(me);
        msg("<g>Teleported to the raid event.");
    }
}
