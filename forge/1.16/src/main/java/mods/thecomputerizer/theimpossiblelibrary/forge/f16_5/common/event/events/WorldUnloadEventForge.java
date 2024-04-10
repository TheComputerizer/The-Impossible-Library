package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldUnloadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class WorldUnloadEventForge extends WorldUnloadEventWrapper<Unload> {

    @Override
    protected EventFieldWrapper<Unload,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Unload::getWorld);
    }
}