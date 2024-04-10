package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_PRE;

public class RenderOverlayPreEventLegacy extends RenderOverlayPreEventWrapper<Pre> {

    @SubscribeEvent
    public static void onEvent(Pre event) {
        RENDER_OVERLAY_PRE.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull Pre event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<Pre,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsLegacy.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
