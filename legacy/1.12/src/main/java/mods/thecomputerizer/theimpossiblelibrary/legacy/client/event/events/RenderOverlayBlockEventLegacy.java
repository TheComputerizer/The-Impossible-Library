package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;

public class RenderOverlayBlockEventLegacy extends RenderOverlayBlockEventWrapper<RenderBlockOverlayEvent> {

    @SubscribeEvent
    public static void onEvent(RenderBlockOverlayEvent event) {
        RENDER_OVERLAY_BLOCK.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return ClientEventsLegacy.initRenderer(event::getRenderPartialTicks);
    }

    @Override
    protected EventFieldWrapper<RenderBlockOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsLegacy.getOverlayBlockType(event.getOverlayType()),OverlayType.BLOCK);
    }
}