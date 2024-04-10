package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityJoinWorldEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldEventForge extends EntityJoinWorldEventWrapper<EntityJoinWorldEvent> {

    @Override
    protected EventFieldWrapper<EntityJoinWorldEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinWorldEvent::getWorld);
    }

    @Override
    protected EventFieldWrapper<EntityJoinWorldEvent,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinWorldEvent::getEntity);
    }
}