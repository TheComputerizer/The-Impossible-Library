package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public class RenderOverlayPostEvent1_12_2 extends RenderOverlayPostEventWrapper<Post> {

    @SubscribeEvent
    public static void onEvent(Post event) {
        RENDER_OVERLAY_POST.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Post event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected RenderContext initRenderer(@Nonnull Post event) {
        return ClientEvents1_12_2.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<Post,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_12_2.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
