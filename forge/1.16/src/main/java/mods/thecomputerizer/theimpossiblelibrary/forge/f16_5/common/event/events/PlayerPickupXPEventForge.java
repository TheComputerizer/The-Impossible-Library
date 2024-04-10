package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;

public class PlayerPickupXPEventForge extends PlayerPickupXPEventWrapper<PickupXp> {

    @Override
    protected EventFieldWrapper<PickupXp,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(PickupXp::getPlayer);
    }

    @Override
    protected EventFieldWrapper<PickupXp,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(PickupXp::getOrb);
    }
}