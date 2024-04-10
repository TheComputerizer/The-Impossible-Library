package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

public class EntityEnteringChunkEventForge extends EntityEnteringChunkEventWrapper<EnteringChunk> {

    @Override
    protected EventFieldWrapper<EnteringChunk,EntityAPI<?>> wrapEntityField() {
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
