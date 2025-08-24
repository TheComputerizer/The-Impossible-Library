package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogDensityEventForge;



public class FogDensityEventForge1_20 extends FogDensityEventForge<Object> {
    
    @Override protected RenderContext initRenderer(Object event) {
        return null;
    }
    
    @Override public void cancel() {}
    
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