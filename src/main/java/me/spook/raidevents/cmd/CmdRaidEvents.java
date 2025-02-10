package me.spook.raidevents.cmd;

import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import me.spook.raidevents.Perm;
import me.spook.raidevents.entity.MConf;

import java.util.List;

@SuppressWarnings("unused")
public class CmdRaidEvents extends RaidEventsCommand {

    private static final CmdRaidEvents i = new CmdRaidEvents();

    public static CmdRaidEvents get() {
        return i;
    }

    private final CmdDebug cmdDebug = new CmdDebug();
    private final CmdTeleport cmdTeleport = new CmdTeleport();

    public CmdRaidEvents() {
        addRequirements(RequirementHasPerm.get(Perm.BASECOMMAND));
    }


    @Override
    public List<String> getAliases() {
        return MConf.get().aliases;
    }
}
