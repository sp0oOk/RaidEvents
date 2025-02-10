package me.spook.raidevents.entity;

import com.massivecraft.massivecore.store.Coll;

public class MConfColl extends Coll<MConf> {

    private static final MConfColl i = new MConfColl();

    public static MConfColl get() {
        return i;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) return;
        MConf.i = get("instance", true);
    }
}
