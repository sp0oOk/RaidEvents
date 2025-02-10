package me.spook.raidevents;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified {
    BASECOMMAND;

    private final String id = PermissionUtil.createPermissionId(RaidEvents.get(), this);

    @Override
    public String getId() {
        return id;
    }

    public boolean has(Permissible permissible, boolean verbose) {
        return PermissionUtil.hasPermission(permissible, this, verbose);
    }

    public boolean has(Permissible permissible) {
        return PermissionUtil.hasPermission(permissible, this);
    }
}
