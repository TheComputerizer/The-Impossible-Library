package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class EntityEnteringChunkEventFabric extends EntityEnteringChunkEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EnteringChunk::getEntity);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapNewXField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkX,EnteringChunk::setNewChunkX,0);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapNewZField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkZ,EnteringChunk::setNewChunkZ,0);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapOldXField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkX,EnteringChunk::setOldChunkX,0);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapOldZField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkZ,EnteringChunk::setOldChunkZ,0);
    }
}
