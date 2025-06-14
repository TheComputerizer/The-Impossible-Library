package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;
import static net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage.AFTER_LEVEL;

public class RenderWorldLastEventNeoForge extends RenderWorldLastEventWrapper<RenderLevelStageEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderLevelStageEvent event) {
        if(event.getStage()==AFTER_LEVEL) RENDER_WORLD_LAST.invoke(event);
    }
    
    @Override public void setEvent(RenderLevelStageEvent event) {
        super.setEvent(event);
    }
    
    @Override protected RenderContext initRenderer(RenderLevelStageEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
}