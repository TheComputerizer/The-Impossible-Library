package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.ScreenEvent.Render.Post;
import net.minecraftforge.eventbus.api.SubscribeEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayPostEventForge1_21 extends RenderOverlayPostEventWrapper<Post> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        RENDER_OVERLAY_POST.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(Post event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(Post event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Post,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ALL,ALL);
    }
}
