package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldLoadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_LOAD;

public class WorldLoadEvent1_12_2 extends WorldLoadEventWrapper<Load> {

    @SubscribeEvent
    public static void onEvent(Load event) {
        WORLD_LOAD.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Load event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<Load,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Load::getWorld);
    }
}