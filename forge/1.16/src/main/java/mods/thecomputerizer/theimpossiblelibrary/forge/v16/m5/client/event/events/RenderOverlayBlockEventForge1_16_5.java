package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.RenderOverlayBlockEventForge;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

import javax.annotation.Nonnull;

public class RenderOverlayBlockEventForge1_16_5 extends RenderOverlayBlockEventForge {
    
    @Override protected RenderContext initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
}
