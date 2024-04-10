package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogRenderEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public class FogRenderEventLegacy extends FogRenderEventWrapper<RenderFogEvent> {

    @SubscribeEvent
    public static void onEvent(RenderFogEvent event) {
        FOG_RENDER.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderFogEvent event) {
        return ClientEventsLegacy.initRenderer(() -> (float)event.getRenderPartialTicks());
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(RenderFogEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(RenderFogEvent::getState);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,Float> wrapFarplaneField() {
        return wrapGenericGetter(RenderFogEvent::getFarPlaneDistance,0f);
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,Integer> wrapFogModeField() {
        return wrapGenericGetter(RenderFogEvent::getFogMode,0);
    }
}