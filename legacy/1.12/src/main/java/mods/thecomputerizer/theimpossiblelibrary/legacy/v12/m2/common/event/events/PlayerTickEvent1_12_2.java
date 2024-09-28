package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerTickEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.*;

public class PlayerTickEvent1_12_2 extends PlayerTickEventWrapper<PlayerTickEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerTickEvent event) {
        TICK_PLAYER.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerTickEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}