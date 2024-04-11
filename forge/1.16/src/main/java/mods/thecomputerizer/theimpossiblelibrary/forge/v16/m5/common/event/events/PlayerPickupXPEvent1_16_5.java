package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;

public class PlayerPickupXPEvent1_16_5 extends PlayerPickupXPEventWrapper<PickupXp> {

    @Override
    protected EventFieldWrapper<PickupXp,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PickupXp::getPlayer);
    }

    @Override
    protected EventFieldWrapper<PickupXp,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(PickupXp::getOrb);
    }
}