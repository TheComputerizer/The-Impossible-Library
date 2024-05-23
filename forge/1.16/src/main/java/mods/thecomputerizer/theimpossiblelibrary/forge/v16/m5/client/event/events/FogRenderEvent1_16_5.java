package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogRenderEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;

import javax.annotation.Nonnull;

public class FogRenderEvent1_16_5 extends FogRenderEventWrapper<RenderFogEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RenderFogEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected RenderContext initRenderer(@Nonnull RenderFogEvent event) {
        return ClientEvents1_16_5.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,Float> wrapFarplaneField() {
        return wrapGenericGetter(RenderFogEvent::getFarPlaneDistance,0f);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,Integer> wrapFogModeField() {
        return wrapGenericGetter(event -> 0,0);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}