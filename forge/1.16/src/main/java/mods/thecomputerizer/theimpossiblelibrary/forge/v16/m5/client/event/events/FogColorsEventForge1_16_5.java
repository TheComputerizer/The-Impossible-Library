package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogColorsEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;

import javax.annotation.Nonnull;

public class FogColorsEventForge1_16_5 extends FogColorsEventForge {
    
    @Override protected RenderContext initRenderer(@Nonnull FogColors event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getRenderPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<FogColors,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }
}