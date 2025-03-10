package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.TickEvent.RenderTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_RENDER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class RenderTickEventNeoForge extends RenderTickEventWrapper<RenderTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderTickEvent event) {
        TICK_RENDER.invoke(event);
    }
    
    @Override public void setEvent(RenderTickEvent event) {
        super.setEvent(event);
    }

    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }

    @Override protected EventFieldWrapper<RenderTickEvent,Float> wrapTickTimeField() {
        return wrapGenericGetter(ev -> ev.renderTickTime,0f);
    }
}