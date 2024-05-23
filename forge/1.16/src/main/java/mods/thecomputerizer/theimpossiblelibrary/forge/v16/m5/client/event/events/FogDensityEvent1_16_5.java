package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;

import javax.annotation.Nonnull;

public class FogDensityEvent1_16_5 extends FogDensityEventWrapper<FogDensity> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FogDensity event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull FogDensity event) {
        return ClientEvents1_16_5.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }

    @Override
    protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }

    @Override
    protected EventFieldWrapper<FogDensity,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}