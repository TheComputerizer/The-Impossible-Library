package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

public class EntityEnteringChunkEventLegacy extends EntityEnteringChunkEventWrapper<EnteringChunk> {

    @SubscribeEvent
    public static void onEvent(EnteringChunk event) {
        ENTITY_ENTERING_CHUNK.invoke(event);
    }

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
