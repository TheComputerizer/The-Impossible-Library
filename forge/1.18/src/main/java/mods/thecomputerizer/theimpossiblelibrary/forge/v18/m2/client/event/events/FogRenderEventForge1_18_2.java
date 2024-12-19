package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FogRenderEventForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;

import javax.annotation.Nonnull;

public class FogRenderEventForge1_18_2 extends FogRenderEventForge {
    
    @Override protected RenderContext initRenderer(@Nonnull RenderFogEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks((float)event.getPartialTicks()));
    }
    
    @Override protected EventFieldWrapper<RenderFogEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getCamera().getEntity());
    }
}