package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.AFTER_RESPAWN;

public class PlayerRespawnEventFabric extends PlayerRespawnEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return AFTER_RESPAWN;
    }
    
    @Override protected EventFieldWrapper<Object[],Boolean> wrapEndConqueredField() {
        return wrapGenericGetter(wrapArrayGetter(2),false);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(wrapArrayGetter(1));
    }
}