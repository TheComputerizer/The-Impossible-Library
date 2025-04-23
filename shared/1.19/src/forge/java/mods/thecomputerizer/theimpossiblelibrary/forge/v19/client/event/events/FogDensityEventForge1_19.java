package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogDensityEventForge;

import javax.annotation.Nonnull;

public class FogDensityEventForge1_19 extends FogDensityEventForge<Object> {
    
    @Override protected RenderContext initRenderer(@Nonnull Object event) {
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