package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityEvent.EnteringSection;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

public class EntityEnteringChunkEventNeoForge extends EntityEnteringChunkEventWrapper<EnteringSection> {
    
    @SubscribeEvent
    public static void onEvent(EnteringSection event) {
        if(event.didChunkChange()) ENTITY_ENTERING_CHUNK.invoke(event);
    }
    
    @Override public void setEvent(EnteringSection event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<EnteringSection,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EnteringSection::getEntity);
    }
    
    @Override protected EventFieldWrapper<EnteringSection,Integer> wrapNewXField() {
        return wrapGenericBoth(event -> event.getNewPos().x(),(event,x) -> {},0);
    }
    
    @Override protected EventFieldWrapper<EnteringSection,Integer> wrapNewZField() {
        return wrapGenericBoth(event -> event.getNewPos().z(),(event,z) -> {},0);
    }
    
    @Override protected EventFieldWrapper<EnteringSection,Integer> wrapOldXField() {
        return wrapGenericBoth(event -> event.getOldPos().x(),(event,x) -> {},0);
    }
    
    @Override protected EventFieldWrapper<EnteringSection,Integer> wrapOldZField() {
        return wrapGenericBoth(event -> event.getOldPos().z(),(event,z) -> {},0);
    }
}