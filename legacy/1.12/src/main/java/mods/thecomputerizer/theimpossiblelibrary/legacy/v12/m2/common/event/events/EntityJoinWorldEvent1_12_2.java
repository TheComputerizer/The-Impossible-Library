package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityJoinWorldEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public class EntityJoinWorldEvent1_12_2 extends EntityJoinWorldEventWrapper<EntityJoinWorldEvent> {

    @SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event) {
        ENTITY_JOIN_WORLD.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityJoinWorldEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<EntityJoinWorldEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinWorldEvent::getWorld);
    }

    @Override protected EventFieldWrapper<EntityJoinWorldEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinWorldEvent::getEntity);
    }
}