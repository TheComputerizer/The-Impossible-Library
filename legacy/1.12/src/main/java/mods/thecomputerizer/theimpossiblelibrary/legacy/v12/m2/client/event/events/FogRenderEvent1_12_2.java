package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogRenderEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public class FogRenderEvent1_12_2 extends FogRenderEventWrapper<RenderFogEvent> {

    @SubscribeEvent
    public static void onEvent(RenderFogEvent event) {
        FOG_RENDER.invoke(event);
    }
    
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
        return ClientEvents1_12_2.initRenderer(() -> (float)event.getRenderPartialTicks());
    }

    @Override
    protected EventFieldWrapper<RenderFogEvent,EntityAPI<?,?>> wrapEntityField() {
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