package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import javax.annotation.Nonnull;

public class RenderWorldLastEventForge extends RenderWorldLastEventWrapper<RenderWorldLastEvent> {

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderWorldLastEvent event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }
}