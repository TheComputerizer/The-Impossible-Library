package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogDensityEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public class FogDensityEventForge1_18_2 extends FogDensityEventForge<FogDensity> {
    
    @SubscribeEvent
    public static void onEvent(FogDensity event) {
        FOG_DENSITY.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull FogDensity event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<FogDensity,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<FogDensity,Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }
    
    @Override protected EventFieldWrapper<FogDensity,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}