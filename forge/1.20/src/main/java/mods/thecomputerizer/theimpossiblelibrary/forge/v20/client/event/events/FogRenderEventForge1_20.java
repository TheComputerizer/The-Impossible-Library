package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogRenderEventForge;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public class FogRenderEventForge1_20 extends FogRenderEventForge<RenderFog> {
    
    @SubscribeEvent
    public static void onEvent(RenderFog event) {
        FOG_RENDER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderFog event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTick()));
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