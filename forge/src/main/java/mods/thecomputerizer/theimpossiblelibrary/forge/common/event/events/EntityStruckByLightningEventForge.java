package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public class EntityStruckByLightningEventForge extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {
    
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