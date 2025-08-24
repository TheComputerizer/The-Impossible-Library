package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.EntityJoinWorldEventForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public class EntityJoinWorldEventForge1_19 extends EntityJoinWorldEventForge<EntityJoinLevelEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityJoinLevelEvent event) {
        ENTITY_JOIN_WORLD.invoke(event);
    }
    
    @Override protected EventFieldWrapper<EntityJoinLevelEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinLevelEvent::getLevel);
    }
    
    @Override protected EventFieldWrapper<EntityJoinLevelEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinLevelEvent::getEntity);
    }
}
