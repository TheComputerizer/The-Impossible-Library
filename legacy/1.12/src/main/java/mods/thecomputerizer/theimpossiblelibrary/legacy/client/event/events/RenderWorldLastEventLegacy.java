package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public class RenderWorldLastEventLegacy extends RenderWorldLastEventWrapper<RenderWorldLastEvent> {

    @SubscribeEvent
    public static void onEvent(RenderWorldLastEvent event) {
        RENDER_WORLD_LAST.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderWorldLastEvent event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }
}