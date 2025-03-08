package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.EntityEnteringChunkEventForge;
import net.minecraftforge.event.level.ChunkEvent.Load;

public class EntityEnteringChunkEventForge1_20 extends EntityEnteringChunkEventForge<Load> { //TODO This is the wrong event
    
    @Override protected EventFieldWrapper<Load,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> null);
    }
    
    @Override protected EventFieldWrapper<Load,Integer> wrapNewXField() {
        return wrapGenericBoth(event -> 0,(event,x) -> {},0);
    }
    
    @Override protected EventFieldWrapper<Load,Integer> wrapNewZField() {
        return wrapGenericBoth(event -> 0,(event,z) -> {},0);
    }
    
    @Override protected EventFieldWrapper<Load,Integer> wrapOldXField() {
        return wrapGenericBoth(event -> 0,(event,x) -> {},0);
    }
    
    @Override protected EventFieldWrapper<Load,Integer> wrapOldZField() {
        return wrapGenericBoth(event -> 0,(event,z) -> {},0);
    }
}
