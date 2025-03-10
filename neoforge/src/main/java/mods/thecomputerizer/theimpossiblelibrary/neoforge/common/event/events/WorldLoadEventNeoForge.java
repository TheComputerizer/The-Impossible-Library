package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldLoadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Load;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_LOAD;

public class WorldLoadEventNeoForge extends WorldLoadEventWrapper<Load> {
    
    @SubscribeEvent
    public static void onEvent(Load event) {
        WORLD_LOAD.invoke(event);
    }
    
    @Override public void setEvent(Load event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Load,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Load::getLevel);
    }
}