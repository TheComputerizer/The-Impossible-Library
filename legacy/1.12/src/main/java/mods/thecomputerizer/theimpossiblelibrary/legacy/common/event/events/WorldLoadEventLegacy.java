package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldLoadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_LOAD;

public class WorldLoadEventLegacy extends WorldLoadEventWrapper<Load> {

    @SubscribeEvent
    public static void onEvent(Load event) {
        WORLD_LOAD.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Load,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Load::getWorld);
    }
}