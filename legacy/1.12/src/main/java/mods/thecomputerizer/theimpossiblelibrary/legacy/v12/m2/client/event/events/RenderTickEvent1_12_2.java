package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_RENDER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.*;

public class RenderTickEvent1_12_2 extends RenderTickEventWrapper<RenderTickEvent> {

    @SubscribeEvent
    public static void onEvent(RenderTickEvent event) {
        TICK_RENDER.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RenderTickEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }

    @Override protected EventFieldWrapper<RenderTickEvent,Float> wrapTickTimeField() {
        return wrapGenericGetter(ev -> ev.renderTickTime,0f);
    }
}