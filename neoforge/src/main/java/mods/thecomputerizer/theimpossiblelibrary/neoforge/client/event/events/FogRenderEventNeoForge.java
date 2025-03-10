package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogRenderEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public class FogRenderEventNeoForge extends FogRenderEventWrapper<RenderFog> {
    
    @SubscribeEvent
    public static void onEvent(RenderFog event) {
        FOG_RENDER.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderFog event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTick()));
    }
    
    @Override public void setEvent(RenderFog event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<RenderFog,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<RenderFog,Float> wrapFarplaneField() {
        return wrapGenericGetter(RenderFog::getFarPlaneDistance,0f);
    }
    
    @Override protected EventFieldWrapper<RenderFog,Integer> wrapFogModeField() {
        return wrapGenericGetter(event -> 0,0);
    }
    
    @Override protected EventFieldWrapper<RenderFog,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}