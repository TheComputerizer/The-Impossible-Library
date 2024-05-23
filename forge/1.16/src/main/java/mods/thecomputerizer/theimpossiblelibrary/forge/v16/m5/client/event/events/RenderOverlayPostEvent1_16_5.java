package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;

import javax.annotation.Nonnull;

public class RenderOverlayPostEvent1_16_5 extends RenderOverlayPostEventWrapper<Post> {
    
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
        return ClientEvents1_16_5.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Post,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_16_5.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
