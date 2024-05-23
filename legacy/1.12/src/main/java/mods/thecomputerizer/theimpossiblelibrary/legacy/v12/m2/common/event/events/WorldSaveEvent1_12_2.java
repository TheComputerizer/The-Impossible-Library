package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldSaveEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_SAVE;

public class WorldSaveEvent1_12_2 extends WorldSaveEventWrapper<Save> {

    @SubscribeEvent
    public static void onEvent(Save event) {
        WORLD_SAVE.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Save event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<Save,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Save::getWorld);
    }
}