package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_PRE;

public class RenderOverlayPreEvent1_12_2 extends RenderOverlayPreEventWrapper<Pre> {

    @SubscribeEvent
    public static void onEvent(Pre event) {
        RENDER_OVERLAY_PRE.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Pre event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected RenderContext initRenderer(@Nonnull Pre event) {
        return ClientEvents1_12_2.initRenderer(event::getPartialTicks);
    }

    @Override
    protected EventFieldWrapper<Pre,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> ClientEvents1_12_2.getOverlayElementType(event.getType()),OverlayType.ALL);
    }
}
