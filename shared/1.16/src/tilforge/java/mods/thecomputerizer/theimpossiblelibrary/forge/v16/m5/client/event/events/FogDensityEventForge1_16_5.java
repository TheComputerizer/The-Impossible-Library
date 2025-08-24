package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogDensityEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public class FogDensityEventForge1_16_5 extends FogDensityEventForge<FogDensity> {
    
    @SubscribeEvent
    public static void onEvent(FogDensity event) {
        FOG_DENSITY.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(FogDensity event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<FogDensity,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }
    
    @Override protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }
    
    @Override protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}