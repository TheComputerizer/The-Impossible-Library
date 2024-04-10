package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldSaveEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Save;

public class WorldSaveEventForge extends WorldSaveEventWrapper<Save> {

    @Override
    protected EventFieldWrapper<Save,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Save::getWorld);
    }
}