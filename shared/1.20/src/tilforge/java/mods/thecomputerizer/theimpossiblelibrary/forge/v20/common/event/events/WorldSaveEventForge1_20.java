package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.WorldSaveEventForge;
import net.minecraftforge.event.level.LevelEvent.Save;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_SAVE;

public class WorldSaveEventForge1_20 extends WorldSaveEventForge<Save> {
    
    @SubscribeEvent
    public static void onEvent(Save event) {
        WORLD_SAVE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<Save,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Save::getLevel);
    }
}