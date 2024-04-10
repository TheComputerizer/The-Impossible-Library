package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import javax.annotation.Nonnull;

public class RenderOverlayPreEventForge extends RenderOverlayPreEventWrapper<Pre> {

    @Override
    protected RenderAPI initRenderer(@Nonnull Pre event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<Pre,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
