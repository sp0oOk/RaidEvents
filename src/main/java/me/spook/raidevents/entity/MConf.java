package me.spook.raidevents.entity;

import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;

import java.util.List;

public class MConf extends Entity<MConf> {

    protected static transient MConf i;

    public static MConf get() {
        return i;
    }

    public List<String> aliases = MUtil.list("raidevents", "re");
    public String raidEventsWorld = "raid-events";

    public int cannonBoxWidth = 16;
    public int cannonBoxHeight = 16;

    public String cannotPlaceBlocksHereMessage = "&c&lRaid Event &8➼ &7You cannot place blocks outside of your designated cannon box!";
    public final List<String> raidEventStartingNowMessage = MUtil.list(
            "&6&lRaid Event",
            "&aNew Raid Starting Now!",
            "&aVisit your cannon box with &n/f warp raid&a!"
    );

    public final List<String> raidEventAnnouncementJoinedMessage = MUtil.list(
            "&6&lRaid Event",
            "&aYour Faction has joined the active Raid Event!",
            "&aJoin the action with &n/f warp raid&a!"
    );

    public final List<String> raidEventBreachedMessage = MUtil.list(
            "&6&lRaid Event",
            "&2&lWINNER WINNER! &2You have breached the base in {time}!",
            "&aCheck &n/raidevent rewards&a for your prizes",
            "",
            "",
            "&cThis world will be deleted in 5 minutes",
            "&cCollect your TNT and loot before it's gone!",
            ""
    );

}
