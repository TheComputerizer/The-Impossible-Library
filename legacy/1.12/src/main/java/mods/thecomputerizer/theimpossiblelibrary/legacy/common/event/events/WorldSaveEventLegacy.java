package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldSaveEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_SAVE;

public class WorldSaveEventLegacy extends WorldSaveEventWrapper<Save> {

    @SubscribeEvent
    public static void onEvent(Save event) {
        WORLD_SAVE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Save,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Save::getWorld);
    }
}