package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.EntityJoinWorldEventForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public class EntityJoinWorldEventForge1_16_5 extends EntityJoinWorldEventForge<EntityJoinWorldEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
        ENTITY_JOIN_WORLD.invoke(event);
    }
    
    @Override protected EventFieldWrapper<EntityJoinWorldEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinWorldEvent::getWorld);
    }
    
    @Override protected EventFieldWrapper<EntityJoinWorldEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinWorldEvent::getEntity);
    }
}
