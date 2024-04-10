package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogRenderEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;

import javax.annotation.Nonnull;

public class FogRenderEventForge extends FogRenderEventWrapper<RenderFogEvent> {

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderFogEvent event) {
        return ClientEventsForge.initRenderer(() -> (float)event.getRenderPartialTicks(),() -> null);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,EntityAPI<?>> wrapEntityField() {
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