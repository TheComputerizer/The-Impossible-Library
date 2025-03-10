package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityJoinWorldEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public class EntityJoinWorldEventNeoForge extends EntityJoinWorldEventWrapper<EntityJoinLevelEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityJoinLevelEvent event) {
        ENTITY_JOIN_WORLD.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityJoinLevelEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<EntityJoinLevelEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinLevelEvent::getLevel);
    }

    @Override protected EventFieldWrapper<EntityJoinLevelEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinLevelEvent::getEntity);
    }
}