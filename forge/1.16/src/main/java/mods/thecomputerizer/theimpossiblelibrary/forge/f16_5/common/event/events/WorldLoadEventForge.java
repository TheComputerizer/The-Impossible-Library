package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldLoadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Load;

public class WorldLoadEventForge extends WorldLoadEventWrapper<Load> {

    @Override
    protected EventFieldWrapper<Load,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Load::getWorld);
    }
}