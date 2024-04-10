package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;

import javax.annotation.Nonnull;

public class RenderOverlayPostEventForge extends RenderOverlayPostEventWrapper<Post> {

    @Override
    protected RenderAPI initRenderer(@Nonnull Post event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Post,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
