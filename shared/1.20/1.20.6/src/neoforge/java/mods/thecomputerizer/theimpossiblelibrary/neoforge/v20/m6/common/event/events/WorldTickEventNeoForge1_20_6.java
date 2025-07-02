package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.WorldTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class WorldTickEventNeoForge1_20_6 extends WorldTickEventNeoForge<LevelTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        TICK_WORLD.invoke(event);
    }
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        TICK_WORLD.invoke(event);
    }
    
    TickPhase phase;
    
    @Override public WorldAPI<?> getWorld() {
        return wrapWorld(getter("getLevel"));
    }
    
    @Override public void setEvent(LevelTickEvent event) {
        super.setEvent(event);
        this.phase = Objects.nonNull(event) ? (event instanceof Pre ? START : END) : DEFAULT;
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.phase) ? this.phase : DEFAULT;
    }
}