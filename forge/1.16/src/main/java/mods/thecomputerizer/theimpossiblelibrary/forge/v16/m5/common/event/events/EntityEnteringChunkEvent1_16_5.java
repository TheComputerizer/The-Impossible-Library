package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

public class EntityEnteringChunkEvent1_16_5 extends EntityEnteringChunkEventWrapper<EnteringChunk> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EnteringChunk event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<EnteringChunk,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EnteringChunk::getEntity);
    }

    @Override
    protected EventFieldWrapper<EnteringChunk,Integer> wrapNewXField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkX,EnteringChunk::setNewChunkX,0);
    }

    @Override
    protected EventFieldWrapper<EnteringChunk,Integer> wrapNewZField() {
        return wrapGenericBoth(EnteringChunk::getNewChunkZ,EnteringChunk::setNewChunkZ,0);
    }

    @Override
    protected EventFieldWrapper<EnteringChunk,Integer> wrapOldXField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkX,EnteringChunk::setOldChunkX,0);
    }

    @Override
    protected EventFieldWrapper<EnteringChunk,Integer> wrapOldZField() {
        return wrapGenericBoth(EnteringChunk::getOldChunkZ,EnteringChunk::setOldChunkZ,0);
    }
}
