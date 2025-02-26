package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogRenderEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public class FogRenderEventForge1_16_5 extends FogRenderEventForge<RenderFogEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderFogEvent event) {
        FOG_RENDER.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderFogEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<RenderFogEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }
    
    @Override protected EventFieldWrapper<RenderFogEvent,Float> wrapFarplaneField() {
        return wrapGenericGetter(RenderFogEvent::getFarPlaneDistance,0f);
    }
    
    @Override protected EventFieldWrapper<RenderFogEvent,Integer> wrapFogModeField() {
        return wrapGenericGetter(event -> 0,0);
    }
    
    @Override protected EventFieldWrapper<RenderFogEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}