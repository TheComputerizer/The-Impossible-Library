package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCloneEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.COPY_FROM;

public class PlayerCloneEventFabric extends PlayerCloneEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return COPY_FROM;
    }
    
    @Override protected EventFieldWrapper<Object[],Boolean> wrapDeathField() {
        return wrapGenericGetter(wrapArrayGetter(2),true);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapOriginalField() {
        return wrapPlayerGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(wrapArrayGetter(1));
    }
}