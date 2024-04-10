package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public class RenderOverlayPostEventLegacy extends RenderOverlayPostEventWrapper<Post> {

    @SubscribeEvent
    public static void onEvent(Post event) {
        RENDER_OVERLAY_POST.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull Post event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<Post,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsLegacy.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
