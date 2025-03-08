package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.RenderWorldLastEventForge;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;
import static net.minecraftforge.client.event.RenderLevelStageEvent.Stage.AFTER_LEVEL;

public class RenderWorldLastEventForge1_20 extends RenderWorldLastEventForge<RenderLevelStageEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderLevelStageEvent event) {
        if(event.getStage()==AFTER_LEVEL) RENDER_WORLD_LAST.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderLevelStageEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
}