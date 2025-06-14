package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.RenderWorldLastEventForge;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public class RenderWorldLastEventForge1_19 extends RenderWorldLastEventForge<RenderLevelLastEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderLevelLastEvent event) {
        RENDER_WORLD_LAST.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(RenderLevelLastEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
}