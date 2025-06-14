package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;



public class FogDensityEventNeoForge extends FogDensityEventWrapper<Object> {
    
    @Override protected RenderContext initRenderer(Object event) {
        return null;
    }
    
    @Override public void setEvent(Object event) {}
    
    @Override protected EventFieldWrapper<Object,EntityAPI<?,?>> wrapEntityField() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object,Float> wrapDensityField() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object,BlockStateAPI<?>> wrapStateField() {
        return null;
    }
}