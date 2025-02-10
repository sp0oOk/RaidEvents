package me.spook.raidevents.cmd.req;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReqHasFaction extends RequirementAbstract {

    private static ReqHasFaction i = new ReqHasFaction();
    public static ReqHasFaction get() { return i; }


    @Override
    public boolean apply(CommandSender commandSender, MassiveCommand massiveCommand) {
        if (!(commandSender instanceof Player)) return false;
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer((Player) commandSender);

        return fPlayer.hasFaction();
    }

    @Override
    public String createErrorMessage(CommandSender commandSender, MassiveCommand massiveCommand) {
        return "You must be in a faction to use this command.";
    }
}
