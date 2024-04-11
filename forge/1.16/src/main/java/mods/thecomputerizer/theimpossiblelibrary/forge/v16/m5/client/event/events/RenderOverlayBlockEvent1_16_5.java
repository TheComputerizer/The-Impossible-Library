package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

import javax.annotation.Nonnull;

public class RenderOverlayBlockEvent1_16_5 extends RenderOverlayBlockEventWrapper<RenderBlockOverlayEvent> {

    @Override
    protected RenderAPI initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return ClientEvents1_16_5.initRenderer(() -> 0f,event::getMatrixStack);
    }

    @Override
    protected EventFieldWrapper<RenderBlockOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_16_5.getOverlayBlockType(event.getOverlayType()),OverlayType.BLOCK);
    }
}