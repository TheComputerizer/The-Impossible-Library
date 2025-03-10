package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldUnloadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Unload;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_UNLOAD;

public class WorldUnloadEventNeoForge extends WorldUnloadEventWrapper<Unload> {
    
    @SubscribeEvent
    public static void onEvent(Unload event) {
        WORLD_UNLOAD.invoke(event);
    }
    
    @Override public void setEvent(Unload event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Unload,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Unload::getLevel);
    }
}