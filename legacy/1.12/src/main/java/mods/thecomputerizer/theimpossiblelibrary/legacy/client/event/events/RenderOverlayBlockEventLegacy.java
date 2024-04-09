package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Objects;

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
    protected OverlayType wrapOverlayType() {
        switch(Objects.isNull(this.event) ? RenderBlockOverlayEvent.OverlayType.BLOCK : this.event.getOverlayType()) {
            case FIRE: return OverlayType.FIRE;
            case WATER: return OverlayType.WATER;
            default: return OverlayType.BLOCK;
        }
    }
}