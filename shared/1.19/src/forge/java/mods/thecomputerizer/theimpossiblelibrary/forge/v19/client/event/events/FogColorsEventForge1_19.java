package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogColorsEventForge;
import net.minecraftforge.client.event.ViewportEvent.ComputeFogColor;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public class FogColorsEventForge1_19 extends FogColorsEventForge<ComputeFogColor> {
    
    @SubscribeEvent
    public static void onEvent(ComputeFogColor event) {
        FOG_COLORS.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(ComputeFogColor event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTick()));
    }
    
    @Override protected EventFieldWrapper<ComputeFogColor,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
    
    @Override protected EventFieldWrapper<ComputeFogColor,Float> wrapBlue() {
        return wrapGenericBoth(ComputeFogColor::getBlue,ComputeFogColor::setBlue,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFogColor,Float> wrapGreen() {
        return wrapGenericBoth(ComputeFogColor::getGreen,ComputeFogColor::setGreen,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFogColor,Float> wrapRed() {
        return wrapGenericBoth(ComputeFogColor::getRed,ComputeFogColor::setRed,0f);
    }
    
    @Override protected EventFieldWrapper<ComputeFogColor,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}