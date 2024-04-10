package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldUnloadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_UNLOAD;

public class WorldUnloadEventLegacy extends WorldUnloadEventWrapper<Unload> {

    @SubscribeEvent
    public static void onEvent(Unload event) {
        WORLD_UNLOAD.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Unload,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Unload::getWorld);
    }
}