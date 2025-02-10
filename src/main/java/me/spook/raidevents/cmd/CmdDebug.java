package me.spook.raidevents.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.MUtil;
import me.spook.raidevents.RaidEvents;
import me.spook.raidevents.entity.MConf;
import me.spook.raidevents.obj.IParticipator;
import me.spook.raidevents.obj.RaidEvent;
import me.spook.raidevents.obj.impl.UUIDRaidParticipator;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class CmdDebug extends RaidEventsCommand {

    public CmdDebug() {
        aliases = MUtil.list("debug");
        addRequirements(RequirementIsPlayer.get());
        addParameter(TypeString.get(), "extra", "none");
    }

    @Override
    public void perform() throws MassiveException {

        if (RaidEvents.get().getActiveRaidEvent() == null)
            RaidEvents.get().setActiveRaidEvent(new RaidEvent(new Location(Bukkit.getWorld(MConf.get().raidEventsWorld), 0.0D, 50.0D, 0.0D)));

        final RaidEvent temp = RaidEvents.get().getActiveRaidEvent();
        final String opt = readArg(null);
        if (Objects.equals(opt, "center")) {
            final Location location = me.getLocation();
            msg("<g>Me is is in box: " + temp.isInsideBox(location));
            return;
        }

        final IParticipator randomParticipator = new UUIDRaidParticipator(Factions.getInstance().getAllFactions()
                .stream().filter(m -> Objects.equals(m.getTag(), "spook")).findFirst().orElse(null).getId(), temp.findNextAvailableCenterForBox());
        temp.join(randomParticipator);
        msg("Joined random participator: " + randomParticipator.getId());
    }
}
