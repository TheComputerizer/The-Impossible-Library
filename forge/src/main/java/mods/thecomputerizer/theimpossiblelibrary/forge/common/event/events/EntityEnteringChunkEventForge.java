package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

public class EntityEnteringChunkEventForge extends EntityEnteringChunkEventWrapper<EnteringChunk> {
    
    @SubscribeEvent
    public static void onEvent(EnteringChunk event) {
        ENTITY_ENTERING_CHUNK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EnteringChunk event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<EnteringChunk,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EnteringChunk::getEntity);
    }

    @Override protected EventFieldWrapper<EnteringChunk,Integer> wrapNewXField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkX,EnteringChunk::setNewChunkX,0);
    }

    @Override protected EventFieldWrapper<EnteringChunk,Integer> wrapNewZField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkZ,EnteringChunk::setNewChunkZ,0);
    }

    @Override protected EventFieldWrapper<EnteringChunk,Integer> wrapOldXField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkX,EnteringChunk::setOldChunkX,0);
    }

    @Override protected EventFieldWrapper<EnteringChunk,Integer> wrapOldZField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkZ,EnteringChunk::setOldChunkZ,0);
    }
}
