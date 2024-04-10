package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.ClientEventsForge;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

import javax.annotation.Nonnull;

public class RenderOverlayBlockEventForge extends RenderOverlayBlockEventWrapper<RenderBlockOverlayEvent> {

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return ClientEventsForge.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<RenderBlockOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEventsForge.getOverlayBlockType(event.getOverlayType()),OverlayType.BLOCK);
    }
}