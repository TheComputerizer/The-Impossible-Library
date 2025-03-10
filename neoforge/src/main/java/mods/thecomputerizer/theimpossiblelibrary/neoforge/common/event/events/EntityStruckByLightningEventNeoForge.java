package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public class EntityStruckByLightningEventNeoForge extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityStruckByLightningEvent event) {
        ENTITY_SMITTEN.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityStruckByLightningEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getEntity);
    }

    @Override protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapLightningEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getLightning);
    }
}