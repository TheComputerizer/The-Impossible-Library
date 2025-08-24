package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.WorldTickEventForge;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class WorldTickEventForge1_20 extends WorldTickEventForge<LevelTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(LevelTickEvent event) {
        TICK_WORLD.invoke(event);
    }
    
    @Override public WorldAPI<?> getWorld() {
        return wrapWorld(event -> ((LevelTickEvent)event).level);
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}