package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_PRE;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayPreEventForge extends RenderOverlayPreEventWrapper<Pre> {
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        RENDER_OVERLAY_PRE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull Pre event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
    
    @Override public void setEvent(Pre event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Pre,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(event.getType()),ALL);
    }
}
